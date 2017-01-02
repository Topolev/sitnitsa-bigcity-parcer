package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.domain.enums.StatusProduct;
import com.mycompany.myapp.web.rest.entitybigcity.Category;
import com.mycompany.myapp.web.rest.entitybigcity.Item;
import com.mycompany.myapp.web.rest.vm.CheckSelectorVM;
import com.mycompany.myapp.web.rest.vm.SelectorVM;
import com.mycompany.myapp.web.rest.vmbigcity.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.mycompany.myapp.service.util.StringUrlUtil.*;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Created by Vladimir on 17.12.2016.
 */

@Service
public class ParserShopService {

    private static final Logger LOG = LoggerFactory.getLogger(ParserShopService.class);

    @Inject
    private CategoryService categoryService;

    @Inject
    private ProductService productService;


    public boolean checkUrl(String urlString) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            if (connection.getResponseCode() == 404) {
                return false;
            }
        } catch (IOException e) {
            LOG.debug("Error during check url", e);
            return false;
        }
        return true;
    }


    public SelectorVM getOneSelector(CheckSelectorVM checkSelectorVM) {
        SelectorVM selectorVM = createSelectorVM(checkSelectorVM);
        try {
            Document html = getHTML(checkSelectorVM.getUrl());

            Elements selectors = html.body().select(checkSelectorVM.getSelector());
            if (selectors.size() > 0) {
                selectorVM.setExisted(true);
                selectorVM.setContent(selectors.get(0).toString());
            } else {
                selectorVM.setExisted(false);
            }
        } catch (IOException e) {
            selectorVM.setExisted(false);
        }
        return selectorVM;
    }


    private boolean isNotExcludedUrl(String url, Collection<String> excludedUrls){
        return excludedUrls.stream().noneMatch(url::contains);
    }


    private List<String> getListTemplatesExcludedUrls(RulesToExtractCategoryVM rules){
        if (isNotBlank(rules.getExcludeUrls())){
            return Arrays.asList(rules.getExcludeUrls().split(","));
        } else {
            return emptyList();
        }
    }

    private Predicate<Element> nonExcludedUrl(Collection<String> excludedUrls) {
        return element -> excludedUrls.isEmpty() || isNotExcludedUrl(element.attr("href"), excludedUrls);
    }

    private Function<Element,CategoryLink> toCategoryLink(){
        return el -> new CategoryLink(deleteHtmlTags(el.html()),deleteSlashFromBeginAndEnd(el.attr("href")), el);
    }

    public List<CategoryVM> extractCategories(RulesToExtractCategoryVM rules) {
        try {
            Collection<String> excludedUrls = getListTemplatesExcludedUrls(rules);
            List<CategoryLink> categoryLinks = getElementsBySelectorOnPage(rules.getRootUrl(), rules.getSelector()).stream()
                .filter(nonExcludedUrl(excludedUrls))
                .map(toCategoryLink())
                .collect(toList());
            List<CategoryVM> categories = createCategoriesFromLinks(categoryLinks, rules);
            categoryService.saveCategories(categories, rules.getIdShop());
            return categories;
        } catch (IOException e) {
            LOG.debug("There was problem during extracting categories from {}.", rules.getRootUrl(), e);
        }
        return emptyList();
    }


    private List<CategoryVM> createCategoriesFromLinks(List<CategoryLink> links, RulesToExtractCategoryVM rules) {
        List<CategoryVM> categories = new ArrayList<>();
        for (CategoryLink link : links) {
            String preparedHref = handleCategoryLink(link.getHref(), rules);
            String[] partsUrl = preparedHref.split("/");
            CategoryVM category = new CategoryVM(partsUrl[partsUrl.length - 1],link.getName(),partsUrl.length - 1,preparedHref, link.getHref(), rules.getPrefix());
            if (partsUrl.length >= 2) {
                int indexParent = categories.indexOf(new CategoryVM(partsUrl[partsUrl.length - 2]));
                if (indexParent != -1) {
                    CategoryVM parent = categories.get(indexParent);
                    category.setIdParent(parent.getExternalId());
                    parent.addIdSubCategory(category.getExternalId());
                }
            }
            categories.add(category);
        }
        return categories;
    }

    private String handleCategoryLink(String link, RulesToExtractCategoryVM rules){
        return deleteSlashFromBeginAndEnd(deleteExtension(deletePrefix(rules.getPrefix(),link)));

    }



    private Document getHTML(String urlPage) throws IOException {
        return Jsoup.parse(getHtmlPage(urlPage));
    }

    private String getEncodingPage(HttpURLConnection connection){
        return connection.getContentType().replaceFirst(".+charset\\s*=\\s*", "");
    }

    private String getHtmlPage(String urlString) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        String encoding = getEncodingPage(connection);
        BufferedReader in;
        try{
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
        } catch (UnsupportedEncodingException e){
            LOG.debug("Problem with extract encoding. Applying the UTF8.", e);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }

    private Elements getElementsBySelectorOnPage(String urlPage, String selector) throws IOException {
        Document html = getHTML(urlPage);
        return html.body().select(selector);
    }

    private SelectorVM createSelectorVM(CheckSelectorVM checkSelectorVM) {
        SelectorVM selectorVM = new SelectorVM();
        selectorVM.setUrl(checkSelectorVM.getUrl());
        selectorVM.setSelector(checkSelectorVM.getSelector());
        return selectorVM;
    }

