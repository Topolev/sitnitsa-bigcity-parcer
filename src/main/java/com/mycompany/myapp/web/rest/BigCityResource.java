package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.repository.dataparsing.CategoryRepository;
import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.service.ProductService;
import com.mycompany.myapp.service.bigcity.CategoryFormatBigCity;
import com.mycompany.myapp.service.bigcity.MenuImportFormatBigCity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class BigCityResource {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private CategoryService categoryService;

    @Inject
    private ProductService productService;




    @RequestMapping(value = "/categories/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCategoriesForShop(@PathVariable Long id){
        List<Category> categories = categoryRepository.findCategoriesByShop(id);
        List<CategoryFormatBigCity> categoriesFormatBigCity = categoryService.convertToFormatBigCity(categories);
        return new ResponseEntity(categoriesFormatBigCity, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getProductsForShop(@PathVariable Long id){
        List<Product> products = productService.extractProductsBy(id);
        return new ResponseEntity(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/menuimport/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getMenuImport(@PathVariable Long id){
        MenuImportFormatBigCity menu = new MenuImportFormatBigCity();

        List<Category> categories = categoryRepository.findCategoriesByShop(id);
        List<CategoryFormatBigCity> categoriesFormatBigCity = categoryService.convertToFormatBigCity(categories);

        List<Product> products = productService.extractProductsBy(id);


        menu.setCategories(categoriesFormatBigCity);
        menu.setItems(products);

        return new ResponseEntity(menu, HttpStatus.OK);
    }


}
