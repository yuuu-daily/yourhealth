package com.example.yourhealth.entity;

import java.math.BigDecimal;
import java.util.List;



public interface UserInf {
	
	Long getUserId();

    String getUsername();
    
    BigDecimal getTargetWeightData();

	void setTargetWeightData(BigDecimal targetWeight);
	
	String getPurpose();
	
	void setPurpose(String purpose);
	
	List<TrainingMenu> getTrainingMenuList(Long id);
	
	void setTrainingMenuList(List<TrainingMenu> trainingMenuList);

}
