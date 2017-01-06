package com.mycompany.myapp.domain.enums;

/**
 * Created by Vladimir on 04.01.2017.
 */
public enum ProductFields {
    NAME("name"),
    IMAGE("image"),
    PRICE("price");

    private ProductFields(String nameField) {
        this.nameField = nameField;
    }
    public String nameField;
}
