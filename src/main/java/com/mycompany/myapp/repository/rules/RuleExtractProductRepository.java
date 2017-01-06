package com.mycompany.myapp.repository.rules;

import com.mycompany.myapp.domain.rules.RuleExtractProduct;
import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RuleExtractProductRepository extends JpaRepository<RuleExtractProduct, Long> {

    @Query(value = "select distinct rules from RuleExtractProduct rules " +
        "left join rules.shop where rules.shop.id = ?1")
    RuleExtractProduct findRuleBelongShop(Long idShop);
}
