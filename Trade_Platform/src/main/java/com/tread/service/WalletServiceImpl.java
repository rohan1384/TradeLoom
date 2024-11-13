package com.tread.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tread.domain.OrderType;
import com.tread.model.Order;
import com.tread.model.User;
import com.tread.model.Wallet;
import com.tread.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private  WalletRepository walletRepository;
	
	@Override
	public Wallet getUserWallet(User user) {
          Wallet wallet=walletRepository.findByUserId(user.getId());
          
          if(wallet==null) {
        	  wallet=new Wallet();
        	  wallet.setUser(user);
          }
          
		
		return wallet;
	}

	@Override
	public Wallet addBalance(Wallet wallet, Long money) {
		BigDecimal balance =wallet.getBalance();
		BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
		
		wallet.setBalance(newBalance);
		
		
		return walletRepository.save(wallet);
				
	}

	@Override
	public Wallet findWalletById(Long id) throws Exception {

		Optional<Wallet> wallet = walletRepository.findById(id);
		
		if(wallet.isPresent()) {
			return wallet.get();
		}
		
		throw new Exception("wallet not found");
	}

	@Override
	public Wallet walletToWallettransfer(User sender, Wallet receiveWallet, Long amount) throws Exception {
		
		Wallet senderWallet = getUserWallet(sender);
		
		if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0) {
			
			throw new Exception("Insufficient balance...");
			
			
		}
		
		BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
		   senderWallet.setBalance(senderBalance);
		   
		   walletRepository.save(senderWallet);
		   
		BigDecimal receiverBalance =receiveWallet.getBalance().add(BigDecimal.valueOf(amount));
		receiveWallet.setBalance(receiverBalance);
		walletRepository.save(receiveWallet);
		
		return senderWallet;
		
		
		
		
		
		
		
	}

	@Override
	public Wallet payorderPayment(Order order, User user) throws Exception {
         Wallet wallet = getUserWallet(user);
         
         if(order.getOrderType().equals(OrderType.BUY)) {
        	 
        	 BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
        	 
        	 if(newBalance.compareTo(order.getPrice())<0) {
        		 throw new Exception("insufficient funds for this transaction");
        	 }
        	 
        	 wallet.setBalance(newBalance);
        	 
         }
         
         else {
        	 
        	 BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
                 wallet.setBalance(newBalance);
         }
		
		walletRepository.save(wallet);
		
		return wallet;
	}

}
