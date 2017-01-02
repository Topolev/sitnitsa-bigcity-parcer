package com.mycompany.myapp.web.rest.vmbigcity;

import com.mycompany.myapp.web.rest.entitybigcity.Category;

import java.util.List;

/**
 * Created by Vladimir on 21.12.2016.
 */
public class RulesToExtractProductLinksVM {
    String rootUrl;
    List<Category> categories;
    String selector;

    public RulesToExtractProductLinksVM(){}

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
}
