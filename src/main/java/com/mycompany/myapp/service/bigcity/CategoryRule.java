package com.mycompany.myapp.service.bigcity;

/**
 * Created by Vladimir on 02.01.2017.
 */
public class CategoryRule {

    String selector;
    CategoryRule children;

    public CategoryRule(){};

    public CategoryRule(String selector){
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public CategoryRule getChildren() {
        return children;
    }

    public void setChildren(CategoryRule children) {
        this.children = children;
    }
}
