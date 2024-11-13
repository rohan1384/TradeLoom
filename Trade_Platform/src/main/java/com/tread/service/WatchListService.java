package com.tread.service;

import com.tread.model.Coin;
import com.tread.model.User;
import com.tread.model.WatchList;

public interface WatchListService {

	WatchList findUserWatchList(Long userId) throws Exception;
	
	WatchList createWatchList(User user);
	
	WatchList findById(Long id) throws Exception;
	
	
	Coin addItemToWatchList(Coin coin,User user) throws Exception;
	
	
	
}
