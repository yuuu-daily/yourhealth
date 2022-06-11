package com.example.yourhealth.form;

import java.math.BigDecimal;

//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class EditMypageForm {
	
	@NotNull
	private Long userId;
	
	@NotNull
	@Size(min=1, max=150)
	private BigDecimal targetWeight;
}