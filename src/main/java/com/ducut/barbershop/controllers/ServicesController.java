package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Masters;
import com.ducut.barbershop.models.Service;
import com.ducut.barbershop.repos.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ServicesController {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/services")
    public String services(Model model) {

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        return "services-main";
    }

    @GetMapping("/services/edit")
    public String servicesEdit(Model model) {

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        return "services-main";
    }


}
