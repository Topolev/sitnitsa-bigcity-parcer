package com.mycompany.myapp.web.rest.vmrules;

import com.mycompany.myapp.domain.enums.ProductFields;
import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.web.rest.vmbigcity.SelectorProductField;
import com.mycompany.myapp.web.rest.vmbigcity.ShopVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 04.01.2017.
 */
public class RuleExtractProductVM {

    private Long id;
    private ShopVM shop;
    private List<SelectorProductField> selectors;

    public RuleExtractProductVM() {
    }

    public RuleExtractProductVM(RuleExtractProduct rules) {
        id = rules.getId();
        shop = new ShopVM(rules.getShop());

        selectors = new ArrayList<>();

        for (ProductFields field : ProductFields.values()) {
            switch (field) {
                case NAME: {
                    selectors.add(new SelectorProductField(field.nameField, rules.getSelectorName()));
                    break;
                }
                case IMAGE: {
                    selectors.add(new SelectorProductField(field.nameField, rules.getSelectorImage()));
                    break;
                }
                case COMPOSITION:{
                    selectors.add(new SelectorProductField(field.nameField, rules.getSelectorComposition()));
                    break;
                }
                case SUMMARY:{
                    selectors.add(new SelectorProductField(field.nameField, rules.getSelectorSummary()));
                    break;
                }
                case DESCRIPTION:{
                    selectors.add(new SelectorProductField(field.nameField, rules.getSelectorDescription()));
                    break;
                }
                case PRICE: {
                    selectors.add(new SelectorProductField(field.nameField, rules.getSelectorPrice()));
                    break;
                }
                case OLDPRICE:{
                    selectors.add(new SelectorProductField(field.nameField, rules.getSelectorOldPrice()));
                }
            }
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopVM getShop() {
        return shop;
    }

    public void setShop(ShopVM shop) {
        this.shop = shop;
    }

    public List<SelectorProductField> getSelectors() {
        return selectors;
    }

    public void setSelectors(List<SelectorProductField> selectors) {
        this.selectors = selectors;
    }
}






