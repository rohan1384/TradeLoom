package com.tread.service;

import com.tread.domain.VerificationType;
import com.tread.model.User;
import com.tread.model.VerificationCode;

public interface VerificationCodeservice {

	VerificationCode sendVerificationCode(User user,VerificationType verificationType);
	
	VerificationCode getVerificationCodeById(Long id) throws Exception;
	
	VerificationCode getVerificationCodeByUser(Long userId);
	
	void deleteVerificationCodeById(VerificationCode verificationCode);
}
