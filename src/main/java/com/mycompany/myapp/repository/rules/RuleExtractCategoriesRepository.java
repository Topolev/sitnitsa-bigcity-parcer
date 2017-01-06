package com.mycompany.myapp.repository.rules;

import com.mycompany.myapp.domain.rules.RuleExtractCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RuleExtractCategoriesRepository extends JpaRepository<RuleExtractCategories, Long> {

    @Query(value = "select distinct rules from RuleExtractCategories rules " +
        "left join fetch rules.parent " +
        "left join fetch rules.child " +
        "left join rules.shop " +
        "where rules.shop.id = ?1 order by rules.id")
    List<RuleExtractCategories> findFullRules(Long idShop);

    @Query(value = "select distinct rules from RuleExtractCategories rules " +
        "left join fetch rules.parent " +
        "left join fetch rules.child " +
        "left join rules.shop " +
        "where rules.shop.id = ?1 order by rules.id")
    List<RuleExtractCategories> findAllRulesBelongShop(Long idShop);
}
