package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.models.Service;
import com.ducut.barbershop.repos.ServiceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServicesController {

    @GetMapping("/services")
    public String services(Model model) {
        return "services-main";
    }

}
