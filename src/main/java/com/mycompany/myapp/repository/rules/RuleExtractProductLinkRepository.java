package com.mycompany.myapp.repository.rules;

import com.mycompany.myapp.domain.rules.RuleExtractCategories;
import com.mycompany.myapp.domain.rules.RuleExtractProductLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RuleExtractProductLinkRepository extends JpaRepository<RuleExtractProductLink, Long> {

    @Query(value = "select distinct rules from RuleExtractProductLink rules " +
        "left join rules.shop where rules.shop.id = ?1")
    List<RuleExtractProductLink> findFullRules(Long idShop);

    @Query(value = "select distinct rules from RuleExtractProductLink rules " +
        "left join rules.shop where rules.shop.id = ?1")
    RuleExtractProductLink findRuleBelongShop(Long id);
}
