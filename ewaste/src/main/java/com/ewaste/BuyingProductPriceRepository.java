package com.ewaste;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BuyingProductPriceRepository extends JpaRepository<BuyingProductPrice, Long> {

	
}
