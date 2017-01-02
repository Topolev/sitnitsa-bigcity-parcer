package com.mycompany.myapp.repository.rulesparsing;

import com.mycompany.myapp.domain.rulesparsing.RulesExtractProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RulesExtractProductRepository extends JpaRepository<RulesExtractProduct, Long> {

    @Query(value = "select rules from RulesExtractProduct rules where rules.shop.id = ?1")
    RulesExtractProduct findRulesForShop(Long shopId);
}
