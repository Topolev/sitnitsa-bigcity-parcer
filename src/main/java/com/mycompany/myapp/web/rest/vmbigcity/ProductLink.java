package com.mycompany.myapp.web.rest.vmbigcity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.myapp.domain.dataparsing.Category;
import org.jsoup.nodes.Element;

/**
 * Created by Vladimir on 28.12.2016.
 */
public class ProductLink {
    Category category;

    @JsonIgnore
    Element elementDOM;

    String originUrl;

    public ProductLink(){};

    public ProductLink(String originalUrl, Element elementDOM, Category category){
        this.originUrl = originalUrl;
        this.elementDOM = elementDOM;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Element getElementDOM() {
        return elementDOM;
    }

    public void setElementDOM(Element elementDOM) {
        this.elementDOM = elementDOM;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }
}
