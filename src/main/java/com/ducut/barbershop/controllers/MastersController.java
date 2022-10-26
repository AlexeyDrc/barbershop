package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.repos.MastersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MastersController {

    @Autowired
    private MastersRepository mastersRepository;

    @GetMapping("/masters")
    public String masters(Model model) {
        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);
        return "masters-main";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);
        return "orders-main";
    }
}