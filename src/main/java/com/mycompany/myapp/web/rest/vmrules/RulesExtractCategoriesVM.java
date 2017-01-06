package com.mycompany.myapp.web.rest.vmrules;


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
            ruleCategories.add(new RuleLevelCategory(currentLevelRule.getSelector()));
            currentLevelRule = currentLevelRule.getChild();
        }
    }



    public static class RuleLevelCategory {
        private String selector;

        public RuleLevelCategory(){}

        public RuleLevelCategory(String selector){
            this.selector = selector;
        }

        public String getSelector() {
            return selector;
        }

        public void setSelector(String selector) {
            this.selector = selector;
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
