package com.tread.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tread.domain.PaymentMethod;
import com.tread.model.PaymentOrder;
import com.tread.model.User;
import com.tread.response.PaymentResponse;

public interface PaymentService {

	
	
	  PaymentOrder createOrder(User user,Long amount,PaymentMethod paymentMethod);
	  
	  PaymentOrder getPaymentOrderById(Long id) throws Exception;
	  
	  Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws RazorpayException;
	  
	  
	  PaymentResponse createRazorpayPaymentLink(User user,Long amount) throws RazorpayException;
	  
	  PaymentResponse createStripePaymentLink(User user,Long amount,Long orderId) throws StripeException;
	  
	  
}
