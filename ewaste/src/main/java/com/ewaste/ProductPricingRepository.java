package com.ewaste;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPricingRepository extends JpaRepository<ProductPricing, Long> {

	List<ProductPricing> findByProductModel(ProductModel model);
	
}
