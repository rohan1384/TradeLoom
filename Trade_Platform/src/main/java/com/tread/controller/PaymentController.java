package com.tread.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tread.domain.PaymentMethod;
import com.tread.model.PaymentOrder;
import com.tread.model.User;
import com.tread.response.PaymentResponse;
import com.tread.service.PaymentService;
import com.tread.service.UserService;

@RestController
public class PaymentController {
	 @Autowired
	private UserService userService;
	  @Autowired
	 private PaymentService paymentService;
	  
	  @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
	  public ResponseEntity<PaymentResponse>paymentHandler(@PathVariable PaymentMethod paymentMethod,@PathVariable Long amount,@RequestHeader("Authorization") String jwt) throws Exception,RazorpayException,StripeException{
		  
		  User user=userService.findUserProfileByJwt(jwt);
		   
		   PaymentResponse paymentResponse;
		   
		   PaymentOrder order=paymentService.createOrder(user, amount, paymentMethod);
		   
		   if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
			   
			   paymentResponse=paymentService.createRazorpayPaymentLink(user, amount);
			   
		   }
		   else {
			   paymentResponse=paymentService.createStripePaymentLink(user, amount, amount);

		   }
		   
		   return new ResponseEntity<>(paymentResponse,HttpStatus.CREATED);
		  
	  }

}
