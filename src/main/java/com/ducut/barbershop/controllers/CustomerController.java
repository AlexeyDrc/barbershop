package com.ducut.barbershop.controllers;


import com.ducut.barbershop.repos.CustomerRepository;
import com.ducut.barbershop.repos.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

}
