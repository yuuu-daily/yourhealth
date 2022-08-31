package com.example.yourhealth.form;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class TrainingMenuForm {
	
	@NotNull
	private Long userId;
	
	@NotNull
	private String category;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	public String getCategory() {
		return category;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
}