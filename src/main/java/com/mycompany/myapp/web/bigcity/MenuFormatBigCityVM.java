package com.mycompany.myapp.web.bigcity;

import java.util.List;


public class MenuFormatBigCityVM {
    List<CategoryFormatBigCityVM> categories;
    List<ProductFormatBigCityVM> items;

    public MenuFormatBigCityVM(){}

    public MenuFormatBigCityVM(List<CategoryFormatBigCityVM> categories, List<ProductFormatBigCityVM> items){
        this.categories = categories;
        this.items = items;
    }

    public List<CategoryFormatBigCityVM> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryFormatBigCityVM> categories) {
        this.categories = categories;
    }

    public List<ProductFormatBigCityVM> getItems() {
        return items;
    }

    public void setItems(List<ProductFormatBigCityVM> items) {
        this.items = items;
    }
}
