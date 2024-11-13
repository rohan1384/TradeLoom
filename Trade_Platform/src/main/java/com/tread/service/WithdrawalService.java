package com.tread.service;

import java.util.List;

import com.tread.model.User;
import com.tread.model.Withdrawal;

public interface WithdrawalService {

	Withdrawal requesWithdrawal(Long amount,User user);
	
	Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception;
	
	List<Withdrawal>getUsersWithdrawalHistory(User user);
	
	List<Withdrawal>getAllWithdrawalRequest();
	
	
	
	
	
	
	
}
