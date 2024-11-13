package com.tread.request;

import com.tread.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {

	
	private String coinId;
	
	private double quantity;
	
	private OrderType orderType;
}
