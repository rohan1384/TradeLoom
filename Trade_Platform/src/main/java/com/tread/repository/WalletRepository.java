package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	
	Wallet findByUserId(Long userId);
	
	
}
