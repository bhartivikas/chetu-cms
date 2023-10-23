package com.clientmanagement.app.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import com.clientmanagement.app.dto.CartDashboardInfoDto;
import com.clientmanagement.app.dto.CartSaveOrUpdateDto;
import com.clientmanagement.app.dto.ItemDto;
import com.clientmanagement.app.entity.Cart;
import com.clientmanagement.app.entity.Item;
import com.clientmanagement.app.repository.CartRepository;
import com.clientmanagement.app.repository.ItemRepository;
import com.clientmanagement.app.service.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;

	@Override
	@Transactional
	public void addItemToCart(CartSaveOrUpdateDto cartDto) {

		// We need the logged in username
		// because every user have its own cart
		// A user can have only one cart
		final String username = getLoggeInUsername();

		// We are trying to get the Cart details of the User
		final Optional<Cart> optionalCart = this.cartRepository.findByUsername(username);

		// If user cart is available then just add the item to the cart
		if (optionalCart.isPresent()) {
			log.info("ACTION: adding a new cart");
			updateCart(optionalCart.get(), cartDto);
		}
		// Let's say this is the first time the user trying to add items to the cart
		// So, no cart is available for the user in the start
		// then we must have to create a cart to the user
		else {
			log.info("ACTION: updating  an existing cart");
			createNewCart(username, cartDto);
		}

	}

	@Override
	public CartDashboardInfoDto getCart() {

		// Cart is directly related to the user
		// No cart can exist without user
		// So, fetch the username of the logged in user which is email in our case
		final String username = getLoggeInUsername();

		// There is a chance that, this is the first time user logged in after signup or
		// He never added Item to the cart
		final Optional<Cart> optionalCart = this.cartRepository.findByUsername(username);

		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();

			// Every cart had set of items in it
			final Set<Item> items = cart.getItems();

			// So, the size of the set is out total item count. It may be Zero
			final int totalItem = items.size();

			// Now we have to calculate the total amount
			BigDecimal totalAmount = BigDecimal.ZERO;
			for (var i : items) {
				totalAmount = totalAmount.add(i.getUnitPrice());
			}

			CartDashboardInfoDto info = new CartDashboardInfoDto();
			info.setTotalCount(totalItem);// IMP: set the total item count
			info.setTotalAmount(totalAmount);// IMP: set the total amount

			// Now, we need to return the whole Item set
			// But the problem is Item is not equal to ItemDto, both are different object
			info.setItems(items.stream()
					// MAP from Item ==> ItemDto
					.map(item -> {
						ItemDto dto = new ItemDto();

						dto.setId(item.getId());
						dto.setName(item.getName());
						dto.setUnitPrice(item.getUnitPrice());

						return dto;
					})
					// COLLECT to a list
					.collect(Collectors.toList()));

			return info;
		} else {
			// If first log in or never added Item to the cart
			// Create a dummy response or while signup create an empty cart for the user
			CartDashboardInfoDto info = new CartDashboardInfoDto();
			info.setTotalCount(0);// No Items in the cart
			info.setTotalAmount(BigDecimal.ZERO);// If no Items then the amount is also 0
			return info;
		}

	}

	@Override
	@Transactional // VVI
	public void removeItemFromCart(CartSaveOrUpdateDto cartDto) {

		// We need the logged in username
		// because every user have its own cart
		// A user can have only one cart
		final String username = getLoggeInUsername();

		// Get Cart belongs to the user otherwise throw error
		final Cart cart = this.cartRepository.findByUsername(username)
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Cart not found"));

		// Why I had used iterator?
		Iterator<Item> itemItr = cart.getItems().iterator();

		while (itemItr.hasNext()) {

			var item = itemItr.next();

			if (item.getId() == cartDto.getItemId()) {
				// This Item object is in the Persistence Context
				// i.e, this Item is in Transaction boundary
				// so no need to call the save() method again for the cart object
				// changes will be flushed during dirty checking
				itemItr.remove();
			}
		}

		// Do not use for each loop, ConcurrentModificationException

	}

	private void createNewCart(String username, CartSaveOrUpdateDto cartDto) {

		// Find the Item object from its ID
		final Item item = fetchItemById(cartDto);

		Cart cart = new Cart();
		cart.addItem(item);// Must add the Item entity which is retrieved from the database
		cart.setUsername(username);// Must add the username(email) in our case

		// This method will save the Cart in the database
		this.cartRepository.save(cart);
	}

	private void updateCart(Cart cart, CartSaveOrUpdateDto cartDto) {
		// Find the Item object from its ID
		final Item item = fetchItemById(cartDto);

		// Just add the Item into the cart
		cart.addItem(item);

		// no need as update cart is in a Transaction Boundary
		// this.cartRepository.save(cart);
	}

	private Item fetchItemById(CartSaveOrUpdateDto cartDto) {
		return this.itemRepository
				.findById(cartDto.getItemId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item not found"));
	}

	private String getLoggeInUsername() {

		// SecurityContextHolder => This stores the logged in user details
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// If auth object is null, Logged in user not present
		// It means we will not get any Cart object so, better throw exception over here
		if (Objects.isNull(auth)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized Access");
		}

		// return the username(email) in our case
		return auth.getName();
	}

}
