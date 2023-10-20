package com.clientmanagement.app.service;

import com.clientmanagement.app.dto.CartDashboardInfoDto;
import com.clientmanagement.app.dto.CartSaveOrUpdateDto;

public interface CartService {

	void addItemToCart(CartSaveOrUpdateDto cartDto);

	CartDashboardInfoDto getCart();

	void removeItemFromCart(CartSaveOrUpdateDto cartDto);

}
