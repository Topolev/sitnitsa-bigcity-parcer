package com.mycompany.myapp.web.rest.vmrules;

import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import com.mycompany.myapp.web.rest.vmbigcity.ShopVM;

/**
 * Created by Vladimir on 03.01.2017.
 */
public class RuleExtractProductLinkVM {

    private ShopVM shop;
    private String selector;
    private String paginatorTemplate;
    private Long paginatorStartPage;
    private Long paginatorStepChange;


    public RuleExtractProductLinkVM() {
    }

    public RuleExtractProductLinkVM(RuleExtractProductLink rules) {
        shop = new ShopVM(rules.getShop());
        selector = rules.getSelector();
        paginatorTemplate = rules.getPaginatorTemplate();
        paginatorStartPage = rules.getPaginatorStartPage();
        paginatorStepChange = rules.getPaginatorStepChange();
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

    public String getPaginatorTemplate() {
        return paginatorTemplate;
    }

    public void setPaginatorTemplate(String paginatorTemplate) {
        this.paginatorTemplate = paginatorTemplate;
    }

    public Long getPaginatorStartPage() {
        return paginatorStartPage;
    }

    public void setPaginatorStartPage(Long paginatorStartPage) {
        this.paginatorStartPage = paginatorStartPage;
    }

    public Long getPaginatorStepChange() {
        return paginatorStepChange;
    }

    public void setPaginatorStepChange(Long paginatorStepChange) {
        this.paginatorStepChange = paginatorStepChange;
    }
}
