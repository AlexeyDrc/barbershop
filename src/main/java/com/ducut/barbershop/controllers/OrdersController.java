package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Auth.User;
import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.models.Orders;
import com.ducut.barbershop.models.Service;
import com.ducut.barbershop.repos.MastersRepository;
import com.ducut.barbershop.repos.OrdersRepository;
import com.ducut.barbershop.repos.ServiceRepository;
import com.ducut.barbershop.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MastersRepository mastersRepository;


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


    @GetMapping("/orders/add")
    public String ordersAdd(Model model) {


        Iterable<Orders> orders = ordersRepository.findByDateASC();
        model.addAttribute("orders", orders);

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);

        Iterable<Masters> masters = mastersRepository.findAll();;
        model.addAttribute("masters", masters);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        model.addAttribute("currentDate", sdf.format(date));

        return "orders-add";
    }

    /*@PostMapping("/orders/add")
    public String blogPostAdd(@RequestParam Number id, @RequestParam Number idUser, @RequestParam String Date, @RequestParam Number idMaster, @RequestParam Number idService, Model model){
        Orders order = new Orders(id.longValue() ,Date, idService.longValue(), idUser.longValue(), idMaster.longValue());
        ordersRepository.save(order);
        return "redirect:/orders";
    }*/

}