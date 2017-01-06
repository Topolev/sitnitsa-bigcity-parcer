package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.enums.StatusProduct;
import com.mycompany.myapp.domain.rules.RuleExtractCategories;
import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import com.mycompany.myapp.repository.dataparsing.CategoryRepository;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;
import com.mycompany.myapp.service.bigcity.ProductLink;

import com.mycompany.myapp.web.rest.vmrules.RuleExtractProductVM;
import com.mycompany.myapp.web.rest.vmrules.RulesExtractCategoriesVM;
import com.mycompany.myapp.web.rest.vmbigcity.SelectorProductField;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mycompany.myapp.service.util.ExternalPageUtil.*;
import static com.mycompany.myapp.service.util.StringUrlUtil.deleteRootUrl;
import static com.mycompany.myapp.service.util.StringUrlUtil.deleteSlashFromBeginAndEnd;

@Service
public class ParserService {

    private static final Logger LOG = LoggerFactory.getLogger(ParserService.class);

    @Inject
    private RuleExtractCategoriesService ruleExtractCategoriesService;
    @Inject
    private RuleExtractProductLinkService ruleExtractProductLinkService;
    @Inject
    private RuleExtractProductService ruleExtractProductService;

    @Inject
    private ShopRepository shopRepository;
    @Inject
    private CategoryService categoryService;
    @Inject
    private ProductService productService;



    public List<Category> buildCategories(RuleExtractCategories rules) {
        List<Category> categories = new ArrayList<>();
        Shop currentShop = shopRepository.findOne(rules.getShop().getId());
        RuleExtractCategories currentRule = rules;
        try {
            Document pageDOM = getPageDOM(rules.getShop().getUrl());
            Elements currentCategories = pageDOM.body().select(currentRule.getSelector());
            for (Element wrapCategory : currentCategories) {
                Category parentCategory = extractCategoryFrom(wrapCategory, rules.getShop().getUrl(), currentShop);
                categories.add(parentCategory);
                if (currentRule.getChild() != null) {
                    buildChildrenCategory(wrapCategory, parentCategory, currentRule.getChild(), currentShop);
                }
            }
        } catch (IOException e) {
            LOG.debug("Cannot get page with url '{}'", e);
        }
        return categories;
    }


    public List<ProductLink> buildProductLinks(RuleExtractProductLink rules, List<Category> categories) {
        List<ProductLink> productLinks = new ArrayList<>();
        List<Category> categoriesWithoutChildren = categoryService.getCategoriesWithoutChildren(categories);
        for (Category category : categoriesWithoutChildren) {
            String currentUrl = rules.getShop().getUrl() + category.getHref();
            try {
                Document pageDOM = getPageDOM(currentUrl);
                Elements links = pageDOM.body().select(rules.getSelector());
                for (Element link : links) {
                    String productName = link.ownText();
                    String productHref = deleteSlashFromBeginAndEnd(deleteRootUrl(rules.getShop().getUrl(), link.attr("href")));
                    ProductLink productLink = new ProductLink(productName, productHref, category);
                    productLinks.add(productLink);
                }
                if (productLinks.size() > 10) break;
            } catch (IOException e) {
                LOG.debug("Cannot get page with url '{}'", currentUrl, e);
            }
        }
        return productLinks;
    }


    public List<Product> buildProduct(RuleExtractProduct rules, List<ProductLink> links) {
        List<Product> products = new ArrayList<>();
        for (ProductLink link : links) {
            String currentUrl = rules.getShop().getUrl() + link.getUrl();
            try {
                Document pageDOM = getPageDOM(currentUrl);
                Element wrapProduct = pageDOM.body();
                Product product = extractProductFrom(wrapProduct, rules, rules.getShop().getUrl());
                product.setCategory(link.getCategory());
                product.setShop(rules.getShop());
                product.setStatus(StatusProduct.AVAILABLE);
                products.add(product);
                if (products.size() > 10) break;
            } catch (IOException e) {
                LOG.debug("Cannot get page with url '{}'", currentUrl, e);
            }
        }
        return products;
    }

