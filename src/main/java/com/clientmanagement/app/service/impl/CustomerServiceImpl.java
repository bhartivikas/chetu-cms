package com.clientmanagement.app.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clientmanagement.app.dto.SignupDto;
import com.clientmanagement.app.entity.Customer;
import com.clientmanagement.app.repository.CustomerRepository;
import com.clientmanagement.app.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void doSignup(SignupDto signupDto) {
		Customer customerEntity = new Customer();
		customerEntity.setFirstName(signupDto.getFirstName());
		customerEntity.setLastName(signupDto.getLastName());
		customerEntity.setEmail(signupDto.getEmail());
		customerEntity.setMobile(signupDto.getMobile());
		customerEntity.setPassword(this.passwordEncoder.encode(signupDto.getPassword()));
		this.customerRepository.save(customerEntity);
	}

}
