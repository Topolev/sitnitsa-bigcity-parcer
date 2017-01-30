package com.mycompany.myapp.web.rest.vmrules;

import com.mycompany.myapp.domain.dataparsing.Category;

/**
 * Created by Vladimir on 30.01.2017.
 */
public class WrapRuleExtractProductLinkVM {

    private Category category;
    private RuleExtractProductLinkVM ruleExtractProductLink;

    public WrapRuleExtractProductLinkVM() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public RuleExtractProductLinkVM getRuleExtractProductLink() {
        return ruleExtractProductLink;
    }

    public void setRuleExtractProductLink(RuleExtractProductLinkVM ruleExtractProductLink) {
        this.ruleExtractProductLink = ruleExtractProductLink;
    }
}
