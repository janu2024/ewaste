/**
 * 
 */
package com.ewaste;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jahnavi
 *
 */
@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {

}
