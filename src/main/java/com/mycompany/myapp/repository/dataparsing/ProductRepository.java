package com.mycompany.myapp.repository.dataparsing;

import com.mycompany.myapp.domain.dataparsing.Category;
import com.mycompany.myapp.domain.dataparsing.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select distinct product from Product product where product.shop.id = ?1")
    List<Product> findProductsByShop(Long shopId);
}
