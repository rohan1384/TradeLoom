package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long>{

	PaymentDetails findByUserId(Long userId);
	
}
