package com.mycompany.myapp.repository.dataparsing;

import com.mycompany.myapp.domain.dataparsing.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select distinct category from Category category where category.shop.id = ?1")
    List<Category> findCategoriesByShop(Long shopId);
}
