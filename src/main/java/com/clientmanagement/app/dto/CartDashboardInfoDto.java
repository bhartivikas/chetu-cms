package com.clientmanagement.app.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDashboardInfoDto {

	private Integer totalCount;
	private BigDecimal totalAmount;
	private List<ItemDto> items;

}
