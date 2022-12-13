/*
package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.User;
import com.ducut.barbershop.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {

        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model)
    {
        if(userService.createUser(user)) {
            model.addAttribute("errorMessage" , "Пользователь с таким номером телефона уже существует!");
            return "registration";
        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/hello")
    public String securityUrl()
    {
        return "hello";
    }
}
*/
