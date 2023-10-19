package com.clientmanagement.app.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
	private Long id;
	private String name;
	private BigDecimal unitPrice;
}
