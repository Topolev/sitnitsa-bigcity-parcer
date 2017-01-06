package com.mycompany.myapp.web.bigcity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.domain.enums.StatusProduct;
import com.mycompany.myapp.web.rest.entitybigcity.ItemStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class ProductFormatBigCityVM {

    String externalId;
    String name;

    @JsonInclude(NON_NULL)
    String imageUrl;

    @JsonInclude(NON_NULL)
    String composition;

    @JsonInclude(NON_NULL)
    String summary;

    @JsonInclude(NON_NULL)
    String description;

    @JsonInclude(NON_NULL)
    String price;

    @JsonInclude(NON_NULL)
    String oldPrice;

    StatusProduct status;

    Long priority;

    String categoryExternalId;


    public ProductFormatBigCityVM(){}

    public ProductFormatBigCityVM(Product product){
        this.externalId = product.getId().toString();
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
        this.composition = product.getComposition();
        this.summary = product.getSummary();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.oldPrice = product.getOldPrice();
        this.status = product.getStatus();
        this.priority = product.getPriority();
        this.categoryExternalId = product.getCategory().getId().toString();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public StatusProduct getStatus() {
        return status;
    }

    public void setStatus(StatusProduct status) {
        this.status = status;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getCategoryExternalId() {
        return categoryExternalId;
    }

    public void setCategoryExternalId(String categoryExternalId) {
        this.categoryExternalId = categoryExternalId;
    }
}
