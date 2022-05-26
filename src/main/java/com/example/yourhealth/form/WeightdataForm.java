package com.example.yourhealth.form;

import java.math.BigDecimal;

import lombok.Data;

/* フォームクラス POSTメソッドで送信された<form>タグの中身が入っている */
@Data
public class WeightDataForm {

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

	public void setUser(UserForm userForm) {
		this.user = userForm;
		
	}

}

