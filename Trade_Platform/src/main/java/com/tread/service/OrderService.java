package com.tread.service;

import java.util.List;

import com.tread.domain.OrderType;
import com.tread.model.Coin;
import com.tread.model.Order;
import com.tread.model.OrderItem;
import com.tread.model.User;

public interface OrderService {

	
	Order createOrder(User user,OrderItem orderItem,OrderType orderType);
	
	Order getOrderById(Long orderId) throws Exception;
	
	List<Order>getAllOrdersOfUser(Long userId,OrderType orderType,String assetSymbol );
	
	Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;
	
}
