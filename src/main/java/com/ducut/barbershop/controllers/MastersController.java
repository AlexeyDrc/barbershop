package com.ducut.barbershop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MastersController {

    @GetMapping("/masters")
    public String masters(Model model) {
        return "masters-main";
    }
}