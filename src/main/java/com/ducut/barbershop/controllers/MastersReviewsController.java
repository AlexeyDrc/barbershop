package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.*;
import com.ducut.barbershop.repos.CustomerRepository;
import com.ducut.barbershop.repos.MastersRepository;
import com.ducut.barbershop.repos.MastersReviewsRepository;
import com.ducut.barbershop.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/reviews/add/{id}")
    public String reviews(@AuthenticationPrincipal UserDetails loggedUser, @PathVariable(value = "id") long id, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (loggedUser != null) {
            String currentPrincipalName = loggedUser.getUsername();
            Optional<UserEntity> users = userRepository.findByUsername(currentPrincipalName);
            ArrayList<UserEntity> resU = new ArrayList<>();
            users.ifPresent(resU::add);
            model.addAttribute("user", resU);
        } else {
            model.addAttribute("user", null);
        }


        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);


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


