package com.example.yourhealth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.yourhealth.form.Form;

@Controller
public class RequestParamController {
	
	@GetMapping("show")
	public String showView() {
		return "show";
	}
	
	@PostMapping(value = "send", params = "signup")
	public String showSignupView() {
		return "submit/signup";
	}
	
	@PostMapping(value = "send", params = "login")
	public String showLoginView() {
		return "submit/login";
	}

	@PostMapping("confirm")
	public String confirmView(Form f) {
		return "confirm";
	}
	
	@PostMapping("home")
	public String showhomeView() {
		return "home";
	}
	
	@GetMapping("mypage")
	public String showmypageView() {
		return "mypage";
	}
	
	@GetMapping("weightdate")
	public String showweightdateView() {
		return "weightdate";
	}
	
	
	
}
