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
@Table(name = "seller_cart_product")
public class CartProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cpid;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cart_id", nullable = false, referencedColumnName = "cid")
	private UserCart usercart;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false, referencedColumnName = "pid")
	private ProductPrice productprice;

	@Column(name = "price")
	private Long price;

	@Column(name = "qty")
	private Long qty;

	public Long getCpid() {
		return cpid;
	}

	public void setCpid(Long cpid) {
		this.cpid = cpid;
	}

	public UserCart getUsercart() {
		return usercart;
	}

	public void setUsercart(UserCart usercart) {
		this.usercart = usercart;
	}

	public ProductPrice getProductprice() {
		return productprice;
	}

	public void setProductprice(ProductPrice productprice) {
		this.productprice = productprice;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}
	
	
	
	

}
