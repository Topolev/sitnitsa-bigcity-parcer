package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.enums.StatusProduct;
import com.mycompany.myapp.domain.rules.RuleExtractCategories;
import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;
import com.mycompany.myapp.service.bigcity.ProductLink;

import com.mycompany.myapp.service.util.StringBigCityUtil;
import com.mycompany.myapp.web.rest.vmrules.RulesExtractCategoriesVM;
import com.mycompany.myapp.web.rest.vmbigcity.SelectorProductField;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

import static com.mycompany.myapp.service.util.ExternalPageUtil.*;
import static com.mycompany.myapp.service.util.StringBigCityUtil.*;
import static com.mycompany.myapp.service.util.StringBigCityUtil.deleteRootUrl;
import static com.mycompany.myapp.service.util.StringBigCityUtil.deleteSlashFromBeginAndEnd;
import static org.apache.commons.lang3.StringUtils.*;

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


    public static class WrapPriority{
        private Long priority;
        public WrapPriority(){
            priority = 1L;
        }

        public Long getPriority() {
            return priority;
        }

        public void setPriority(Long priority) {
            this.priority = priority;
        }

        public Long increment(){
            return priority++;
        }
    }


    public List<Category> buildCategories(RuleExtractCategories rules, Shop currentShop) {
        List<Category> categories = new ArrayList<>();
        //Shop currentShop = shopRepository.findOne(rules.getShop().getId());
        WrapPriority priority = new WrapPriority();
        RuleExtractCategories currentRule = rules;
        try {
            Document pageDOM = getPageDOM(rules.getShop().getUrl());
            Elements currentCategories = pageDOM.body().select(currentRule.getSelector());
            for (Element wrapCategory : currentCategories) {
                Category parentCategory = extractCategoryFrom(wrapCategory, rules.getShop().getUrl(), currentShop);
                parentCategory.setPriority(priority.increment());
                categories.add(parentCategory);
                if (currentRule.getChild() != null) {
                    buildChildrenCategory(wrapCategory, parentCategory, currentRule.getChild(), currentShop, priority);
                }
            }
        } catch (IOException e) {
            LOG.debug("Cannot get page with url '{}'", e);
        }
        return categories;
    }


    public List<ProductLink> buildProductLinks(RuleExtractProductLink rules, List<Category> categories, boolean isTest) {
        List<ProductLink> productLinks = new ArrayList<>();
        List<Category> categoriesWithoutChildren = categoryService.getCategoriesWithoutChildren(categories);
        WrapPriority priority = new WrapPriority();
        for (Category category : categoriesWithoutChildren) {
            String currentUrl = rules.getShop().getUrl() + category.getHref();
            if (isNotBlank(rules.getPaginatorTemplate())) {
                int i = rules.getPaginatorStartPage().intValue();
                int step = rules.getPaginatorStepChange().intValue();
                boolean isExcitedNextPage = true;
                do {
                    String pageUrl = currentUrl + rules.getPaginatorTemplate().replace("{d}", String.valueOf(i));
                    isExcitedNextPage = extractProductLinksFromPage(pageUrl, rules, category, productLinks, priority);
                    i = i + step;
                } while (isExcitedNextPage);

            } else {
                extractProductLinksFromPage(currentUrl, rules, category, productLinks, priority);
            }
            if (isTest) break;
        }
        return productLinks;
    }

    private boolean extractProductLinksFromPage(String pageUrl, RuleExtractProductLink rules, Category category, List<ProductLink> productLinks, WrapPriority priority) {
        try {
            Document pageDOM = getPageDOM(pageUrl);
            Elements links = pageDOM.body().select(rules.getSelector());
            if (links.size() == 0) return false;
            for (Element link : links) {
                String productName = link.ownText();
                String productHref = deleteSlashFromBeginAndEnd(deleteRootUrl(rules.getShop().getUrl(), link.attr("href")));
                ProductLink newProductLink = new ProductLink(productName, productHref, category, priority.increment());

                for (ProductLink productLink : productLinks) {
                    if (productLink.getUrl().equals(newProductLink.getUrl())) {
                        return false;
                    }
                }

                productLinks.add(newProductLink);
            }
        } catch (IOException e) {
            LOG.debug("Cannot get page with url '{}'", pageUrl, e);
            return false;
        }
        return true;
    }


    public List<Product> buildProduct(RuleExtractProduct rules, List<ProductLink> links, boolean isTest) {
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
                product.setPriority(link.getPriority());
                products.add(product);
                if (isTest) {
                    if (products.size() > 9) break;
                }
            } catch (IOException e) {
                LOG.debug("Cannot get page with url '{}'", currentUrl, e);
            }
        }
        return products;
    }

    private Category extractCategoryFrom(Element wrap, String rootUrl, Shop shop) {
        Elements elementsByTagA = wrap.getAllElements().select("a");
        Element element = elementsByTagA.get(0);
        String nameCategory = deleteHtmlTags(element.text());
        String href = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, element.attr("href")));
        return new Category(nameCategory, deleteSlashFromBeginAndEnd(href), shop);
    }

    private Product extractProductFrom(Element wrapProductDOM, RuleExtractProduct rules, String rootUrl) {
        Product product = new Product();

        findElement(wrapProductDOM, rules.getSelectorName())
            .ifPresent(element -> product.setName(element.ownText()));


        findElement(wrapProductDOM, rules.getSelectorImage())
            .ifPresent(element -> {
                //String src = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, element.attr("src")));
                String src = element.attr("src").equals("") ? element.attr("href") : element.attr("src");
                src = deleteSlashFromBeginAndEnd(deleteRootUrl(rootUrl, src));
                product.setImageUrl(src);
            });

        findElement(wrapProductDOM, rules.getSelectorComposition())
            .ifPresent(element -> product.setComposition(element.ownText()));

        findElement(wrapProductDOM, rules.getSelectorSummary())
            .ifPresent(element -> product.setSummary(element.ownText()));

        findElement(wrapProductDOM, rules.getSelectorDescription())
            .ifPresent(element -> product.setDescription(element.text()));

        findElement(wrapProductDOM, rules.getSelectorPrice())
            .ifPresent(element -> product.setPrice(convertStrToPrice(element.ownText())));

        findElement(wrapProductDOM, rules.getSelectorOldPrice())
            .ifPresent(element -> product.setOldPrice(convertStrToPrice(element.ownText())));

        return product;
    }

    private Optional<Element> findElement(Element wrapProductDOM, String rules) {
        return Optional.ofNullable(rules)
            .filter(StringUtils::isNotBlank)
            .map(wrapProductDOM::select)
            .map(Elements::first);
    }



