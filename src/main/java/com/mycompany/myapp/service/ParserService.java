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

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.service.util.ExternalPageUtil.*;
import static com.mycompany.myapp.service.util.StringUrlUtil.deleteRootUrl;
import static com.mycompany.myapp.service.util.StringUrlUtil.deleteSlashFromBeginAndEnd;

@Service
public class ParserService {

    private static final Logger LOG = LoggerFactory.getLogger(ParserService.class);

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private CategoryService categoryService;

    @Inject
    private ShopRepository shopRepository;

    private Category extractCategoryFrom(Element wrap, String rootUrl, Shop shop) {
        Elements elementsByTagA = wrap.getAllElements().select("a");
        Element element = elementsByTagA.get(0);
        String nameCategory = element.ownText();
        String href = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, element.attr("href")));
        return new Category(nameCategory, deleteSlashFromBeginAndEnd(href), shop);
    }

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



    private Product extractProductFrom(Element wrapProductDOM, RuleExtractProduct rules, String rootUrl){
        Product product = new Product();

        Element nameProductDOM = wrapProductDOM.select(rules.getSelectorName()).first();
        product.setName(nameProductDOM.ownText());

        Element priceProductDOM = wrapProductDOM.select(rules.getSelectorPrice()).first();
        product.setPrice(priceProductDOM.ownText());

        Element imageProductDOM = wrapProductDOM.select(rules.getSelectorImage()).first();
        String src = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, imageProductDOM.attr("src")));
        product.setImageUrl(src);

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

}
