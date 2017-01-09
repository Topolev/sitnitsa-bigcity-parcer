package com.mycompany.myapp.service;


import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import com.mycompany.myapp.repository.rules.RuleExtractProductLinkRepository;
import com.mycompany.myapp.web.rest.vmrules.RuleExtractProductLinkVM;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class RuleExtractProductLinkService {

    @Inject
    private RuleExtractProductLinkRepository ruleExtractProductLinkRepository;


    public RuleExtractProductLink getRuleBelongsShop(Long idShop) {
        return ruleExtractProductLinkRepository.findRuleBelongShop(idShop);
    }


    public RuleExtractProductLink createNewRules(RuleExtractProductLinkVM rulesVM, Shop shop) {
        RuleExtractProductLink rules = convert(rulesVM, new RuleExtractProductLink());
        rules.setShop(shop);
        return ruleExtractProductLinkRepository.save(rules);
    }


    public RuleExtractProductLink updateRules(RuleExtractProductLinkVM rulesVM, Shop shop) {
        RuleExtractProductLink rules = convert(rulesVM, new RuleExtractProductLink());
        rules.setShop(shop);
        return ruleExtractProductLinkRepository.save(rules);
    }

    public void deleteRuleBelongsShop(Long id){
        RuleExtractProductLink rule = ruleExtractProductLinkRepository.findRuleBelongShop(id);
        ruleExtractProductLinkRepository.delete(rule);
    }


    public RuleExtractProductLink updateRules(RuleExtractProductLinkVM rulesVM) {
        RuleExtractProductLink rules = ruleExtractProductLinkRepository.findRuleBelongShop(rulesVM.getShop().getId());
        rules.setSelector(rulesVM.getSelector());
        rules.setPaginatorTemplate(rules.getPaginatorTemplate());
        rules.setPaginatorStartPage(rules.getPaginatorStartPage());
        rules.setPaginatorStepChange(rules.getPaginatorStepChange());
        ruleExtractProductLinkRepository.save(rules);
        return rules;
    }

    public RuleExtractProductLink convert(RuleExtractProductLinkVM source, RuleExtractProductLink target) {
        Shop shop = new Shop();
        shop.setUrl(source.getShop().getUrl());
        shop.setStatus(source.getShop().getStatus());
        shop.setId(source.getShop().getId());

        target.setId(source.getId());
        target.setSelector(source.getSelector());
        target.setPaginatorTemplate(source.getPaginatorTemplate());
        target.setPaginatorStartPage(source.getPaginatorStartPage());
        target.setPaginatorStepChange(source.getPaginatorStepChange());
        target.setShop(shop);

        return target;
    }
}
