package com.mycompany.myapp.web.rest;



import com.mycompany.myapp.repository.rules.RuleExtractProductRepository;
import com.mycompany.myapp.web.rest.vmrules.RuleExtractProductVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class RuleExtractProductResource {

    private static final Logger LOG = LoggerFactory.getLogger(RuleExtractProductResource.class);

    @Inject
    private RuleExtractProductRepository ruleExtractProductRepository;

    @RequestMapping(value = "/ruleExtractProduct/{id}", method = GET)
    public ResponseEntity getRulesForShop (@PathVariable Long id) {
        LOG.debug("REST request to get RuleExtractProduct for shop with id : {}", id);
        RuleExtractProductVM rule = Optional.ofNullable(ruleExtractProductRepository.findRuleBelongShop(id))
            .map(RuleExtractProductVM::new)
            .orElse(null);
        HttpStatus status = rule==null ? NOT_FOUND : OK;
        return new ResponseEntity(rule, status);
    }
}
