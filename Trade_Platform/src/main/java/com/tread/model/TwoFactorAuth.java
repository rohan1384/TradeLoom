package com.tread.model;



import com.tread.domain.VerificationType;

import lombok.Data;
@Data
public class TwoFactorAuth {
 private boolean isEnabled=false;
	 
	 private VerificationType sendTo;
}
