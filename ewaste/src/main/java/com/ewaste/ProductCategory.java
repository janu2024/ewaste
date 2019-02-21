package com.ewaste;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_category")
public class ProductCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cid;
	
	
	public Long getCid() {
		return cid;
	}



	public void setCid(Long cid) {
		this.cid = cid;
	}



	public List<ProductSubCategory> getSubCategory() {
		return subCategory;
	}



	public void setSubCategory(List<ProductSubCategory> subCategory) {
		this.subCategory = subCategory;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	@OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private List<ProductSubCategory> subCategory;


	
	@Column(name = "category_name")
	private String categoryName;


	
	
}
