package com.tread.service;

import com.tread.model.PaymentDetails;
import com.tread.model.User;

public interface PaymentDetailsService {

	public PaymentDetails addPaymentDetails(String accountNumber,String accountHolderName,String ifsc,String bankName,User user);
	
	
	public PaymentDetails getUsersPaymentDetails(User user);
	
}
