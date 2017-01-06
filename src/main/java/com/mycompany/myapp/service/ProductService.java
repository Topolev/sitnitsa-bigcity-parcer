package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.dataparsing.Product;
import com.mycompany.myapp.repository.dataparsing.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    @Inject
    private ProductRepository productRepository;

    @Transactional
    public List<Product> updateProduct(List<Product> products){
        if ((products == null) || products.isEmpty()){
            return Collections.emptyList();
        }
        Long shopId = products.get(0).getShop().getId();
        List<Product> deletedProducts = productRepository.findProductsByShop(shopId);
        productRepository.delete(deletedProducts);

        return productRepository.save(products);
    }





}
