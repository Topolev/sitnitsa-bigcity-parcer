package com.mycompany.myapp.web.rest.vm;

/**
 * Created by Vladimir on 17.12.2016.
 */
public class CheckSelectorVM {
    private String url;
    private String selector;

    public CheckSelectorVM(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
}
