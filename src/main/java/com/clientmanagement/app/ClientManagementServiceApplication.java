package com.clientmanagement.app;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.clientmanagement.app.entity.Item;
import com.clientmanagement.app.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ClientManagementServiceApplication {

	private final ItemRepository itemItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(ClientManagementServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startApp() {
		var i1 = new Item();
		i1.setName("Mobile");
		i1.setUnitPrice(new BigDecimal("100.25"));
		var i2 = new Item();
		i2.setName("Laptop");
		i2.setUnitPrice(new BigDecimal("200.25"));
		var i3 = new Item();
		i3.setName("Tab");
		i3.setUnitPrice(new BigDecimal("150.25"));

		this.itemItemRepository.saveAll(List.of(i1, i2, i3));
	}

}
