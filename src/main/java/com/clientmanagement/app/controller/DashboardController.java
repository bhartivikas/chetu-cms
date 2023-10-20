package com.clientmanagement.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.clientmanagement.app.service.CartService;
import com.clientmanagement.app.service.CustomerService;
import com.clientmanagement.app.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DashboardController {

	private final ItemService itemService;
	private final CustomerService customerService;
	private final CartService cartService;

	@GetMapping(path = { "/", "/index", "/home" })
	public String getHomePage(Model model) {
		log.info("ACTION: get the dashboard page");

		model.addAttribute("customers", this.customerService.getAllCustomer());
		model.addAttribute("items", this.itemService.getAllItems());
		model.addAttribute("cartsItems", this.cartService.getCart());
		return "home";
	}

}
