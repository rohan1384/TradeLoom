package com.tread.service;

import com.tread.domain.VerificationType;
import com.tread.model.ForgotPasswordToken;
import com.tread.model.User;

public interface ForgotPassword {

	ForgotPasswordToken createToken(User user,String id,String otp, VerificationType verificationType,String sendTo);
	
	ForgotPasswordToken findById(String id);
	
	ForgotPasswordToken findByUser(Long userId);
	
	void deleteToken(ForgotPasswordToken token);
	 
}
