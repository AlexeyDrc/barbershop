package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.*;
import com.ducut.barbershop.repos.*;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private MastersRepository mastersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TimesRepository timesRepository;
     @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String home(Model model) {

        model.addAttribute("title", "Главная страница");
        return "home";

    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails loggedUser, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = loggedUser.getUsername();
        Optional<UserEntity> users = userRepository.findByUsername(currentPrincipalName);
        ArrayList<UserEntity> res = new ArrayList<>();
        users.ifPresent(res::add);
        model.addAttribute("users", res);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        model.addAttribute("date", sqlDate);

        Iterable<Orders> orders = ordersRepository.findByStatusASC();
        model.addAttribute("orders", orders);

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);

        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);

        Iterable<Times> times = timesRepository.findAll();
        model.addAttribute("times", times);

        return "profile";
    }

    @GetMapping("/profile/{orderId}/{status}/{customer}")
    public String updateOrd(@PathVariable(value = "orderId") long orderId, @PathVariable(value = "customer") long customerId, @PathVariable(value = "status") int statusId, Model model) {

        Optional<Customer> customer = customerRepository.findById(customerId);
        Customer cus = customer.get();
        Optional<Orders> or = ordersRepository.findById(orderId);
        Orders ord = or.get();

        if (statusId == 2) {
            if (ord.getWithDiscount() == 1) {
                cus.setCompletedOrders(10);
            }
            ordersRepository.delete(ord);
            return "redirect:/myorders";
        }

        ord.setStatus(statusId);
        ordersRepository.save(ord);

        int completedOrders;
        completedOrders = cus.getCompletedOrders();
        if (completedOrders == 10) {
            completedOrders = 0;
        }
        completedOrders++;
        cus.setCompletedOrders(completedOrders);
        customerRepository.save(cus);


        return "redirect:/myorders";
    }


    @RequestMapping("/auth/login")
    public String login(Model model)
    {
        return "login";
    }

    @RequestMapping("/auth/register")
    public String register(Model model)
    {
        return "register";
    }


    @GetMapping("/admin/panel")
    public String adminPanel(Model model)
    {
        return "about-main";
    }


/*    @GetMapping(value = "/workss")
    public String index(@AuthenticationPrincipal UserEntity principal, Model model) {
        String username = principal.getUsername();
        model.addAttribute("user", username);
        return "work-main";
    }*/

    @GetMapping("/myorders")
    public String test(@AuthenticationPrincipal UserDetails loggedUser, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = loggedUser.getUsername();
        Optional<UserEntity> users = userRepository.findByUsername(currentPrincipalName);
        ArrayList<UserEntity> res = new ArrayList<>();
        users.ifPresent(res::add);
        model.addAttribute("users", res);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        model.addAttribute("date", sqlDate);

        Iterable<Orders> orders = ordersRepository.findByDateASC();
        model.addAttribute("orders", orders);

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);

        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);

        Iterable<Times> times = timesRepository.findAll();
        model.addAttribute("times", times);

        return "work-main";
    }


    private UserEntity getPrincipal()
    {
        UserEntity userEntity = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity)
        {
            userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return userEntity;
    }
}