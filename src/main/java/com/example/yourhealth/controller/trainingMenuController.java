package com.example.yourhealth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class trainingMenuController {

	@GetMapping("/trainingMenu")
	public String showTrainingMenu(Model model) {
		model.addAttribute("moji", "トレーニングメニュー一覧");
		return "trainingMenu";
	}
	
	@GetMapping("/create")
	public String showCreateTrainigMenu(Model model) {
		//model.addAttribute("moji", "hello world!");
		return "create";
	}
}