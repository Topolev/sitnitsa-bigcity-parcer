package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.domain.dataparsing.Shop;
import com.mycompany.myapp.repository.dataparsing.CategoryRepository;
import com.mycompany.myapp.repository.dataparsing.ProductRepository;
import com.mycompany.myapp.repository.dataparsing.ShopRepository;
import com.mycompany.myapp.service.bigcity.CategoryFormatBigCity;
import com.mycompany.myapp.web.rest.vmbigcity.CategoryVM;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Inject
    ShopRepository shopRepository;

    @Inject
    private ProductRepository productRepository;

    @Transactional
    public void saveProducts(List<Product> products, Long shopId) {
        Shop shop = shopRepository.findOne(shopId);
        products
            .forEach(prod ->{
                prod.setShop(shop);
                productRepository.save(prod);
            });
    }

    public List<Product> extractProductsBy(Long shopId){
        return productRepository.findProductsByShop(shopId);
    }



}
