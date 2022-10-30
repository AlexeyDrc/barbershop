package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Auth.User;
import com.ducut.barbershop.models.MastersReviews;
import com.ducut.barbershop.models.Orders;
import com.ducut.barbershop.models.Service;
import com.ducut.barbershop.repos.OrdersRepository;
import com.ducut.barbershop.repos.ServiceRepository;
import com.ducut.barbershop.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/orders")
    public String orders(Model model) {

        Iterable<Orders> orders = ordersRepository.findByDateASC();
        model.addAttribute("orders", orders);

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        model.addAttribute("currentDate", sdf.format(date));

        return "orders-main";
    }

}