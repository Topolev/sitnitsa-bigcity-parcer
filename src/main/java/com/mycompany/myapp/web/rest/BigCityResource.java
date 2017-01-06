package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.domain.enums.StatusShop;
import com.mycompany.myapp.repository.dataparsing.ProductRepository;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;

import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.web.bigcity.CategoryFormatBigCityVM;
import com.mycompany.myapp.web.bigcity.MenuFormatBigCityVM;
import com.mycompany.myapp.web.bigcity.ProductFormatBigCityVM;
import com.mycompany.myapp.web.rest.vmbigcity.ShopVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class BigCityResource {

    @Inject
    ShopRepository shopRepository;

    @Inject
    CategoryService categoryService;

    @Inject
    ProductRepository productRepository;

    @RequestMapping(value = "/infoShop/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity extractCategories(@PathVariable Long id) {
        Shop shop = shopRepository.findOne(id);

        if (shop == null || shop.getStatus() == StatusShop.INACTIVE){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<CategoryFormatBigCityVM> categories = categoryService.getCategoriesBelongsShop(id).stream()
            .map(CategoryFormatBigCityVM::new)
            .collect(toList());

        List<ProductFormatBigCityVM> products = productRepository.findProductsByShop(id).stream()
            .map(ProductFormatBigCityVM::new)
            .collect(toList());

        MenuFormatBigCityVM menuFormatBigCity = new MenuFormatBigCityVM(categories, products);

        return ResponseEntity.ok(menuFormatBigCity);
    }

    @RequestMapping(value = "/availableShops", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShopVM>> getAvailableShops(){
        List<ShopVM> availableShops = shopRepository.findAvailableShop().stream()
            .map(ShopVM::new)
            .collect(toList());

        HttpStatus status = availableShops == null || availableShops.isEmpty() ? NOT_FOUND : OK;
        return new ResponseEntity(availableShops, status);
    }


}
