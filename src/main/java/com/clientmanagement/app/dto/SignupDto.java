package com.clientmanagement.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupDto {

	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String password;
	private AddressDto address;

}
