package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

	 TwoFactorOTP findByUserId(Long userid);
}
