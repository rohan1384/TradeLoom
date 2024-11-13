package com.tread.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.tread.config.JwtProvider;
import com.tread.domain.VerificationType;
import com.tread.model.TwoFactorAuth;
import com.tread.model.User;
import com.tread.repository.UserRepository;

@RestController
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserProfileByJwt(String jwt) throws Exception {

		String email = JwtProvider.getEmailFromToken(jwt);
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new Exception("user Not found");
		}
		
		return user;
	}

	@Override
	public User finduserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new Exception("user Not found");
		}
		
		return user;
		
		
	}

	@Override
	public User findUserById(Long userId) throws Exception {

		Optional<User>user=userRepository.findById(userId);
		
		if(user.isEmpty()) {
			throw new Exception("user not found");
		}
		
		return user.get();
	}

	

	@Override
	public User updatePassword(User user, String newPassword) {
        
        user.setPassword(newPassword);
        return userRepository.save(user);

		
	}

	@Override
	public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
		TwoFactorAuth twoFactorAuth=new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);	
        
        user.setTwoFactorAuth(twoFactorAuth);
        
        
        return userRepository.save(user);
	}

}
