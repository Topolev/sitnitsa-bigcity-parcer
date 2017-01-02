package com.mycompany.myapp.web.rest.vm;

/**
 * Created by Vladimir on 17.12.2016.
 */
public class ExtractCategoryVM {
    private String url;
    private String mandotoryPartUrl;
    private String excludeUrl;
    private String selector;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMandotoryPartUrl() {
        return mandotoryPartUrl;
    }

    public void setMandotoryPartUrl(String mandotoryPartUrl) {
        this.mandotoryPartUrl = mandotoryPartUrl;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getExcludeUrl() {
        return excludeUrl;
    }

    public void setExcludeUrl(String excludeUrl) {
        this.excludeUrl = excludeUrl;
    }
}
