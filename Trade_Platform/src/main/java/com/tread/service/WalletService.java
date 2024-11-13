package com.tread.service;



import com.tread.model.Order;
import com.tread.model.User;
import com.tread.model.Wallet;

public interface WalletService {

	Wallet getUserWallet(User user);
	Wallet addBalance(Wallet wallet,Long money);
	Wallet findWalletById(Long id) throws Exception;
	Wallet walletToWallettransfer(User sender,Wallet receiveWallet,Long amount) throws Exception;
	Wallet payorderPayment(Order order,User user) throws Exception;
	
	
}
