package com.tread.request;

import com.tread.domain.VerificationType;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {

	private String sendTo;
  private VerificationType verificationType;


}
