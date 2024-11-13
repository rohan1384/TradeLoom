package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

	public VerificationCode findByUserId(Long userId);
	
}
