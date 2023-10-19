package com.clientmanagement.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInfoDto {
	private Long customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String buildingNo;
	private String wardNo;
	private String city;
	private String district;
	private String state;
	private String zipcode;
}
