package com.clientmanagement.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.clientmanagement.app.dto.ItemDto;
import com.clientmanagement.app.repository.ItemRepository;
import com.clientmanagement.app.service.ItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemRepository itemRepository;

	@Override
	public List<ItemDto> getAllItems() {
		return this.itemRepository.findAll()
				.stream()
				.map(itemEntity -> {
					var itemDto = new ItemDto();
					itemDto.setId(itemEntity.getId());
					itemDto.setName(itemEntity.getName());
					itemDto.setUnitPrice(itemEntity.getUnitPrice());
					return itemDto;
				})
				.collect(Collectors.toList());
	}

}
