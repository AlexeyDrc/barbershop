package com.ducut.barbershop.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ProductsController {

    @GetMapping("/products")
    public String products(Model model) {
        return "products-main";
    }
}
