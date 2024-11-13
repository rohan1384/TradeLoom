package com.tread.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tread.config.JwtProvider;
import com.tread.model.TwoFactorOTP;
import com.tread.model.User;
import com.tread.repository.UserRepository;
import com.tread.response.AuthResponse;
import com.tread.service.CustomerUserDetailsService;
import com.tread.service.EmailService;
import com.tread.service.TwoFactorOtpService;
import com.tread.service.WatchListService;
import com.tread.utils.OtpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

	      @Autowired
		private UserRepository userRepo;
	      
	      @Autowired
	      private CustomerUserDetailsService customerUserDetailsService;
		 @Autowired
	      private TwoFactorOtpService twoFactorOtpService;
		 @Autowired
		 private WatchListService watchListService;
		 
		 @Autowired
		 private EmailService emailService;
		 
		 
		 @PostMapping("/signup")
		 public ResponseEntity<AuthResponse>register(@RequestBody User user) throws Exception{
			 
			 
	         
	         
	         User isEmailExist=userRepo.findByEmail(user.getEmail());
	         
	         if(isEmailExist!=null) {
	        	 throw new Exception("email is already used with another account");
	         }
	         
            User newUser = new User();
			 
			 newUser.setEmail(user.getEmail());
			 newUser.setPassword(user.getPassword());
	         newUser.setFullName(user.getFullName());		 
			
	         User savedUser = userRepo.save(newUser);
	         
	         watchListService.createWatchList(savedUser);
	         Authentication auth =new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
			    
			    SecurityContextHolder.getContext().setAuthentication(auth);
			    
			    String jwt = JwtProvider.generateToken(auth);
			    AuthResponse res = new AuthResponse();
			    res.setJwt(jwt);
			    res.setStatus(true);
			    res.setMessage("register success");
			    
			 
			 return new ResponseEntity<>(res,HttpStatus.CREATED);
		 }
		 
		 
		 @PostMapping("/signin")
		 public ResponseEntity<AuthResponse>login(@RequestBody User user) throws Exception{
			 
		 String userName = user.getEmail();
		 String password = user.getPassword();
		 
		 
	         
	         
          
			
	         Authentication auth = authenticate(userName,password);
			    
			    SecurityContextHolder.getContext().setAuthentication(auth);
			    
			    String jwt = JwtProvider.generateToken(auth);
			    
			    User authuser = userRepo.findByEmail(userName);
			    if(user.getTwoFactorAuth().isEnabled()) {
				    AuthResponse res = new AuthResponse();
				    res.setMessage("Two factor auth is enabled" );
				    res.setTwoFactorAuthenabled(true);
				    
				    String otp = OtpUtils.generateOTP();
				    
				    TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authuser.getId());
				    
				    if(oldTwoFactorOTP!=null) {
				    	
				    	twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
				    }
				    
				    TwoFactorOTP newTwoFactorOTP=twoFactorOtpService.createTwofactorOtp(authuser, otp, jwt);
				    
				    emailService.sendVerificationEmail(userName, otp);
				    
				    
				    res.setSession(newTwoFactorOTP.getId());
				    
				    return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

			    }
			    
			    AuthResponse res = new AuthResponse();
			    res.setJwt(jwt);
			    res.setStatus(true);
			    res.setMessage("login success");
			    
			 
			 return new ResponseEntity<>(res,HttpStatus.CREATED);
		 }


		private Authentication authenticate(String userName, String password) {
			UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
			
			if(userDetails==null) {
				
				throw new BadCredentialsException("invalid username");
			}
				
			if(!password.equals(userDetails.getPassword())) {
					
					throw new BadCredentialsException("invalid password");
				}
			
			
			
			
			return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities()) ;
		}
		
		@PostMapping("/two-factor/otp/{otp}")
		public ResponseEntity<AuthResponse>verifySiginOtp(@PathVariable String otp , @RequestParam String id ) throws Exception{
			
			TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
			
			if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
				AuthResponse res = new AuthResponse();
				res.setMessage("Two factor authentication verified");
				res.setTwoFactorAuthenabled(true);
				res.setJwt(twoFactorOTP.getJwt());
				
				return new ResponseEntity<>(res,HttpStatus.OK);
				
				
			}
			
			throw new Exception("invalid otp");
			
		}








}
