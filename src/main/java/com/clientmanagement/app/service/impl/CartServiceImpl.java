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

		final String username = getLoggeInUsername();

		final Optional<Cart> optionalCart = this.cartRepository.findByUsername(username);

		if (optionalCart.isPresent()) {
			log.info("ACTION: adding a new cart");
			updateCart(optionalCart.get(), cartDto);
		} else {
			log.info("ACTION: updating  an existing cart");
			createNewCart(username, cartDto);
		}

	}

	@Override
	@Transactional
	public CartDashboardInfoDto getCart() {
		final String username = getLoggeInUsername();
		final Optional<Cart> optionalCart = this.cartRepository.findByUsername(username);

		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();

			final Set<Item> items = cart.getItems();
			final int totalItem = items.size();

			BigDecimal totalAmount = BigDecimal.ZERO;
			for (var i : items) {
				totalAmount = totalAmount.add(i.getUnitPrice());
			}

			CartDashboardInfoDto info = new CartDashboardInfoDto();
			info.setTotalCount(totalItem);
			info.setTotalAmount(totalAmount);
			info.setItems(items.stream()
					.map(item -> {
						ItemDto dto = new ItemDto();

						dto.setId(item.getId());
						dto.setName(item.getName());
						dto.setUnitPrice(item.getUnitPrice());

						return dto;
					})
					.collect(Collectors.toList()));

			return info;
		} else {
			CartDashboardInfoDto info = new CartDashboardInfoDto();
			info.setTotalCount(0);
			info.setTotalAmount(BigDecimal.ZERO);
			return info;
		}

	}

	@Override
	@Transactional // VVI
	public void removeItemFromCart(CartSaveOrUpdateDto cartDto) {
		final String username = getLoggeInUsername();
		final Cart cart = this.cartRepository.findByUsername(username)
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Cart not found"));

		// Why I had used iterator
		Iterator<Item> itemItr = cart.getItems().iterator();

		while (itemItr.hasNext()) {
			var item = itemItr.next();
			if (item.getId() == cartDto.getItemId()) {
				itemItr.remove();
			}
		}

	}

	private void createNewCart(String username, CartSaveOrUpdateDto cartDto) {
		final Item item = fetchItemById(cartDto);

		Cart cart = new Cart();
		cart.addItem(item);
		cart.setUsername(username);

		this.cartRepository.save(cart);
	}

	private void updateCart(Cart cart, CartSaveOrUpdateDto cartDto) {
		final Item item = fetchItemById(cartDto);
		cart.addItem(item);
	}

	private Item fetchItemById(CartSaveOrUpdateDto cartDto) {
		return this.itemRepository
				.findById(cartDto.getItemId())
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Item not found"));
	}

	private String getLoggeInUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (Objects.isNull(auth)) {
			throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "Unauthorized Access");
		}
		return auth.getName();
	}

}
