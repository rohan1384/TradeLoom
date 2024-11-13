package com.tread.service;

import com.tread.model.TwoFactorOTP;
import com.tread.model.User;

public interface TwoFactorOtpService {
	
	TwoFactorOTP createTwofactorOtp(User user,String otp,String jwt);
	
	 TwoFactorOTP findByUser(Long userId);
	 
	 TwoFactorOTP findById(String id);
	 
	 boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp,String otp);
	 
      void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}
