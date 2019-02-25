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

@Entity
@Table(name = "product_pricing")
public class ProductPricing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "m_id", nullable = false, referencedColumnName = "mid")
	private ProductModel subCategory;

	@Column(name = "working")
	private boolean working;

	@Column(name = "product_price")
	private Long productPrice;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public ProductModel getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(ProductModel subCategory) {
		this.subCategory = subCategory;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	public Long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}


	

}
