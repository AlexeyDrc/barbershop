package com.ducut.barbershop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean authUser = false;
    private Long userId;
    private String phoneNumber;
    private String customerName;

    private int completedOrders;
    public Customer() {
    }

    public Customer(boolean authUser, Long userId, String phoneNumber, String customerName, int completedOrders) {
        this.authUser = authUser;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.completedOrders = completedOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAuthUser() {
        return authUser;
    }

    public void setAuthUser(boolean authUser) {
        this.authUser = authUser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(int completedOrders) {
        this.completedOrders = completedOrders;
    }
}

