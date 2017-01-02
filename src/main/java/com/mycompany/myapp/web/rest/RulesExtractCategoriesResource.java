package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.rulesparsing.RulesExtractCategories;
import com.mycompany.myapp.repository.rulesparsing.RulesExtractCategoriesRepository;
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
public class RulesExtractCategoriesResource {

    private static final Logger LOG = LoggerFactory.getLogger(RulesExtractCategoriesResource.class);

    @Inject
    private RulesExtractCategoriesRepository rulesExtractCategoriesRepository;

    @RequestMapping(value = "/rulesextractcategories/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RulesExtractCategories> getTruck (@PathVariable Long id) {
        LOG.debug("REST request to get RulesExtractCategories for shop with id : {}", id);
        RulesExtractCategories rules = rulesExtractCategoriesRepository.findRulesForShop(id);
        HttpStatus status = rules == null ? NOT_FOUND : OK;
        return new ResponseEntity<>(rules, status);
    }
}
