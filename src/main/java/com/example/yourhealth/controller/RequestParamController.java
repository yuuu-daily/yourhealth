package com.example.yourhealth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RequestParamController {
	
	@GetMapping("/")
	public String showView() {
		return "show";
	}
	
	@GetMapping("about")
	public String aboutView() {
		return "about";
	}

	@PostMapping(value = "logout-complete", params = "logout")
	public String showLogoutView() {
		return "layouts/complete";
	}
	
	@GetMapping("home")
	public String showhomeView() {
		return "home";
	}
	
	@GetMapping("concept")
	public String showConceptView() {
		return "concept";
	}
	
}
