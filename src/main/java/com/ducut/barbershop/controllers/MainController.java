package com.ducut.barbershop.controllers;

import com.ducut.barbershop.models.*;
import com.ducut.barbershop.repos.*;
import com.ducut.barbershop.security.RegisterDto;
import com.ducut.barbershop.security.UserService;
import com.ducut.barbershop.security.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {


    @Autowired
    private MastersReviewsRepository mastersReviewsRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private MastersRepository mastersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TimesRepository timesRepository;
    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    int sortOperation = 0;


    @RequestMapping("/")
    public String home(Model model) {

        model.addAttribute("title", "Главная страница");
        return "home";

    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails loggedUser, Model model) {
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

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin-panel";
    }


    @GetMapping("/admin/master/delete")
    public String mastersDelete(Model model) {
        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);

        Iterable<MastersReviews> mastersReviews = mastersReviewsRepository.findAll();
        model.addAttribute("masRev", mastersReviews);

        return "master-delete";
    }

    @GetMapping("/admin/master/delete/{master}")
    public String mastersDeleteDetails(@PathVariable(value = "master") long id, Model model) {
        boolean freeMaster = true;
        Optional<Masters> master = mastersRepository.findById(id);
        ArrayList<Masters> res = new ArrayList<>();
        master.ifPresent(res::add);
        model.addAttribute("master", res);

        Iterable<Orders> o = ordersRepository.findAll();
        for (Orders orders : o) {
            if (orders.getStatus() == 0) {
                if (orders.getMasterId() == id) {
                    freeMaster = false;
                }
            }
        }

        model.addAttribute("freeMaster", freeMaster);

        return "master-delete-details";
    }

    @GetMapping("/admin/master/delete/details/{master}")
    public String mastersDeleteFinal(@PathVariable(value = "master") long id, Model model) {
        Iterable<Orders> orders = ordersRepository.findAll();
        Iterable<MastersReviews> reviews = mastersReviewsRepository.findAll();
        Iterable<UserEntity> users = userRepository.findAll();

        Optional<Masters> m = mastersRepository.findById(id);
        Masters master = m.get();

        for (Orders order : orders) {
            if (order.getMasterId() == master.getId()) {
                ordersRepository.delete(order);
            }
        }

        for (MastersReviews review : reviews) {
            if (review.getMasterId() == master.getId()) {
                mastersReviewsRepository.delete(review);
            }
        }

        for (UserEntity user : users) {
            if (user.getId() == master.getUserId()) {
                userRepository.delete(user);
            }
        }

        mastersRepository.delete(master);

        return "redirect:/admin/master/delete";
    }

    @GetMapping("/admin/review/delete")
    public String reviewsDelete(Model model) {
        Iterable<MastersReviews> mastersReviews = null;
        switch (sortOperation) {
            case 0: {
                mastersReviews = mastersReviewsRepository.findAll();
                break;
            }
            case 1: {
                mastersReviews = mastersReviewsRepository.findByDateDESC();
                break;
            }
            case 2: {
                mastersReviews = mastersReviewsRepository.findByRateASC();
                break;
            }
            case 3: {
                mastersReviews = mastersReviewsRepository.findByMasterASC();
                break;
            }
        }
        model.addAttribute("reviews", mastersReviews);

        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);

        return "reviews-delete";
    }

    @GetMapping("/admin/reviews/{operation}")
    public String reviewsSort(@PathVariable(value = "operation") int operation, Model model) {
        sortOperation = operation;
        return "redirect:/admin/review/delete";
    }

    @GetMapping("/admin/{reviewId}/{masterId}")
    public String reviewsDeleting(@PathVariable(value = "reviewId") long reviewId, @PathVariable(value = "masterId") long masterId, Model model) {

        Optional<MastersReviews> r = mastersReviewsRepository.findById(reviewId);
        MastersReviews review = r.get();

        Optional<Masters> m = mastersRepository.findById(masterId);
        Masters master = m.get();

        double newRate = ((master.getRate() * master.getNumberofratings()) - review.getRate()) / (master.getNumberofratings() - 1);
        master.setRate(newRate);
        master.setNumberofratings(master.getNumberofratings() - 1);

        mastersReviewsRepository.delete(review);

        return "redirect:/admin/review/delete";
    }

    @GetMapping("/admin/master/add")
    public String masterAdd(Model model) {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "master-add";
    }

    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        Iterable<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);
        Iterable<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "admin-users";
    }

    @GetMapping("/admin/users/{userId}")
    public String deleteUser(@PathVariable(value = "userId") long id, Model model) {

        /*Optional<UserEntity> u = userRepository.findById(id);
        UserEntity user = u.get();*/
        Iterable<UserEntity> users = userRepository.findAll();
        Iterable<Customer> customers = customerRepository.findAll();
        Iterable<Orders> orders = ordersRepository.findAll();

        for (UserEntity user : users) {
            if (user.getId() == id) {
                for (Customer customer : customers) {
                    if (customer.getUserId() == user.getId()) {
                        for (Orders order : orders) {
                            if (order.getCustomerId() == customer.getId()) {
                                ordersRepository.delete(order);
                            }
                        }
                        customerRepository.delete(customer);
                    }
                }
                userRepository.delete(user);
            }
        }


        //userRepository.delete(user);

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/master/add")
    public String masterAdd(@Valid @ModelAttribute("user") RegisterDto user,
                            BindingResult result, @RequestParam String name, @RequestParam String photoURL, @RequestParam Number workingDays, Model model) {
        UserEntity existingUserUsername = userService.findUserByUsername(user.getUsername());
        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/admin/master/add?fail";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "master-add";
        }
        userService.saveMaster(user);
        UserEntity user1 = userRepository.findUserByUsername(user.getUsername());
        Masters m = new Masters(name, 0, photoURL, 0, workingDays.intValue(), user1.getId());
        mastersRepository.save(m);
        return "redirect:/masters";

      /*  if (customer.getUserId() == null)
        {
            userService.saveUser(user);
            UserEntity user1 = userService.findUserByUsername(user.getUsername());
            customer.setUserId(user1.getId());
            customerRepository.save(customer);
            return "redirect:/auth/login";
        }
        else {
            return "redirect:/register?failphone";
        }*/
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
    public String login(Model model) {
        return "login";
    }

 /*   @RequestMapping("/auth/register")
    public String register(Model model)
    {
        return "register";
    }*/


  /*  @GetMapping("/register")
    public String getRegisterForm(Model model)
    {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegisterDto user,
                           BindingResult result, Model model) {
        UserEntity existingUserUsername = userRepository.findUserByUsername(user.getUsername());
        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if (result.hasErrors())
        {
            model.addAttribute("user", user);
            return "register";
        }

        return "redirect:/success";
    }*/

    @GetMapping("/admin/panel")
    public String adminPanel(Model model) {
        return "about-main";
    }


    /*@GetMapping(value = "/workss")
    public String index(@AuthenticationPrincipal UserEntity principal, Model model) {
        String username = principal.getUsername();
        model.addAttribute("user", username);
        return "work-main";
    }*/

    @GetMapping("/myorders")
    public String test(@AuthenticationPrincipal UserDetails loggedUser, Model model) {
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


    private UserEntity getPrincipal() {
        UserEntity userEntity = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity) {
            userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return userEntity;
    }
}