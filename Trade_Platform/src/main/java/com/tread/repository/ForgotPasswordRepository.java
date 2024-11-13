package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String>{

	ForgotPasswordToken findByUserId(Long userId);
	
}
