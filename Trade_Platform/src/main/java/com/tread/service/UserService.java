package com.tread.service;

import com.tread.domain.VerificationType;
import com.tread.model.User;

public interface UserService {
	
	public User findUserProfileByJwt(String jwt) throws Exception;
	
	public User finduserByEmail(String email) throws Exception;
	public User findUserById(Long userId) throws Exception;
	
	public User enableTwoFactorAuthentication( VerificationType verificationType,String sendTo,User user);
	
	User updatePassword(User user,String newPassword);
	
	
	
	

}
