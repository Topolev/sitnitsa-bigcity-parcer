package com.mycompany.myapp.repository.dataparsing;

import com.mycompany.myapp.domain.dataparsing.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query(value = "select shop from Shop shop where shop.status = 'ACTIVE'")
    List<Shop> findAvailableShop();

}
