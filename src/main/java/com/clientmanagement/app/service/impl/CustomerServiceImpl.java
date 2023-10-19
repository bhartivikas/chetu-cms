package com.clientmanagement.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clientmanagement.app.dto.CustomerInfoDto;
import com.clientmanagement.app.dto.SignupDto;
import com.clientmanagement.app.entity.Address;
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

		Address address = new Address();
		address.setBuldingNo(signupDto.getAddress().getBuldingNo());
		address.setWardNo(signupDto.getAddress().getWardNo());
		address.setCity(signupDto.getAddress().getCity());
		address.setDistrict(signupDto.getAddress().getDistrict());
		address.setState(signupDto.getAddress().getState());
		address.setZipcode(signupDto.getAddress().getZipcode());

		customerEntity.addAddress(address);

		this.customerRepository.save(customerEntity);
	}

	@Override
	public List<CustomerInfoDto> getAllCustomer() {
		return this.customerRepository.findAll()
				.stream()
				.map(c -> {
					CustomerInfoDto dto = new CustomerInfoDto();
					dto.setFirstName(c.getFirstName());
					dto.setLastName(c.getLastName());
					dto.setEmail(c.getEmail());
					dto.setMobile(c.getMobile());
					dto.setBuildingNo(c.getAddress().getBuldingNo());
					dto.setWardNo(c.getAddress().getWardNo());
					dto.setCity(c.getAddress().getCity());
					dto.setDistrict(c.getAddress().getDistrict());
					dto.setState(c.getAddress().getState());
					dto.setZipcode(c.getAddress().getZipcode());
					return dto;
				}).collect(Collectors.toList());
	}

}
