package com.mycompany.myapp.web.rest.vmbigcity;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class SelectorProductField {
    String field;
    String selector;

    public SelectorProductField(){}
    public SelectorProductField(String field, String selector){
        this.field = field;
        this.selector = selector;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
}
