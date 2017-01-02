package com.mycompany.myapp.domain.rulesparsing;

import com.mycompany.myapp.domain.dataparsing.Shop;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rules_extract_product")
public class RulesExtractProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="selector_name")
    private String selectorName;

    @Column(name="selector_image")
    private String selectorImage;

    @Column(name="selector_composition")
    private String selectorComposition;

    @Column(name="selector_summary")
    private String selectorSummary;

    @Column(name="selector_description")
    private String selectorDescription;

    @Column(name="selector_price")
    private String selectorPrice;

    @Column(name="selector_oldprice")
    private String selectorOldprice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelectorName() {
        return selectorName;
    }

    public void setSelectorName(String selectorName) {
        this.selectorName = selectorName;
    }

    public String getSelectorImage() {
        return selectorImage;
    }

    public void setSelectorImage(String selectorImage) {
        this.selectorImage = selectorImage;
    }

    public String getSelectorComposition() {
        return selectorComposition;
    }

    public void setSelectorComposition(String selectorComposition) {
        this.selectorComposition = selectorComposition;
    }

    public String getSelectorSummary() {
        return selectorSummary;
    }

    public void setSelectorSummary(String selectorSummary) {
        this.selectorSummary = selectorSummary;
    }

    public String getSelectorDescription() {
        return selectorDescription;
    }

    public void setSelectorDescription(String selectorDescription) {
        this.selectorDescription = selectorDescription;
    }

    public String getSelectorPrice() {
        return selectorPrice;
    }

    public void setSelectorPrice(String selectorPrice) {
        this.selectorPrice = selectorPrice;
    }

    public String getSelectorOldprice() {
        return selectorOldprice;
    }

    public void setSelectorOldprice(String selectorOldprice) {
        this.selectorOldprice = selectorOldprice;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}