/*
    public List<String> extractProductLinks(ExtractProductsVM extractProductsVM) {
        //SelectorVM selectorVM = createSelectorVM(checkSelectorVM);
        List<String> collectionLinks = new ArrayList<>();

        try {

            String url = extractProductsVM.getUrl();
            int start = extractProductsVM.getMinPaginator();
            int finish = extractProductsVM.getMaxPaginator();
            int interval = extractProductsVM.getIntervalPaginator();

            for (; start <= finish; start = start + interval) {
                String reg = "{" + extractProductsVM.getAttrPaginator() + "}";
                String currentUrl = url.replace(reg, Integer.toString(start));

                String HTMLpage = getHtmlPage(currentUrl);
                Document html = Jsoup.parse(HTMLpage);

                Elements selectContent = html.body().select(extractProductsVM.getSelector());
                for (Element element : selectContent) {
                    collectionLinks.add(element.attr("href"));
                }
                if (collectionLinks.size() > 20) break;
            }

        } catch (IOException e) {
            LOG.debug("Occured exception during extracting links");
        }
        return collectionLinks;
    }*/

    public SelectorVM getSelectorContent(CheckSelectorVM checkSelectorVM) {
        SelectorVM selectorVM = new SelectorVM();
        try {
            selectorVM.setUrl(checkSelectorVM.getUrl());
            selectorVM.setSelector(checkSelectorVM.getSelector());

            String HTMLpage = getHtmlPage(checkSelectorVM.getUrl());

            Document html = Jsoup.parse(HTMLpage);

            Elements selectContent = html.body().select(checkSelectorVM.getSelector());
            if (!selectContent.isEmpty()) {
                selectorVM.setExisted(true);
                selectorVM.setContent(selectContent.get(0).html());
            } else {
                selectorVM.setExisted(false);
            }
        } catch (IOException e) {
            selectorVM.setExisted(false);
        }
        return selectorVM;
    }


    public SelectorVM getImgUrl(CheckSelectorVM checkSelectorVM) {
        SelectorVM selectorVM = createSelectorVM(checkSelectorVM);
        try {

            String HTMLpage = getHtmlPage(checkSelectorVM.getUrl());

            Document html = Jsoup.parse(HTMLpage);

            Elements selectContent = html.body().select(checkSelectorVM.getSelector());
            if (selectContent.size() > 0) {
                selectorVM.setExisted(true);
                selectorVM.setContent(selectContent.get(0).attr("src"));
            } else {
                selectorVM.setExisted(false);
            }
        } catch (IOException e) {
            selectorVM.setExisted(false);
        }
        return selectorVM;
    }

    private List<Category> getCategoriesBelongLastLevel(List<Category> categoies){
        return categoies.stream().filter(category -> category.getIdSubCategories().size() == 0).collect(toList());
    }
    private Predicate<Category> lastLevelCategory(){
        return category -> category.getIdSubCategories().size() == 0;
    }

    private Function<Category,Elements> toDOMElementsOfURLProduct(RulesToExtractProductLinksVM rules){
        return category -> {
            String urlCategory = rules.getRootUrl() + category.getOriginalUrl();
            try {
                return getElementsBySelectorOnPage(urlCategory, rules.getSelector());
            } catch (IOException e) {
                LOG.debug("Problem with getting page {} ", urlCategory, e);
                return null;
            }
        };
    }

    private Function<Elements, List<ProductLink>> toProductLinks(){
        return elements -> {
            return elements.stream()
                .map(el -> new ProductLink())
                .collect(toList());
        };
    }

    public List<ProductLink> extractProductLinks(RulesToExtractProductLinksVM rules) {
        List<Category> categories = getCategoriesBelongLastLevel(rules.getCategories());
        List<ProductLink> links = new ArrayList<>();

        for (Category category : categories) {
            String urlCategory = rules.getRootUrl() + category.getOriginalUrl();
            try {
                getElementsBySelectorOnPage(urlCategory, rules.getSelector())
                    .forEach(element -> {
                        links.add(new ProductLink(element.attr("href"),element,category));
                    });
                /*if (links.size() > 10) break;*/
            } catch (IOException e) {
                LOG.debug("Problem with getting page {} ", urlCategory, e);
            }
        }
        return links;
    }

    private String getIdCategoryFromUrl(String url) {
        String[] partsUrl = url.split("/");
        if (partsUrl.length >= 2) {
            return partsUrl[partsUrl.length - 2];
        }
        return null;
    }

    public List<Product> extractProducts(RulesToExtractProductVM rules) {

        List<Product> products = new ArrayList<>();
        for (ProductLink productLink : rules.getProductLinks()) {

            String pageUrl = rules.getRootUrl() + productLink.getOriginUrl();
            LOG.debug("Request external page: {} ", pageUrl);
            try {
                Document html = getHTML(pageUrl);
                Product product = new Product();
                product.setStatus(StatusProduct.AVAILABLE);
                product.setCategoryExternalId(productLink.getCategory().getExternalId());
                for (SelectorProduct selector : rules.getSelectors()) {
                    if (!selector.getSelector().equals("")) {
                        handleSelector(html, selector, product, pageUrl, rules.getRootUrl());
                    }
                }
                products.add(product);


               /* if (products.size() > 10) break;*/


            } catch (IOException e) {
                LOG.debug("There was problem with access to page " + pageUrl, e);
            }
        }


        productService.saveProducts(products, rules.getIdShop());

        return products;
    }

    private void handleSelector(Document html, SelectorProduct selector, Product product, String url, String rootUrl) {
        Elements elements = html.body().select(selector.getSelector());

        if (elements.size() == 0) {
            LOG.debug("Can not find selector '{}' for product field '{}' in url {}", selector.getSelector(), selector.getField(), url);
            return;
        }
        Element element = elements.get(0);

        switch (selector.getField()) {
            case "name": {
                product.setName(element.ownText());
                break;
            }
            case "price": {
                product.setPrice(Long.valueOf(element.ownText().replaceAll("[^\\d.]+|\\.(?!\\d)", "")));
                break;
            }
            case "image": {
                String imageUrl = element.attr("src");
                imageUrl = imageUrl.replace(rootUrl, "");
                product.setImageUrl(imageUrl);
                break;
            }
        }
    }


}
