package com.mycompany.myapp.web.rest.vmrules;

import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import com.mycompany.myapp.web.rest.vmbigcity.ShopVM;

/**
 * Created by Vladimir on 03.01.2017.
 */
public class RuleExtractProductLinkVM {

    private ShopVM shop;
    private String selector;


    public RuleExtractProductLinkVM() {
    }

    public RuleExtractProductLinkVM(RuleExtractProductLink rules) {
        shop = new ShopVM(rules.getShop());
        selector = rules.getSelector();
    }

    public ShopVM getShop() {
        return shop;
    }

    public void setShop(ShopVM shop) {
        this.shop = shop;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }


}
