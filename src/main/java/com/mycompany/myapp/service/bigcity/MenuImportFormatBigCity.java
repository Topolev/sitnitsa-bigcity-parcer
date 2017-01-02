package com.mycompany.myapp.service.bigcity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.myapp.domain.dataparsing.Product;

import java.util.List;


public class MenuImportFormatBigCity {

    private List<CategoryFormatBigCity> categories;

    private List<Product> items;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AdditionsSetsFormatBogCity> additionsSets;

    public MenuImportFormatBigCity(){}

    public List<CategoryFormatBigCity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryFormatBigCity> categories) {
        this.categories = categories;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public List<AdditionsSetsFormatBogCity> getAdditionsSets() {
        return additionsSets;
    }

    public void setAdditionsSets(List<AdditionsSetsFormatBogCity> additionsSets) {
        this.additionsSets = additionsSets;
    }
}
