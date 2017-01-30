package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.rules.RuleExtractCategoriesRepository;
import com.mycompany.myapp.service.RuleExtractCategoriesService;
import com.mycompany.myapp.web.rest.vmrules.RulesExtractCategoriesVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class RuleExtractCategoriesResource {

    private static final Logger LOG = LoggerFactory.getLogger(RuleExtractCategoriesResource.class);

    @Inject
    private RuleExtractCategoriesRepository ruleExtractCategoriesRepository;

    @Inject
    private RuleExtractCategoriesService ruleExtractCategoriesService;

    @RequestMapping(value = "/ruleExtractCategories/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RulesExtractCategoriesVM> getRules (@PathVariable Long id) {
        LOG.debug("REST request to get RulesExtractCategories for shop with id : {}", id);

        RulesExtractCategoriesVM rules = ruleExtractCategoriesRepository.findAllRulesBelongShop(id).stream()
            .findFirst()
            .map(RulesExtractCategoriesVM::new)
            .orElse(null);

        HttpStatus status = rules == null ? NOT_FOUND : OK;
        return new ResponseEntity<>(rules, status);
    }

    @RequestMapping(value = "/ruleExtractCategories", method = POST)
    public ResponseEntity<RulesExtractCategoriesVM> saveRules (@PathVariable RulesExtractCategoriesVM rulesVM) {

        RulesExtractCategoriesVM result = Optional.ofNullable(ruleExtractCategoriesService.createNewRules(rulesVM))
            .map(RulesExtractCategoriesVM::new)
            .orElse(null);
        HttpStatus status = result == null ? NOT_FOUND : OK;
        return new ResponseEntity<>(result, status);
    }

}
