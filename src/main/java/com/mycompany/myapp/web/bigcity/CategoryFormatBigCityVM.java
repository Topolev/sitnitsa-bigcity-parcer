package com.mycompany.myapp.web.bigcity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.myapp.domain.dataparsing.Category;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

/**
 * Created by Vladimir on 02.01.2017.
 */
public class CategoryFormatBigCityVM {

    String externalId;
    String name;
    Long priority;

    @JsonInclude(NON_NULL)
    List<CategoryFormatBigCityVM> subCategories;

    public CategoryFormatBigCityVM(){}

    public CategoryFormatBigCityVM(Category category){
        this.externalId = category.getId().toString();
        this.name = category.getName();
        this.priority = category.getPriority();

        List<Category> children = category.getChildren();
        if (children != null && !children.isEmpty()){
            setSubCategories(createChildrenCategory(children));
        }

    }

    private List<CategoryFormatBigCityVM> createChildrenCategory(List<Category> categories){
        List<CategoryFormatBigCityVM> children = new ArrayList<>();
        for (Category category : categories) {
            children.add(new CategoryFormatBigCityVM(category));
        }
        return children;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public List<CategoryFormatBigCityVM> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryFormatBigCityVM> subCategories) {
        this.subCategories = subCategories;
    }
}
