package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Orders;
import com.ducut.barbershop.repos.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/orders")
    public String orders(Model model) {
        Iterable<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders-main";
    }

}