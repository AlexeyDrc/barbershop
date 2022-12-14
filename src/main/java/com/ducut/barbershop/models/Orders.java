package com.ducut.barbershop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private int time;

    private Long serviceTypeId;
    private Long customerId, masterId;

    private int status;

    private int withDiscount;

    public Orders() {
    }

    public Orders(Date date, int time, Long serviceTypeId, Long customerId, Long masterId, int status, int withDiscount) {
        this.date = date;
        this.time = time;
        this.serviceTypeId = serviceTypeId;
        this.customerId = customerId;
        this.masterId = masterId;
        this.status = status;
        this.withDiscount = withDiscount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWithDiscount() {
        return withDiscount;
    }

    public void setWithDiscount(int withDiscount) {
        this.withDiscount = withDiscount;
    }
}
