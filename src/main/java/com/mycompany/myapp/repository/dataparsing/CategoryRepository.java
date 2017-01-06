package com.mycompany.myapp.repository.dataparsing;

import com.mycompany.myapp.domain.dataparsing.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface  CategoryRepository extends JpaRepository<Category, Long> {


    @Query(value = "select distinct category from Category category " +
        "left join fetch category.children " +
        "left join fetch category.parent")
    List<Category> getFullCategory();

    @Query(value = "select distinct category from Category category " +
        "left join fetch category.children " +
        "left join fetch category.parent " +
        "left join fetch category.shop " +
        "where category.shop.id = ?1")
    List<Category> findAllBelongsShop(Long idShop);
}
