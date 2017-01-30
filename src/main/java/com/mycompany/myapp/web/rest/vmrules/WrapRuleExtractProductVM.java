package com.mycompany.myapp.web.rest.vmrules;

import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.service.bigcity.ProductLink;

/**
 * Created by Vladimir on 30.01.2017.
 */
public class WrapRuleExtractProductVM {

    private ProductLink link;
    private RuleExtractProductVM ruleExtractProduct;

    public WrapRuleExtractProductVM(){}

    public ProductLink getLink() {
        return link;
    }

    public void setLink(ProductLink link) {
        this.link = link;
    }

    public RuleExtractProductVM getRuleExtractProduct() {
        return ruleExtractProduct;
    }

    public void setRuleExtractProduct(RuleExtractProductVM ruleExtractProduct) {
        this.ruleExtractProduct = ruleExtractProduct;
    }
}
