package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.enums.StatusShop;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;
import com.mycompany.myapp.web.rest.vm.ShopVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class ShopResource {

    @Inject
    private ShopRepository shopRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ShopResource.class);


    @RequestMapping(value = "/shops", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShopVM>> getAllShops() {
        List<ShopVM> shops = shopRepository.findAll().stream()
            .map(ShopVM::new)
            .collect(toList());
        return ResponseEntity.ok(shops);
    }

    @RequestMapping(value = "/shops/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopVM> getShop (@PathVariable Long id) {
        LOG.debug("REST request to get shop with id : {}", id);
        Shop shop = shopRepository.findOne(id);
        if (shop == null) return new ResponseEntity<>(BAD_REQUEST);
        return new ResponseEntity<>(new ShopVM(shop), OK);
    }

    @RequestMapping(value = "/shops/changestatus/{id}", method = GET)
    public ResponseEntity changeStatusShop(@PathVariable Long id){
        LOG.debug("Change status for shop with id : {}", id);
        Shop shop = shopRepository.findOne(id);
        if (shop == null) return new ResponseEntity<>(BAD_REQUEST);
        shop.setStatus(StatusShop.invertStatus(shop.getStatus()));
        shopRepository.save(shop);
        return new ResponseEntity<>(OK);
    }

}
