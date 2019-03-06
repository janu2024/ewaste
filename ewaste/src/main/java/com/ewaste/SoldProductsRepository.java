package com.ewaste;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldProductsRepository extends JpaRepository<SoldProducts, Long> {

}
