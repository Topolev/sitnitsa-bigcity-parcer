package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.rules.RuleExtractProductLinkRepository;
import com.mycompany.myapp.web.rest.vmrules.RuleExtractProductLinkVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class RuleExtractProductLinkResource {

    private static final Logger LOG = LoggerFactory.getLogger(RuleExtractProductLinkResource.class);

    @Inject
    private RuleExtractProductLinkRepository ruleExtractProductLinkRepository;

    @RequestMapping(value = "/ruleExtractProductLink/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RuleExtractProductLinkVM> getRules (@PathVariable Long id) {
        LOG.debug("REST request to get RuleExtractProductLink for shop with id : {}", id);

        RuleExtractProductLinkVM rules = ruleExtractProductLinkRepository.findFullRules(id).stream()
            .findFirst()
            .map(RuleExtractProductLinkVM::new)
            .orElse(null);


        HttpStatus status = rules == null ? NOT_FOUND : OK;
        return new ResponseEntity<>(rules, status);
    }
}
