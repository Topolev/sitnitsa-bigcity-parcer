package com.mycompany.myapp.web.rest.vmbigcity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by Vladimir on 19.12.2016.
 */
public class CategoryVM {
    String externalId;
    String name;
    Long priority;
    int level;
    private String handleUrl;
    private String originalUrl;
    private String prefix;
    private String idParent;

    @JsonInclude(NON_NULL)
    List<CategoryVM> subCategories;


    List<String> idSubCategories = new ArrayList<>();






    public CategoryVM() {}

    public CategoryVM(String externalId, String name, int level, String handleUrl, String originalUrl, String prefix){
        this.externalId = externalId;
        this.name = name;
        this.level = level;
        this.handleUrl = handleUrl;
        this.originalUrl = originalUrl;
        this.prefix = prefix;

    }

    public CategoryVM(String externalId){
        this.externalId = externalId;
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

    public List<CategoryVM> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryVM> subCategories) {
        this.subCategories = subCategories;
    }

    public String getHandleUrl() {
        return handleUrl;
    }

    public void setHandleUrl(String handleUrl) {
        this.handleUrl = handleUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void addSubCategory(CategoryVM category) {
        if (this.subCategories == null) {
            this.subCategories = new ArrayList<>();
        }
        this.subCategories.add(category);
    }

    public List<String> getIdSubCategories() {
        return idSubCategories;
    }

    public void setIdSubCategories(List<String> idSubCategories) {
        this.idSubCategories = idSubCategories;
    }

    public void addIdSubCategory(String idSubCategory){
        if (this.idSubCategories == null ){
            this.idSubCategories = new ArrayList<>();
        }
        this.idSubCategories.add(idSubCategory);
    }


    public void setIdSubCategory(List<String> idSubCategories) {
        this.idSubCategories = idSubCategories;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryVM category = (CategoryVM) o;

        return getExternalId().equals(category.getExternalId());

    }

    @Override
    public int hashCode() {
        return getExternalId().hashCode();
    }
}
