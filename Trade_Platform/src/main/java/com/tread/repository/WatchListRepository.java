package com.tread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tread.model.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {

	WatchList findByUserId(Long userId);
	
}
