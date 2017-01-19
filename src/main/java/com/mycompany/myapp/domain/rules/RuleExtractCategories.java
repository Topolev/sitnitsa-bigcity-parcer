package com.mycompany.myapp.domain.rules;

import com.mycompany.myapp.domain.dataparsing.Shop;

import javax.persistence.*;

@Entity
@Table(name = "rule_extract_categories")
public class RuleExtractCategories {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "selector")
    private String selector;

    @Column(name = "prefix")
    private String prefix;


    @OneToOne
    @JoinColumn(name = "parent_id")
    private RuleExtractCategories parent;

    @OneToOne(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private RuleExtractCategories child;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public RuleExtractCategories() {

    }



    public RuleExtractCategories(String selector) {
        this.selector = selector;
    }



    public RuleExtractCategories(String selector, String prefix, Shop shop) {
        this.selector = selector;
        this.prefix = prefix;
        this.shop = shop;
    }

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

    public RuleExtractCategories getParent() {
        return parent;
    }

    public void setParent(RuleExtractCategories parent) {
        this.parent = parent;
    }

    public RuleExtractCategories getChild() {
        return child;
    }

    public void setChild(RuleExtractCategories child) {
        this.child = child;
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

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
