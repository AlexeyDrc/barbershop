package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.models.MastersReviews;
import com.ducut.barbershop.models.Orders;
import com.ducut.barbershop.repos.MastersRepository;
import com.ducut.barbershop.repos.MastersReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class MastersReviewsController {

    @Autowired
    private MastersRepository mastersRepository;
    @Autowired
    private MastersReviewsRepository mastersReviewsRepository;


    @GetMapping("/reviews/add/{id}")
    public String reviews(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Masters> master = mastersRepository.findById(id);
        ArrayList<Masters> res = new ArrayList<>();
        master.ifPresent(res::add);
        model.addAttribute("master", res);
        model.addAttribute("masterId", String.valueOf(id));
        return "masters-reviews-add";
    }

    @PostMapping("/reviews/add/{id}")
    public String ordersAdd(@PathVariable(value = "id") long id,@RequestParam String name, @RequestParam String description, @RequestParam Number rate, Model model){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        MastersReviews.addReview(id,description,rate,name, java.sql.Date.valueOf(sdf.format(date)));

        return "redirect:/masters";
    }
}


