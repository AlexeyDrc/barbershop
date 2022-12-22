package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.Customer;
import com.ducut.barbershop.models.UserEntity;
import com.ducut.barbershop.repos.CustomerRepository;
import com.ducut.barbershop.repos.UserRepository;
import com.ducut.barbershop.security.RegisterDto;
import com.ducut.barbershop.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model)
    {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegisterDto user,
                           BindingResult result, @RequestParam String phoneNumber, @RequestParam String name, Model model) {
        UserEntity existingUserUsername = userService.findUserByUsername(user.getUsername());
        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if (result.hasErrors())
        {
            model.addAttribute("user", user);
            return "register";
        }

        Optional<Customer> c = customerRepository.findById(getCustomerId(phoneNumber, name));
        Customer customer = c.get();

        if (customer.getUserId() == null)
        {
            userService.saveUser(user);
            UserEntity user1 = userService.findUserByUsername(user.getUsername());
            customer.setUserId(user1.getId());
            customerRepository.save(customer);
            return "redirect:/auth/login";
        }
        else {
            return "redirect:/register?failphone";
        }
    }

    @GetMapping("/log")
    public String test(Model model)
    {
        return "log";
    }

    @GetMapping("/register/done")
    public String getRegisterDone(Model model)
    {
        return "register-extra";
    }

  /*  @PostMapping("/register/save")
    public String registerExtra(@AuthenticationPrincipal UserDetails loggedUser, @RequestParam String phoneNumber, @RequestParam String name, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = loggedUser.getUsername();
        Optional<UserEntity> u = userRepository.findByUsername(currentPrincipalName);
        UserEntity user = u.get();

        OrdersController ordersController = new OrdersController();
        Optional<Customer> c = customerRepository.findById(ordersController.getCustomerId(phoneNumber, name));
        Customer customer = c.get();
        customer.setUserId(user.getId());
        customerRepository.save(customer);

        return "redirect:/profile";
    }*/


    @PostMapping("/register/done")
    public String registerExtra(@AuthenticationPrincipal UserDetails loggedUser, @RequestParam String phoneNumber, @RequestParam String name, Model model)
    {
        String currentPrincipalName = loggedUser.getUsername();
        Optional<UserEntity> u = userRepository.findByUsername(currentPrincipalName);
        UserEntity user = u.get();

        Optional<Customer> c = customerRepository.findById(getCustomerId(phoneNumber, name));
        Customer customer = c.get();

        if (customer.getUserId() != user.getId()) {
            if (customer.getUserId() != null) {
                return "redirect:/register/done?fail";
            } else {
                customer.setUserId(user.getId());
                customerRepository.save(customer);
                return "redirect:/profile";
            }
        }
        else
            {
                customer.setPhoneNumber(phoneNumber);
                customer.setCustomerName(name);
                customerRepository.save(customer);
                return "redirect:/profile";
            }
    }

    public long getCustomerId(String savePhone, String customerName) {
        long customerId = 0;
        boolean customerHaveARow = false;


        Iterable<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            if (Objects.equals(customer.getPhoneNumber(), savePhone)) {
                customerId = customer.getId();
                customerHaveARow = true;
            }
        }

        if (customerHaveARow == false) {
            Customer c = new Customer(false, null, savePhone, customerName, 0);
            customerRepository.save(c);
            customerId = c.getId();
        }
        return customerId;
    }
  /*  private void addCustomer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<UserEntity> u = userRepository.findByUsername(currentPrincipalName);
        UserEntity user = u.get();

        OrdersController ordersController = new OrdersController();
        Optional<Customer> c = customerRepository.findById(ordersController.getCustomerId(savePhoneNumber, saveName));
        Customer customer = c.get();
        customer.setUserId(user.getId());
        customerRepository.save(customer);
    }
*/
}
