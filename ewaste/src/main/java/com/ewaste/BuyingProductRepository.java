package com.ewaste;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BuyingProductRepository extends JpaRepository<BuyingProduct, Long> {

	List<BuyingProduct> findByTransporterInfo(UserInfo transporterInfo);
	
	List<BuyingProduct> findByUserInfo(UserInfo userInfo);

	
}
