package com.ducut.barbershop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewsController {

    @GetMapping("/reviews")
        public String reviews(Model model){
            return "reviews-main";
        }
}


