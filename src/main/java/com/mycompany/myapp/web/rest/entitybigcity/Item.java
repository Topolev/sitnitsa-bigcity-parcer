package com.mycompany.myapp.web.rest.entitybigcity;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class Item {
    String externalId;
    String name;
    String imageUrl;
    String composition;
    String summary;
    String description;
    Float price;
    Float oldPrice;
    ItemStatus status;
    String categoryExternalId;

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getCategoryExternalId() {
        return categoryExternalId;
    }

    public void setCategoryExternalId(String categoryExternalId) {
        this.categoryExternalId = categoryExternalId;
    }
}
