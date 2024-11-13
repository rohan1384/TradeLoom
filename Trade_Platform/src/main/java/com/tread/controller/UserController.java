package com.tread.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tread.domain.VerificationType;
import com.tread.model.ForgotPasswordToken;
import com.tread.model.User;
import com.tread.model.VerificationCode;
import com.tread.request.ForgotPasswordTokenRequest;
import com.tread.request.ResetPasswordRequest;
import com.tread.response.ApiResponse;
import com.tread.response.AuthResponse;
import com.tread.service.EmailService;
import com.tread.service.ForgotPassword;
import com.tread.service.UserService;
import com.tread.service.VerificationCodeservice;
import com.tread.utils.OtpUtils;

@RestController
public class UserController {

	  @Autowired  
	private UserService userService;
	  
	  @Autowired
	  private EmailService emailService;
	  @Autowired
	  private VerificationCodeservice verificationCodeservice;
	  
	  @Autowired
	  private ForgotPassword forgotPassword;
	  
	  
	  @GetMapping("/api/users/profile")
	  public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt ) throws Exception{
		  
		  User user =userService.findUserProfileByJwt(jwt);
		  
		  return new ResponseEntity<User>(user,HttpStatus.OK);
		  
	  }
	  
	  @PostMapping("/api/users/verification/{verificationType}/send-otp") 
	  public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt , @PathVariable VerificationType verificationType  ) throws Exception{
	 		  
		  
	 		  User user =userService.findUserProfileByJwt(jwt);
	 		  
	 		  VerificationCode verificationCode = verificationCodeservice.getVerificationCodeById(user.getId());
	 		  
	 		  if(verificationCode==null) {

	 		  verificationCode = verificationCodeservice.sendVerificationCode(user, verificationType);
	 		  
	 		  }
	 		  
	 		  if(verificationType.equals(verificationType.EMAIL)) {
	 			  
	 			  emailService.sendVerificationEmail(user.getEmail(), verificationCode.getOtp());
	 		  }
	 		  
	 		  return new ResponseEntity<>("Verification otp sent successfylly",HttpStatus.OK);
	 		  
	 	  }
	  
	  
	 @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}") 
 public ResponseEntity<User> enableTwofactorAuthentication(@PathVariable String otp,@RequestHeader("Authorization") String jwt  ) throws Exception{
		  
		  User user =userService.findUserProfileByJwt(jwt);
		  VerificationCode verificationCode=verificationCodeservice.getVerificationCodeByUser(user.getId());
		  
		  String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();
		  
		  boolean isVerified = verificationCode.getOtp().equals(otp);
		  
		  if(isVerified) {
			  User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
		    verificationCodeservice.deleteVerificationCodeById(verificationCode);
		    return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
		  
		  }
		  
 throw new Exception("Wrong otp");		  
	  }
	 
	 @PostMapping("/auth/users/reset-password/send-otp") 
	  public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest req ) throws Exception{
	 		  
		 
		 User user=userService.finduserByEmail(req.getSendTo());
		  String otp = OtpUtils.generateOTP();
		  
		  UUID uuid=UUID.randomUUID();
		  String id = uuid.toString();
		  
	 		  ForgotPasswordToken token=forgotPassword.findByUser(user.getId());
	 		  
	 		    if(token==null) {
	 		    	
	 		    	token=forgotPassword.createToken(user, id, otp,req.getVerificationType(),req.getSendTo());
	 		    }
	 		  
	 		    if(req.getVerificationType().equals(VerificationType.EMAIL)) {
	 		    	
	 		    	emailService.sendVerificationEmail(user.getEmail(), token.getOtp());
	 		    }
	 		    
	 		    AuthResponse response = new AuthResponse();
	 		    response.setSession(token.getId());
	 		    
	 		    response.setMessage("Password reset otp sent successfully");
	 		    
	 		  return new ResponseEntity<>(response,HttpStatus.OK);
	 		  
	 	  }
	 
	 @PatchMapping("/auth/users/reset-password/verify-otp") 
	 public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id ,@RequestBody ResetPasswordRequest req,@RequestHeader("Authorization") String jwt  ) throws Exception{
			  
			  ForgotPasswordToken forgotPasswordToken =forgotPassword.findById(id);
			  boolean isVerified=forgotPasswordToken.getOtp().equals(req.getOtp());
			 	
			  if(isVerified) {
				  userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
				  ApiResponse res = new ApiResponse();
				  res.setMessage("password update successfully");
				  
				  return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
			  }
			  
			  throw new Exception("wrong otp");
		  }
}
