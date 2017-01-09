package com.mycompany.myapp.service.bigcity;

import com.mycompany.myapp.domain.dataparsing.Category;

/**
 * Created by Vladimir on 03.01.2017.
 */
public class ProductLink {

    private String name;
    private String url;
    private Category category;
    Long priority;

    public ProductLink(){}

    public ProductLink(String name, String url, Category category, Long priority){
        this.name = name;
        this.url = url;
        this.category = category;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }
}
