package com.tread.response;

import lombok.Data;

@Data
public class AuthResponse {

	
	private String jwt;
	
	private boolean status;
	
	private String message;
	
	private boolean isTwoFactorAuthenabled;
	
	private String session;
}
