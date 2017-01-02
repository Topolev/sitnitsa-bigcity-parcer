package com.mycompany.myapp.repository.rulesparsing;

import com.mycompany.myapp.domain.rulesparsing.RulesExtractCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RulesExtractCategoriesRepository extends JpaRepository<RulesExtractCategories, Long> {

    @Query(value = "select rules from RulesExtractCategories rules where rules.shop.id = ?1")
    RulesExtractCategories findRulesForShop(Long shopId);
}
