package com.mycompany.myapp.web.rest.vmbigcity;

import org.jsoup.nodes.Element;

/**
 * Created by Vladimir on 19.12.2016.
 */
public class CategoryLink {
    private String name;
    private String href;
    private Element elementDOM;

    public CategoryLink(){}

    public CategoryLink(String name, String href, Element elementDOM){
        this.name = name;
        this.href = href;
        this.elementDOM = elementDOM;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Element getElementDOM() {
        return elementDOM;
    }

    public void setElementDOM(Element elementDOM) {
        this.elementDOM = elementDOM;
    }
}
