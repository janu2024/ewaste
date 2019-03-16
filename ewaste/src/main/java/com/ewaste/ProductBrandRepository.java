/**
 * 
 */
package com.ewaste;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jahnavi
 *
 */
@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {

	List<ProductBrand> findBySubCategory(ProductSubCategory subCategory);
}