    private Category extractCategoryFrom(Element wrap, String rootUrl, Shop shop) {
        Elements elementsByTagA = wrap.getAllElements().select("a");
        Element element = elementsByTagA.get(0);
        String nameCategory = element.ownText();
        String href = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, element.attr("href")));
        return new Category(nameCategory, deleteSlashFromBeginAndEnd(href), shop);
    }

    private Product extractProductFrom(Element wrapProductDOM, RuleExtractProduct rules, String rootUrl){
        Product product = new Product();

        Optional.ofNullable(wrapProductDOM.select(rules.getSelectorName()))
            .map(Elements::first)
            .ifPresent(element -> product.setName(element.ownText()));

        Optional.ofNullable(wrapProductDOM.select(rules.getSelectorImage()))
            .map(Elements::first)
            .ifPresent(element -> {
                String src = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, element.attr("src")));
                product.setImageUrl(src);
            });

        Optional.ofNullable(wrapProductDOM.select(rules.getSelectorComposition()))
            .map(Elements::first)
            .ifPresent(element -> product.setComposition(element.ownText()));

        Optional.ofNullable(wrapProductDOM.select(rules.getSelectorSummary()))
            .map(Elements::first)
            .ifPresent(element -> product.setSummary(element.ownText()));

        Optional.ofNullable(wrapProductDOM.select(rules.getSelectorDescription()))
            .map(Elements::first)
            .ifPresent(element -> product.setDescription(element.text()));

        Optional.ofNullable(wrapProductDOM.select(rules.getSelectorPrice()))
            .map(Elements::first)
            .ifPresent(element -> product.setPrice(element.ownText()));

        Optional.ofNullable(wrapProductDOM.select(rules.getSelectorOldPrice()))
            .map(Elements::first)
            .ifPresent(element -> product.setOldPrice(element.ownText()));

        return product;
    }

    private void handleSelector(SelectorProductField selector, Element wrapProductDOM, Product product, String rootUrl) {
        Element targetElement = wrapProductDOM.getAllElements().select(selector.getSelector()).first();
        switch (selector.getField()) {
            case "name": {
                product.setName(targetElement.ownText());
                break;
            }
            case "price": {
                product.setPrice(targetElement.ownText());
                break;
            }
            case "image": {
                String src = targetElement.attr("src");
                src = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, src));
                product.setImageUrl(src);
                break;
            }
        }

    }

    private Long convertStringToPrice(String strPrice){
        String integerPart;
        String fractionalPart;
        for (int i = 0; i < strPrice.length(); i++){

        }
        return null;
    }

    private void buildChildrenCategory(Element wrapDOM, Category parentCategory, RuleExtractCategories extractCategoryRule, Shop shop) {

        wrapDOM.addClass("test");
        Elements elements = wrapDOM.getAllElements().select(".test >" + extractCategoryRule.getSelector());
        for (Element wrapCategory : elements) {
            Category currentCategory = extractCategoryFrom(wrapCategory, extractCategoryRule.getShop().getUrl(), shop);
            currentCategory.setParent(parentCategory);
            parentCategory.addChild(currentCategory);
            if (extractCategoryRule.getChild() != null) {
                buildChildrenCategory(wrapCategory, currentCategory, extractCategoryRule.getChild(), shop);
            }
        }
    }


    private RuleExtractCategories buildTreeCategoryRules(RulesExtractCategoriesVM rules) {
        RuleExtractCategories result = null;
        RuleExtractCategories prevCategory = null;
        for (RulesExtractCategoriesVM.RuleLevelCategory levelCategory : rules.getRuleCategories()) {
            if (result == null) {
                Shop shop = new Shop();
                shop.setUrl(rules.getShop().getUrl());
                result = new RuleExtractCategories(levelCategory.getSelector(), shop);
                prevCategory = result;
            } else {
                Shop shop = new Shop();
                shop.setUrl(rules.getShop().getUrl());
                RuleExtractCategories currentCategory = new RuleExtractCategories(levelCategory.getSelector(), shop);
                currentCategory.setParent(prevCategory);
                prevCategory.setChild(currentCategory);
                prevCategory = currentCategory;
            }
        }
        return result;
    }


    @Transactional
    public void updateData(){
        List<Shop> shops = shopRepository.findAvailableShop();
        for (Shop shop : shops) {
            updateDataForShop(shop.getId());
        }
    }

    @Transactional
    public void updateDataForShop(Long id){
        RuleExtractCategories rulesExtractCategories = ruleExtractCategoriesService.getTreeRulesExtractCategoriesBelongsShop(id);
        List<Category> categories = buildCategories(rulesExtractCategories);
        categoryService.updateCategories(categories);

        RuleExtractProductLink rulesExtractProductLink = ruleExtractProductLinkService.getRuleBelongsShop(id);
        List<ProductLink> productLinks = buildProductLinks(rulesExtractProductLink,categories);


        RuleExtractProduct ruleExtractProduct = ruleExtractProductService.getRuleBelongsShop(id);
        List<Product> products = buildProduct(ruleExtractProduct, productLinks);
        productService.updateProduct(products);
    }

}
