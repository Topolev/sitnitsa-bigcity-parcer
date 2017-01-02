package com.mycompany.myapp.web.rest.busness;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class Link {

    private String name;
    private String href;

    public Link(){}

    public Link(String name, String href){
        this.name = name;
        this.href = href;
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
}
