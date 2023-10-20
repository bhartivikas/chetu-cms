package com.clientmanagement.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cms_address")
public class Address {

	@Id
	private Long id;

	private String buldingNo;
	private String wardNo;
	private String city;
	private String district;
	private String state;
	private String zipcode;

	@MapsId
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	

}
