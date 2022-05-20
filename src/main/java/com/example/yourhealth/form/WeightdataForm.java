package com.example.yourhealth.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WeightdataForm {

    private Long id;

    private Long userId;

    private BigDecimal weightData;

    private UserForm user;
    
    public BigDecimal getWeightData() {
    	 return weightData;
    }
    
    public void setWeightData(BigDecimal weightData) {
    	 this.weightData = weightData;
    }

}

