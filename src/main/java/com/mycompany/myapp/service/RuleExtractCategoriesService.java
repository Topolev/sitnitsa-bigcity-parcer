package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.rules.RuleExtractCategories;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;
import com.mycompany.myapp.repository.rules.RuleExtractCategoriesRepository;
import com.mycompany.myapp.web.rest.vmrules.RulesExtractCategoriesVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class RuleExtractCategoriesService {

    private static final Logger LOG = LoggerFactory.getLogger(RuleExtractCategoriesService.class);

    @Inject
    private ShopRepository shopRepository;

    @Inject
    private RuleExtractCategoriesRepository ruleExtractCategoriesRepository;


    public RuleExtractCategories getTreeRulesExtractCategoriesBelongsShop(Long idShop){
        return ruleExtractCategoriesRepository.findAllRulesBelongShop(idShop).stream()
            .findFirst()
            .orElse(null);
    }

    public RuleExtractCategories createNewRules(RulesExtractCategoriesVM rulesVM){
        RuleExtractCategories rules = convert(rulesVM);
        ruleExtractCategoriesRepository.save(rules);
        return rules;
    }

    public RuleExtractCategories createNewRules(RuleExtractCategories rules){
        ruleExtractCategoriesRepository.save(rules);
        return rules;
    }


    @Transactional
    public RuleExtractCategories updateRules(RulesExtractCategoriesVM rulesVM){
        List<RuleExtractCategories> deletedRules = ruleExtractCategoriesRepository.findFullRules(rulesVM.getShop().getId());
        ruleExtractCategoriesRepository.delete(deletedRules);
        return createNewRules(rulesVM);
    }

    @Transactional
    public RuleExtractCategories updateRules(RuleExtractCategories rules){
        List<RuleExtractCategories> deletedRules = ruleExtractCategoriesRepository.findAllRulesBelongShop(rules.getShop().getId());
        ruleExtractCategoriesRepository.delete(deletedRules);
        return createNewRules(rules);
    }



    public RuleExtractCategories convert(RulesExtractCategoriesVM rules) {
        Shop shop = shopRepository.findOne(rules.getShop().getId());

        RuleExtractCategories result = null;
        RuleExtractCategories prevCategory = null;
        for (RulesExtractCategoriesVM.RuleLevelCategory levelCategory : rules.getRuleCategories()) {
            if (result == null) {
                result = new RuleExtractCategories(levelCategory.getSelector(), shop);
                prevCategory = result;
            } else {
                RuleExtractCategories currentCategory = new RuleExtractCategories(levelCategory.getSelector(),shop);
                currentCategory.setParent(prevCategory);
                prevCategory.setChild(currentCategory);
                prevCategory = currentCategory;
            }
        }
        return result;
    }

}
