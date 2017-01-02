package com.mycompany.myapp.service.bigcity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.myapp.domain.dataparsing.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 29.12.2016.
 */
public class CategoryFormatBigCity {
    private String externalId;
    private String name;
    private Long priority;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CategoryFormatBigCity> subCategories;

    @JsonIgnore
    private Category category;

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

    public List<CategoryFormatBigCity> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryFormatBigCity> subCategories) {
        this.subCategories = subCategories;
    }

    public void addSubCategory(CategoryFormatBigCity subCategory){
        subCategories = subCategories == null? new ArrayList<>() : subCategories;
        subCategories.add(subCategory);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
