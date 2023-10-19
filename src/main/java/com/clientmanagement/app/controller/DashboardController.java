package com.clientmanagement.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.clientmanagement.app.service.CustomerService;
import com.clientmanagement.app.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {

	private final ItemService itemService;
	private final CustomerService customerService;

	@GetMapping(path = { "/", "/index", "/home" })
	public String getHomePage(Model model) {
		model.addAttribute("customers", this.customerService.getAllCustomer());
		model.addAttribute("items", this.itemService.getAllItems());
		return "home";
	}

}
