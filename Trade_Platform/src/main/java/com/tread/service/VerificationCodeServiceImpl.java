package com.tread.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tread.domain.VerificationType;
import com.tread.model.User;
import com.tread.model.VerificationCode;
import com.tread.repository.VerificationCodeRepository;
import com.tread.utils.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeservice {

	   @Autowired
	  private VerificationCodeRepository verificationCodeRepository;
	
	
	@Override
	public VerificationCode sendVerificationCode(User user,VerificationType verificationType) {
VerificationCode verificationCode1=new VerificationCode();

 verificationCode1.setOtp(OtpUtils.generateOTP());
  verificationCode1.setVerificationType(verificationType);
  verificationCode1.setUser(user);
		
		return verificationCodeRepository.save(verificationCode1);
	}

	@Override
	public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode>verificationCode = verificationCodeRepository.findById(id);
        
        if(verificationCode.isPresent()) {
        	return verificationCode.get();
        }
		
		throw new Exception("verification code not found");
	}

	

	@Override
	public void deleteVerificationCodeById(VerificationCode verificationCode) {
		
		verificationCodeRepository.delete(verificationCode);
		
		
	}

	@Override
	public VerificationCode getVerificationCodeByUser(Long userId) {
		
		return verificationCodeRepository.findByUserId(userId);
	}

	
}
