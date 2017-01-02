package com.mycompany.myapp.domain.rulesparsing;

import com.mycompany.myapp.domain.dataparsing.Shop;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rules_extract_categories")
public class RulesExtractCategories implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="prefix")
    private String prefix;



    @Column(name="exclude_urls")
    private String excludeUrls;


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

    public String getPrefix() {
        return prefix;
    }

    public String getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
