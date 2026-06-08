package com.example.novo_spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "https://lookcustom.vercel.app")
public class ControllerMVC {
    @GetMapping("/")
    public String home() {
        return "redirect:/login.html";
    }
}