/*
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

    }*/

    private Long convertStringToPrice(String strPrice) {
        String integerPart;
        String fractionalPart;
        for (int i = 0; i < strPrice.length(); i++) {

        }
        return null;
    }

    private void buildChildrenCategory(Element wrapDOM, Category parentCategory, RuleExtractCategories extractCategoryRule, Shop shop, WrapPriority priority) {

        wrapDOM.addClass("test");
        Elements elements = wrapDOM.getAllElements().select(".test >" + extractCategoryRule.getSelector());
        for (Element wrapCategory : elements) {
            Category currentCategory = extractCategoryFrom(wrapCategory, extractCategoryRule.getShop().getUrl(), shop);
            currentCategory.setPriority(priority.increment());
            currentCategory.setParent(parentCategory);
            parentCategory.addChild(currentCategory);
            if (extractCategoryRule.getChild() != null) {
                buildChildrenCategory(wrapCategory, currentCategory, extractCategoryRule.getChild(), shop, priority);
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
    public void updateData() {
        List<Shop> shops = shopRepository.findAvailableShop();
        for (Shop shop : shops) {
            updateDataForShop(shop.getId());
        }
    }

    @Transactional
    public void updateDataForShop(Long id) {
        RuleExtractCategories rulesExtractCategories = ruleExtractCategoriesService.getTreeRulesExtractCategoriesBelongsShop(id);

        Shop currentShop = shopRepository.findOne(id);

        List<Category> categories = buildCategories(rulesExtractCategories, currentShop);
        categoryService.updateCategories(categories);

        RuleExtractProductLink rulesExtractProductLink = ruleExtractProductLinkService.getRuleBelongsShop(id);
        List<ProductLink> productLinks = buildProductLinks(rulesExtractProductLink, categories, false);


        RuleExtractProduct ruleExtractProduct = ruleExtractProductService.getRuleBelongsShop(id);
        List<Product> products = buildProduct(ruleExtractProduct, productLinks, false);
        productService.updateProduct(products);
    }

}
