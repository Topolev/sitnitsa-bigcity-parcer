package com.mycompany.myapp.web.rest.vmbigcity;

import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.enums.StatusShop;

import java.time.ZonedDateTime;

/**
 * Created by Vladimir on 21.12.2016.
 */
public class ShopVM {

    private Long id;
    private Long idUrl;
    private String name;
    private String url;
    private StatusShop status;
    private String urlToGetShopInfo;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;

    public ShopVM(){}

    public ShopVM(Shop shop){
        this(shop.getId(), shop.getIdUrl(), shop.getName(), shop.getUrl(), shop.getStatus(), shop.getCreatedDate(), shop.getUpdatedDate());
    }

    public ShopVM(Long id, Long idUrl, String name, String url, StatusShop status, ZonedDateTime createdDate, ZonedDateTime updatedDate){
        this.id = id;
        this.idUrl = idUrl;
        this.name = name;
        this.url = url;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.urlToGetShopInfo = "/infoShop/" + id;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public StatusShop getStatus() {
        return status;
    }

    public void setStatus(StatusShop status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUrlToGetShopInfo() {
        return urlToGetShopInfo;
    }

    public void setUrlToGetShopInfo(String urlToGetShopInfo) {
        this.urlToGetShopInfo = urlToGetShopInfo;
    }

    public Long getIdUrl() {
        return idUrl;
    }

    public void setIdUrl(Long idUrl) {
        this.idUrl = idUrl;
    }
}
