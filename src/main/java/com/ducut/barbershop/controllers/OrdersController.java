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
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;


@Controller
public class OrdersController {

    public long serviceId;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MastersRepository mastersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TimesRepository timesRepository;

    private java.sql.Date saveDate;
    private Long saveTime;
    private Number saveService;
    private Long idMaster;
    private String saveName;
    private String savePhone;


    @GetMapping("/orders")
    public String orders(Model model) {
        Iterable<Orders> orders = ordersRepository.findByDateASC();
        model.addAttribute("orders", orders);

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

      /*  Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);
*/
        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);

        Iterable<Times> times = timesRepository.findAll();
        model.addAttribute("times", times);

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

        Iterable<Masters> masters = mastersRepository.findAll();
        model.addAttribute("masters", masters);

        Iterable<Times> times = timesRepository.findAll();
        model.addAttribute("times", times);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        model.addAttribute("currentDate", sdf.format(date));

        return "orders-add";
    }

    @GetMapping("/service/{id}")
    @PostMapping("/service/{id}")
    public String serviceId(@PathVariable(value = "id") long id, Model model) {

        serviceId = id;

        return "redirect:/orders/type";
    }

    @GetMapping("/orders/add/details/{id}")
    public String orderAddDetails(@PathVariable(value = "id") long id, Model model) {

        model.addAttribute("selectedServId", serviceId);

        Optional<Masters> master = mastersRepository.findById(id);
        ArrayList<Masters> res = new ArrayList<>();
        master.ifPresent(res::add);

        model.addAttribute("master", res);
        model.addAttribute("masterId", String.valueOf(id));

        ArrayList freeTime = new ArrayList<>();
        freeTime = getMasterFreeTime(id, saveDate);
        model.addAttribute("freeTime", freeTime);

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        model.addAttribute("idMaster", String.valueOf(id));

        int day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        model.addAttribute("currentDay", day);

        if (res.get(0).getWorkingDay() == 1) {
            long workingTime = currentDate.getTime() + 1 * 24 * 60 * 60 * 1000;
            model.addAttribute("minDate", sdf.format(workingTime));
        } else {
            model.addAttribute("minDate", sdf.format(currentDate));
        }

        long addTime = currentDate.getTime() + 14 * 24 * 60 * 60 * 1000;
        Date maxDate = new Date(addTime);

        model.addAttribute("maxDate", sdf.format(maxDate));

        return "orders-add-details";
    }

    @PostMapping("/orders/add/details/{id}")
    public String ordersAdd(@RequestParam java.sql.Date date, @RequestParam Number idService, @PathVariable(value = "id") long id, Model model) {

        saveDate = date;
        saveService = idService;
        idMaster = id;

        return "redirect:/orders/add/time";
    }

    @GetMapping("/orders/test/{id}")
    public String orderTest(@PathVariable(value = "id") long id, Model model) {

        Iterable<Orders> orders = ordersRepository.findByDateASC();
        model.addAttribute("orders", orders);

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        /*Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);*/

        Iterable<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);

        Iterable<Masters> masters = mastersRepository.findAll();
        ;
        model.addAttribute("masters", masters);

        Iterable<Times> times = timesRepository.findAll();
        model.addAttribute("times", times);

        model.addAttribute("idMaster", String.valueOf(id));

        return "orders-test";
    }

    @GetMapping("/orders/add/time")
    public String orderAddTime(@AuthenticationPrincipal UserDetails loggedUser, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = loggedUser.getUsername();
        Optional<UserEntity> users = userRepository.findByUsername(currentPrincipalName);
        ArrayList<UserEntity> resU = new ArrayList<>();
        users.ifPresent(resU::add);
        model.addAttribute("users", resU);

        model.addAttribute("dateTest", String.valueOf(saveDate));

        Optional<Masters> master = mastersRepository.findById(idMaster);
        ArrayList<Masters> res = new ArrayList<>();
        master.ifPresent(res::add);
        model.addAttribute("master", res);
        model.addAttribute("masterId", String.valueOf(idMaster));

        ArrayList freeTime = new ArrayList<>();
        freeTime = getMasterFreeTime(idMaster, saveDate);
        model.addAttribute("freeTime", freeTime);

        if (freeTime.size() == 0)
        {
            model.addAttribute("freeTimeBool", 0);
        }
        else
        {
            model.addAttribute("freeTimeBool", 1);
        }

        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);

        Iterable<Times> times = timesRepository.findAll();
        model.addAttribute("times", times);

        return "orders-add-time";
    }

    @PostMapping("/orders/add/time")
    public String ordersAddRes(@RequestParam String customerPhone, @RequestParam String customerName, @RequestParam Number selectedTime, Model model) {

        savePhone = customerPhone;
        saveName = customerName;

        Number customerId = getCustomerId(savePhone, customerName);

        addRow(customerId, idMaster, saveDate, saveService, selectedTime);

        /*Orders orders = new Orders(saveDate, selectedTime.intValue(),serviceId, customerId.longValue(), idMaster, false);
        ordersRepository.save(orders);*/

        saveTime = selectedTime.longValue();
        //saveDate = null;

        return "redirect:/orders/complete";
    }

    @GetMapping("/orders/complete")
    public String orderComplete(Model model) {
        Optional<Masters> master = mastersRepository.findById(idMaster);
        /*Optional<Masters> master = mastersRepository.findById(2L);*/
        ArrayList<Masters> res = new ArrayList<>();
        master.ifPresent(res::add);
        model.addAttribute("master", res);

        Optional<Times> time = timesRepository.findById(saveTime);
        /*Optional<Times> time = timesRepository.findById(2L);*/
        ArrayList<Times> resT = new ArrayList<>();
        time.ifPresent(resT::add);
        model.addAttribute("time", resT);

        Optional<Service> service = serviceRepository.findById(saveService.longValue());
        /*Optional<Service> service = serviceRepository.findById(2L);*/
        ArrayList<Service> resS = new ArrayList<>();
        service.ifPresent(resS::add);
        model.addAttribute("service", resS);


        model.addAttribute("date", saveDate);

        return "orders-complete";
    }


    @GetMapping("/orders/type")
    public String orderType(Model model) {
        return "orders-type";
    }

    @GetMapping("/orders/date")
    public String orderDateCh(Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        model.addAttribute("minDate", sdf.format(currentDate));

        long addTime = currentDate.getTime() + 14 * 24 * 60 * 60 * 1000;
        Date maxDate = new Date(addTime);

        model.addAttribute("maxDate", sdf.format(maxDate));

        return "orders-date-ch";
    }

    @PostMapping("/orders/date")
    public String orderDatCh(@RequestParam java.sql.Date date, Model model) {
        saveDate = date;
        return "redirect:/orders/date/masters";
    }

    @GetMapping("/orders/date/masters")
    public String orderDateChMasters(Model model) {
        Iterable<Orders> orders = ordersRepository.findByDateASC();
        model.addAttribute("orders", orders);

        Iterable<Times> times = timesRepository.findAll();
        model.addAttribute("times", times);

        Iterable<Masters> masters = mastersRepository.findAll();
        ;
        model.addAttribute("masters", masters);

        ArrayList mastersDate = new ArrayList<>();
        mastersDate = getMastersForDate(saveDate);
        model.addAttribute("mastersDate", mastersDate);

        ArrayList<Long> mastersDateLong = new ArrayList<>();
        mastersDate = getMastersForDate(saveDate);



        return "orders-date-masters";
    }

















    /*Методы для обработки данных заказов*/


    private ArrayList<Long> getMastersForDate(java.sql.Date saveDate) {
        ArrayList<Long> mastersForDate = new ArrayList<>();
        Iterable<Masters> masters = mastersRepository.findAll();
        Iterable<Orders> orders = ordersRepository.findAll();
        Iterable<Times> times = timesRepository.findAll();

        for (Masters master : masters) {
            mastersForDate.add(master.getId());
        }

        int day;
        int savedDay;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(saveDate);
        savedDay = calendar.get(Calendar.DAY_OF_MONTH);


        for (Masters master : masters) {
            if ((master.getWorkingDay() + day) % 2 != savedDay % 2) {
                mastersForDate.remove(master.getId());
            }
        }


        for (int i = 0; i < mastersForDate.size(); i++) {
            for (Masters master : masters) {
                if (master.getId() == mastersForDate.get(i)) {
                    int busyTime = 13;
                    for (Orders order : orders) {
                        if (order.getMasterId() == master.getId()) {
                            if (order.getDate().equals(saveDate)) {
                                for (Times time : times) {
                                    if (order.getTime() == time.getId()) {
                                        busyTime--;
                                    }
                                    else continue;
                                }
                            }
                        }
                    }
                    if (busyTime == 0)
                    {
                        mastersForDate.remove(master.getId());
                    }
                }
            }
        }

        return mastersForDate;
    }


    private long getCustomerId(String savePhone, String customerName) {
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
            Customer c = new Customer(false, null, savePhone, customerName);
            customerRepository.save(c);
            customerId = c.getId();
        }
        return customerId;
    }

    private ArrayList getMasterFreeTime(long id, java.sql.Date saveDate) {

        Iterable<Orders> orders = ordersRepository.findAll();
        Iterable<Times> times = timesRepository.findAll();

        ArrayList freeTime = new ArrayList<>();

        for (Times time : times) {
            freeTime.add(time.getId());
        }

        for (Orders order : orders) {
            if (order.getMasterId() == id) {
                if (order.getDate().equals(saveDate)) {
                    for (Times time : times) {
                        if (order.getTime() == time.getId()) {
                            freeTime.remove(time.getId());
                        }
                    }
                }
            }
        }

        return freeTime;

    }

    private long getSelectedTimeId(String selectedTimeString) {
        Iterable<Times> times = timesRepository.findAll();

        long id = 0;

        for (Times time : times) {
            if (selectedTimeString == toString()) {
                id = time.getId();
            }
        }

        return id;
    }


    private static void addRow(Number customer_id, Number master_id, java.sql.Date date, Number service_type_id, Number time_id) {

        Orders o = new Orders();

        try {
            String url = "jdbc:mysql://127.0.0.1:3308/orders_database";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            /* SELECT MAX(`id`) FROM `orders`;*/

            ResultSet rs;
            int lastId = 0;
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                rs = statement.executeQuery("SELECT MAX(`id`) FROM `orders`");

                if (rs.next()) {
                    lastId = rs.getInt(1);
                    lastId++;
                }
              /*  lastId = statement.executeQuery("SELECT MAX(`id`) FROM `orders`");
                lastId++;*/
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                int rows = statement.executeUpdate("INSERT INTO `orders` (`id` , `customer_id`, `master_id`, `date`, `service_type_id`, `time`) VALUES ('" + lastId + "','" + customer_id + "', '" + master_id + "', '" + date + "', '" + service_type_id + "', '" + time_id + "')");
                System.out.printf("Added %d rows", rows);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}