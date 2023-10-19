package com.clientmanagement.app.service;

import java.util.List;

import com.clientmanagement.app.dto.CustomerInfoDto;
import com.clientmanagement.app.dto.SignupDto;

public interface CustomerService {

	void doSignup(SignupDto signupDto);

	List<CustomerInfoDto> getAllCustomer();

}
