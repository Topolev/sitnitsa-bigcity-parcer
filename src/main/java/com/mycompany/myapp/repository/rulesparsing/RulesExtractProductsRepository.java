package com.mycompany.myapp.repository.rulesparsing;

import com.mycompany.myapp.domain.rulesparsing.RulesExtractProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RulesExtractProductsRepository extends JpaRepository<RulesExtractProducts, Long> {

    @Query(value = "select rules from RulesExtractProducts rules where rules.shop.id = ?1")
    RulesExtractProducts findRulesForShop(Long shopId);
}
