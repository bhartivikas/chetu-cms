package com.clientmanagement.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.clientmanagement.app.dto.SignupDto;
import com.clientmanagement.app.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping("/signup")
	public String getSignupPage(Model model) {
		model.addAttribute("user", new SignupDto());
		return "signup";
	}

	@PostMapping("/signup")
	public String doSignupPage(@ModelAttribute("user") SignupDto signupDto) {
		this.customerService.doSignup(signupDto);
		return "login";
	}

}
