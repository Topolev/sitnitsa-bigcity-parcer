package com.mycompany.myapp.web.rest.vmbigcity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RulesToExtractProductVM {

    private String rootUrl;
    private Long idShop;
    private ProductLink[] productLinks;
    private List<SelectorProduct> selectors = new ArrayList<>();

    public RulesToExtractProductVM(){}

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public List<SelectorProduct> getSelectors() {
        return selectors;
    }

    public void setSelectors(List<SelectorProduct> selectors) {
        this.selectors = selectors;
    }

    public ProductLink[] getProductLinks() {
        return productLinks;
    }

    public Long getIdShop() {
        return idShop;
    }
}


