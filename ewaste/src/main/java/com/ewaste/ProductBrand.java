/**
 * 
 */
package com.ewaste;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author jahnavi
 *
 */
@Entity
@Table(name = "product_brands")
public class ProductBrand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "m_id", nullable = false, referencedColumnName = "psid")
	private ProductSubCategory subCategory;


}
