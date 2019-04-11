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
@Table(name = "buying_product_pricing")
public class BuyingProductPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "m_id", nullable = false, referencedColumnName = "mid")
	private ProductModel productModel;

	@Column(name = "product_price")
	private Long productPrice;

	@Column(name = "product_image")
	private String productImage;
	
	
	@Column(name = "product_image2")
	private String productImage2;
	
	
	@Column(name = "product_image3")
	private String productImage3;
	
	@Column(name = "product_image4")
	private String productImage4;
	
	
	
	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public ProductModel getProductModel() {
		return productModel;
	}

	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

	public Long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductImage2() {
		return productImage2;
	}

	public void setProductImage2(String productImage2) {
		this.productImage2 = productImage2;
	}

	public String getProductImage3() {
		return productImage3;
	}

	public void setProductImage3(String productImage3) {
		this.productImage3 = productImage3;
	}

	public String getProductImage4() {
		return productImage4;
	}

	public void setProductImage4(String productImage4) {
		this.productImage4 = productImage4;
	}
	
	

}
