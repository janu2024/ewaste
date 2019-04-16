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
@Table(name = "buying_products")
public class BuyingProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long spid;

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pricing_id", nullable = false, referencedColumnName = "pid")
	private BuyingProductPrice pricing;
	
	
	@Column(name = "product_price")
	private Long productPrice;


	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "uid")
	private UserInfo userInfo;
	

	@ManyToOne(fetch = FetchType.LAZY, optional =true)
	@JoinColumn(name = "transporter_id", nullable = true, referencedColumnName = "uid")
	private UserInfo transporterInfo;


	@Column(name = "product_status")
	private String productStatus;

	public Long getSpid() {
		return spid;
	}


	public void setSpid(Long spid) {
		this.spid = spid;
	}


	public BuyingProductPrice getPricing() {
		return pricing;
	}


	public void setPricing(BuyingProductPrice pricing) {
		this.pricing = pricing;
	}


	public Long getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}


	public UserInfo getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	public UserInfo getTransporterInfo() {
		return transporterInfo;
	}


	public void setTransporterInfo(UserInfo transporterInfo) {
		this.transporterInfo = transporterInfo;
	}


	public String getProductStatus() {
		return productStatus;
	}


	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	
	



}
