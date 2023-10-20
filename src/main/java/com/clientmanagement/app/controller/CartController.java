package com.clientmanagement.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clientmanagement.app.dto.CartSaveOrUpdateDto;
import com.clientmanagement.app.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@PostMapping("/add-item")
	public String addOrUpdateCart(@RequestParam Long itemId) {
		log.info("ACTION: add or update items to the cart wiht id: {}", itemId);

		CartSaveOrUpdateDto cartDto = new CartSaveOrUpdateDto();
		cartDto.setItemId(itemId);
		this.cartService.addItemToCart(cartDto);

		return "redirect:/home";
	}

	@PostMapping("/remove-item")
	public String removeItemFromCart(@RequestParam Long itemId) {
		log.info("ACTION: remove item from the cart wiht id: {}", itemId);

		CartSaveOrUpdateDto cartDto = new CartSaveOrUpdateDto();
		cartDto.setItemId(itemId);
		this.cartService.removeItemFromCart(cartDto);

		return "redirect:/home";
	}

}
