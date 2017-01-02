package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.service.ParserShopService;
import com.mycompany.myapp.web.rest.entitybigcity.Item;
import com.mycompany.myapp.web.rest.vm.CheckSelectorVM;
import com.mycompany.myapp.web.rest.vm.SelectorVM;
import com.mycompany.myapp.web.rest.vmbigcity.RulesToExtractCategoryVM;
import com.mycompany.myapp.web.rest.vmbigcity.RulesToExtractProductVM;
import com.mycompany.myapp.web.rest.vmbigcity.RulesToExtractProductLinksVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Vladimir on 17.12.2016.
 */

@RestController
@RequestMapping("/api")
public class ParserShopsResource {


    private final static Logger LOG = LoggerFactory.getLogger(ParserShopsResource.class);
    @Inject
    private ParserShopService parserShopService;


    @RequestMapping(value = "/checkurl", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> checkUrl(@RequestBody String urlString) {
        LOG.debug("Check url {}", urlString);
        return parserShopService.checkUrl(urlString) ? ResponseEntity.status(OK).build() : ResponseEntity.status(BAD_REQUEST).build();
    }

    @RequestMapping(value = "/checkselector", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity checkSelector(@RequestBody CheckSelectorVM checkSelectorVM) {
        LOG.debug("Check selector {}", checkSelectorVM);

        SelectorVM contentSelector = parserShopService.getOneSelector(checkSelectorVM);
        return contentSelector.isExisted() ?
            ResponseEntity.ok(contentSelector) :
            ResponseEntity.status(BAD_REQUEST).build();
    }

    @RequestMapping(value = "/extractCategories", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCategories(@RequestBody RulesToExtractCategoryVM rules) {
        LOG.debug("Extract categories {}", rules);
        return ResponseEntity.ok(parserShopService.extractCategories(rules));
    }


    @RequestMapping(value = "/extractProducts", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity extractProducts(@RequestBody RulesToExtractProductLinksVM rules){
        LOG.debug("Extract product links {}", rules);
        return ResponseEntity.ok(parserShopService.extractProductLinks(rules));
    }


    @RequestMapping(value = "/checkimgselector", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getSrcFromImgTag(@RequestBody CheckSelectorVM checkSelectorVM) {
        LOG.debug("Check selector {}", checkSelectorVM);
        SelectorVM contentSelector = parserShopService.getImgUrl(checkSelectorVM);
        return contentSelector.isExisted() ?
            ResponseEntity.ok(contentSelector) :
            ResponseEntity.status(BAD_REQUEST).build();
    }

    /*
    @RequestMapping(value = "/extractproducts", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity extractProductLinks(@RequestBody ExtractProductsVM extractProductsVM) {
        LOG.debug("Check selector {}", extractProductsVM);

        List<String> links = parserShopService.extractProductLinks(extractProductsVM);
        return links.size() > 0 ?
            ResponseEntity.ok(links) :
            ResponseEntity.status(BAD_REQUEST).build();
    }*/

    @RequestMapping(value = "/extractproduct", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity extractProduct(@RequestBody RulesToExtractProductVM rules){
        LOG.debug("Extract fields of product: {}", rules);
        List<Product> items = parserShopService.extractProducts(rules);
        return ResponseEntity.ok(items);
    }


}
