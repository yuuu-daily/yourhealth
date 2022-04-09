package com.example.yourhealth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionsController {

    @GetMapping(path = "/login")
    public String index() {
        return "show";
    }

    @GetMapping(path = "/login-failure")
    public String loginFailure(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-danger");
        model.addAttribute("message", "Emailまたはパスワードに誤りがあります。");

        return "show";
    }

    @GetMapping(path = "/logout-complete")
    public String logoutComplete(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-info");
        model.addAttribute("message", "ログアウトしました。");

        return "show";
    }
}