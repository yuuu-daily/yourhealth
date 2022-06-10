package com.example.yourhealth.entity;

import java.math.BigDecimal;

public interface UserInf {
	
	Long getUserId();

    String getUsername();
    
    BigDecimal getTargetWeightData();

	void setTargetWeightData(BigDecimal targetWeight);

}
