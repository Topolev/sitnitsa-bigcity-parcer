package com.mycompany.myapp.web.rest.vm;

/**
 * Created by Vladimir on 17.12.2016.
 */
public class ExtractProductsVM {

    private String url;
    private String selector;
    private String attrPaginator;
    private Integer maxPaginator;
    private Integer minPaginator;
    private Integer intervalPaginator;

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

    public String getAttrPaginator() {
        return attrPaginator;
    }

    public void setAttrPaginator(String attrPaginator) {
        this.attrPaginator = attrPaginator;
    }

    public Integer getMaxPaginator() {
        return maxPaginator;
    }

    public void setMaxPaginator(Integer maxPaginator) {
        this.maxPaginator = maxPaginator;
    }

    public Integer getMinPaginator() {
        return minPaginator;
    }

    public void setMinPaginator(Integer minPaginator) {
        this.minPaginator = minPaginator;
    }

    public Integer getIntervalPaginator() {
        return intervalPaginator;
    }

    public void setIntervalPaginator(Integer intervalPaginator) {
        this.intervalPaginator = intervalPaginator;
    }
}
