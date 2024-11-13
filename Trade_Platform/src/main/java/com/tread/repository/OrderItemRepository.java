package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	
	
}
