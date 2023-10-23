package com.clientmanagement.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@GetMapping("/login")
	public String getLoginPage() {
		// Here GET method just return the VIEW which is login.html in our case
		// But where does the POST call going?
		// Here POST call is handled by the Spring Security Framework
		return "login";
	}

}
