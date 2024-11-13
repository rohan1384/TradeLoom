package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.Coin;

public interface CoinRepository  extends JpaRepository<Coin, String>{

}
