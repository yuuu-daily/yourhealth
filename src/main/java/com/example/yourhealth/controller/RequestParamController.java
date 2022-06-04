package com.example.yourhealth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestParamController {
	
	@GetMapping("show")
	public String showView() {
		return "show";
	}

	@PostMapping(value = "logout-complete", params = "logout")
	public String showLogoutView() {
		return "layouts/complete";
	}
	
	@GetMapping("home")
	public String showhomeView() {
		return "home";
	}
	
	@GetMapping("mypage")
	public String showmypageView() {
		return "mypage";
	}
	
	@GetMapping("contact")
	public String showContactView() {
		return "contact";
	}
	
	@GetMapping("privacyPolicy")
	public String showprivacyPolicyView() {
		return "privacyPolicy";
	}
	
	@GetMapping("concept")
	public String showConceptView() {
		return "concept";
	}
	
    /** contactの確認画面を表示 */
	@PostMapping("contactConfirm")
	public String contactConfirmView(
		Model model, @RequestParam String name, @RequestParam String furigana, 
		@RequestParam String email, @RequestParam String item, @RequestParam String content
	) {
		model.addAttribute("name", name);
		model.addAttribute("furigana", furigana);
		model.addAttribute("email", email);
		model.addAttribute("item", item);
		model.addAttribute("content", content);
		return "contactConfirm";
	}
	
}
