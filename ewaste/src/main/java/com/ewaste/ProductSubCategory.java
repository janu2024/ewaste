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
@Table(name = "product_sub_category")
public class ProductSubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long psid;

	@Column(name = "category_name")
	private String categoryName;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cat_id", nullable = false, referencedColumnName = "cid")
	private ProductCategory productCategory;

	public Long getPsid() {
		return psid;
	}

	public void setPsid(Long psid) {
		this.psid = psid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

}
