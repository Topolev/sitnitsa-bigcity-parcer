package com.mycompany.myapp.web.rest.vmrules;


import com.mycompany.myapp.domain.enums.CategoryChildPlace;
import com.mycompany.myapp.domain.rules.RuleExtractCategories;
import com.mycompany.myapp.web.rest.vmbigcity.ShopVM;

import java.util.ArrayList;
import java.util.List;

public class RulesExtractCategoriesVM {

    private ShopVM shop;


    private List<RuleLevelCategory> ruleCategories;

    public RulesExtractCategoriesVM(){}

    public RulesExtractCategoriesVM(RuleExtractCategories ruleExtractCategories){
        ruleCategories = new ArrayList<>();
        RuleExtractCategories currentLevelRule = ruleExtractCategories;
        shop = new ShopVM(ruleExtractCategories.getShop());
        while(currentLevelRule != null){
            ruleCategories.add(new RuleLevelCategory(currentLevelRule.getSelector(), currentLevelRule.getPrefix(), currentLevelRule.getChildPlace()));
            currentLevelRule = currentLevelRule.getChild();
        }
    }



    public static class RuleLevelCategory {
        private String selector;
        private String prefix;
        private CategoryChildPlace childPlace;

        public RuleLevelCategory(){}

        public RuleLevelCategory(String selector){
            this.selector = selector;
        }

        public RuleLevelCategory(String selector, String prefix, CategoryChildPlace childPlace){
            this.selector = selector;
            this.prefix = prefix;
            this.childPlace = childPlace;
        }

        public String getSelector() {
            return selector;
        }

        public void setSelector(String selector) {
            this.selector = selector;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public CategoryChildPlace getChildPlace() {
            return childPlace;
        }

        public void setChildPlace(CategoryChildPlace childPlace) {
            this.childPlace = childPlace;
        }
    }

    public ShopVM getShop() {
        return shop;
    }

    public void setShop(ShopVM shop) {
        this.shop = shop;
    }

    public List<RuleLevelCategory> getRuleCategories() {
        return ruleCategories;
    }

    public void setRuleCategories(List<RuleLevelCategory> ruleCategories) {
        this.ruleCategories = ruleCategories;
    }

}
