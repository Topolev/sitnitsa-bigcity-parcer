package com.mycompany.myapp.domain.rules;

import com.mycompany.myapp.domain.dataparsing.Shop;

import javax.persistence.*;

@Entity
@Table(name="rule_extract_product_link")
public class RuleExtractProductLink {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "selector")
    private String selector;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public RuleExtractProductLink(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
