package com.mycompany.myapp.web.rest.vmbigcity;

/**
 * Created by Vladimir on 19.12.2016.
 */
public class RulesToExtractCategoryVM {
    private Long idShop;
    private String rootUrl;
    private String prefix;
    private String excludeUrls;
    private String selector;

    public RulesToExtractCategoryVM(){}

    public Long getIdShop() {
        return idShop;
    }

    public void setIdShop(Long idShop) {
        this.idShop = idShop;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }
}
