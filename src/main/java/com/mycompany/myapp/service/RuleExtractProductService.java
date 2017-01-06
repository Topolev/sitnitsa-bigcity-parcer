package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.repository.rules.RuleExtractProductRepository;
import com.mycompany.myapp.web.rest.vmbigcity.SelectorProductField;
import com.mycompany.myapp.web.rest.vmrules.RuleExtractProductVM;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class RuleExtractProductService {

    @Inject
    private RuleExtractProductRepository ruleExtractProductRepository;


    public RuleExtractProduct getRuleBelongsShop(Long idShop){
        return ruleExtractProductRepository.findRuleBelongShop(idShop);
    }


    public RuleExtractProduct updateRules(RuleExtractProductVM rulesVM){
        RuleExtractProduct rules = ruleExtractProductRepository.findRuleBelongShop(rulesVM.getShop().getId());
        convert(rulesVM, rules);
        ruleExtractProductRepository.save(rules);
        return rules;
    }

    public RuleExtractProduct convert(RuleExtractProductVM source, RuleExtractProduct target){

        for (SelectorProductField selectorField : source.getSelectors()){
            switch (selectorField.getField()){
                case "name" :{
                    target.setSelectorName(selectorField.getSelector());
                    break;
                }
                case "image" :{
                    target.setSelectorImage(selectorField.getSelector());
                    break;
                }
                case "price" : {
                    target.setSelectorPrice(selectorField.getSelector());
                    break;
                }
            }
        }

        Shop shop = new Shop();
        shop.setId(source.getShop().getId());
        shop.setUrl(source.getShop().getUrl());
        shop.setStatus(source.getShop().getStatus());
        target.setShop(shop);

        return target;
    }



}

