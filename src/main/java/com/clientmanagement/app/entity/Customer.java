package com.clientmanagement.app.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cms_customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String firstName;
	private String lastName;

	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String mobile;

	private String password;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Address address;

	public void addAddress(Address address) {
		address.setCustomer(this);
		this.setAddress(address);
	}

}
