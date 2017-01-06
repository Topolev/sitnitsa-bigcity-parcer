package com.mycompany.myapp.web.rest.vmrules;


import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.web.rest.vmbigcity.ShopVM;


public class WrapAllRulesVM {


    private ShopVM shop;
    private RulesExtractCategoriesVM rulesExtractCategories;
    private RuleExtractProductLinkVM ruleExtractProductLink;
    private RuleExtractProductVM ruleExtractProduct;

    public WrapAllRulesVM(){}


    public ShopVM getShop() {
        return shop;
    }

    public void setShop(ShopVM shop) {
        this.shop = shop;
    }

    public RulesExtractCategoriesVM getRulesExtractCategories() {
        return rulesExtractCategories;
    }

    public void setRulesExtractCategories(RulesExtractCategoriesVM rulesExtractCategories) {
        this.rulesExtractCategories = rulesExtractCategories;
    }

    public RuleExtractProductLinkVM getRuleExtractProductLink() {
        return ruleExtractProductLink;
    }

    public void setRuleExtractProductLink(RuleExtractProductLinkVM ruleExtractProductLink) {
        this.ruleExtractProductLink = ruleExtractProductLink;
    }

    public RuleExtractProductVM getRuleExtractProduct() {
        return ruleExtractProduct;
    }

    public void setRuleExtractProduct(RuleExtractProductVM ruleExtractProduct) {
        this.ruleExtractProduct = ruleExtractProduct;
    }
}
