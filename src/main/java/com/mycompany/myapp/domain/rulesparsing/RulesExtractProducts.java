package com.mycompany.myapp.domain.rulesparsing;

import com.mycompany.myapp.domain.dataparsing.Shop;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rules_extract_products")
public class RulesExtractProducts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="selector")
    private String selector;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

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
