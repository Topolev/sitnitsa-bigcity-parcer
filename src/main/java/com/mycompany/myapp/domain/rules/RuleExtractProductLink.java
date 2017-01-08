package com.mycompany.myapp.domain.rules;

import com.mycompany.myapp.domain.dataparsing.Shop;

import javax.persistence.*;

@Entity
@Table(name="rule_extract_product_link")
public class RuleExtractProductLink {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "selector")
    private String selector;

    @Column(name="paginator_template")
    private String paginatorTemplate;

    @Column(name="paginator_start_page")
    private Long paginatorStartPage;

    @Column(name="paginator_step_change")
    private Long paginatorStepChange;


    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public RuleExtractProductLink(){};

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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
