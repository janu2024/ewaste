package com.ewaste;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_price")
public class ProductPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;

	@Column(name = "type")
	private String type;

	@Column(name = "model")
	private String model;

	@Column(name = "company")
	private String company;

	@Column(name = "status_of_proudct")
	private String status_of_product;

	@Column(name = "price")
	private Long price;

}
