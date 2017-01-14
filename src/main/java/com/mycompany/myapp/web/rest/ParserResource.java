package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.enums.StatusShop;
import com.mycompany.myapp.domain.rules.RuleExtractCategories;
import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;
import com.mycompany.myapp.service.*;
import com.mycompany.myapp.service.bigcity.ProductLink;
import com.mycompany.myapp.web.rest.entitybigcity.BigCitySession;
import com.mycompany.myapp.web.rest.vmrules.RuleExtractProductLinkVM;
import com.mycompany.myapp.web.rest.vmrules.RuleExtractProductVM;
import com.mycompany.myapp.web.rest.vmrules.RulesExtractCategoriesVM;
import com.mycompany.myapp.web.rest.vmrules.WrapAllRulesVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class ParserResource {

    private static final Logger LOG = LoggerFactory.getLogger(ParserResource.class);

    @Inject
    private ParserService parserService;
    @Inject
    private BigCitySession bigCitySession;

    @Inject
    private RuleExtractCategoriesService ruleExtractCategoriesService;
    @Inject
    private RuleExtractProductLinkService ruleExtractProductLinkService;
    @Inject
    private RuleExtractProductService ruleExtractProductService;

    @Inject
    private ShopRepository shopRepository;
    @Inject
    private CategoryService categoryService;
    @Inject
    private ProductService productService;



    @RequestMapping(value = "/extractCategories", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity extractCategories(@RequestBody RulesExtractCategoriesVM rulesVM) {
        LOG.debug("Extract categories {}", rulesVM);

        Shop shop = new Shop();
        shop.setUrl(rulesVM.getShop().getUrl());

        RuleExtractCategories rules = ruleExtractCategoriesService.convert(rulesVM, shop);



        List<Category> categories = parserService.buildCategories(rules, shop);
        bigCitySession.put("categories", categories);
        return ResponseEntity.ok(categories);
    }

    @RequestMapping(value = "/extractProductLinks", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity extractProductLinks(@RequestBody RuleExtractProductLinkVM rulesVM){
        LOG.debug("Extract product links {}", rulesVM);
        List<Category> categories = bigCitySession.get("categories");
        RuleExtractProductLink rules = ruleExtractProductLinkService.convert(rulesVM, new RuleExtractProductLink());
        List<ProductLink> links = parserService.buildProductLinks(rules, categories, true);
        bigCitySession.put("productlinks", links);
        return ResponseEntity.ok(links);
    }

    @RequestMapping(value = "/extractProducts", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity extractProducts(@RequestBody RuleExtractProductVM rulesVM){
        LOG.debug("Extract products {}", rulesVM);
        List<ProductLink> links = bigCitySession.get("productlinks");
        RuleExtractProduct rules = ruleExtractProductService.convert(rulesVM, new RuleExtractProduct());
        List<Product> products = parserService.buildProduct(rules, links, true);
        return ResponseEntity.ok(products);
    }



    @Transactional
    @RequestMapping(value = "/deleteShopAndRules/{id}", method = GET)
    public ResponseEntity deleteShopAndRules(@PathVariable Long id){


        ruleExtractCategoriesService.deleteRulesBelongShop(id);
        ruleExtractProductLinkService.deleteRuleBelongsShop(id);
        ruleExtractProductService.deleteRuleBelongsShop(id);

        shopRepository.delete(id);

        return new ResponseEntity(OK);
    }


    @Transactional
    @RequestMapping(value = "/updateRules", method = PUT)
    public ResponseEntity updateRules(@RequestBody  WrapAllRulesVM rules){

        Shop shop = new Shop();
        shop.setId(rules.getShop().getId());
        shop.setIdUrl(rules.getShop().getIdUrl());
        shop.setStatus(StatusShop.ACTIVE);
        shop.setUrl(rules.getShop().getUrl());
        shop.setName(rules.getShop().getName());
        shop.setCreatedDate(ZonedDateTime.now());
        shop = shopRepository.save(shop);

        ruleExtractCategoriesService.updateRules(rules.getRulesExtractCategories(), shop);
        ruleExtractProductLinkService.updateRules(rules.getRuleExtractProductLink(), shop);
        ruleExtractProductService.updateRules(rules.getRuleExtractProduct(), shop);

        return new ResponseEntity(OK);
    }

    @Transactional
    @RequestMapping(value = "/updateRules", method = POST)
    public ResponseEntity saveRules(@RequestBody  WrapAllRulesVM rules){
        Shop shop = new Shop();
        shop.setIdUrl(rules.getShop().getIdUrl());
        shop.setStatus(StatusShop.ACTIVE);
        shop.setUrl(rules.getShop().getUrl());
        shop.setName(rules.getShop().getName());
        shop.setCreatedDate(ZonedDateTime.now());
        shop = shopRepository.save(shop);

        ruleExtractCategoriesService.createNewRules(rules.getRulesExtractCategories(), shop);
        ruleExtractProductLinkService.createNewRules(rules.getRuleExtractProductLink(), shop);
        ruleExtractProductService.createNewRules(rules.getRuleExtractProduct(), shop);

        return new ResponseEntity(OK);
    }





    @RequestMapping(value = "/updateData", method = GET)
    public ResponseEntity updateDataShops(){
        parserService.updateData();
        return new ResponseEntity(OK);
    }

    @RequestMapping(value = "/updateData/{id}", method = GET)
    public ResponseEntity getDataShop(@PathVariable Long id){
        parserService.updateDataForShop(id);
        return new ResponseEntity(OK);
    }


}
