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
		
		// Just before returning the VIEW 
		// We are attaching the model("user") with the VIEW
		model.addAttribute("user", new SignupDto());
		
		// Here GET method returns the VIEW which is signup.html in our case
		return "signup";
	}

	@PostMapping("/signup")
	public String doSignupPage(@ModelAttribute("user") SignupDto signupDto) {
		
		// @ModelAttribute binds the VIEW data to the method parameter
		// This service call actually saving the user data to database
		this.customerService.doSignup(signupDto);
		
		// Here POST method returns the VIEW which is login.html in our case
		// After successful signup we are forcing the user to login
		return "login";
	}

}
