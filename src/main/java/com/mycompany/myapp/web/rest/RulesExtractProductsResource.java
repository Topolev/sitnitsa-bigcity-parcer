package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.rulesparsing.RulesExtractProducts;
import com.mycompany.myapp.repository.rulesparsing.RulesExtractProductsRepository;
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
public class RulesExtractProductsResource {

    private static final Logger LOG = LoggerFactory.getLogger(RulesExtractProductsResource.class);

    @Inject
    private RulesExtractProductsRepository rulesExtractProductsRepository;

    @RequestMapping(value = "/rulesextractproducts/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RulesExtractProducts> getTruck (@PathVariable Long id) {
        LOG.debug("REST request to get RulesExtractProducts for shop with id : {}", id);
        RulesExtractProducts rules = rulesExtractProductsRepository.findRulesForShop(id);
        HttpStatus status = rules == null ? NOT_FOUND : OK;
        return new ResponseEntity<>(rules, status);
    }
}
