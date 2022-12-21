package com.ducut.barbershop.models;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Masters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FIO, photoURL;
    private double rate;

    private int workingDay;
    private long userId;

    private int numberofratings;
    public Masters() {
    }

    public Masters(String FIO, double rate, String photoURL, int numberofratings,  int workingDay, long userId) {
        this.FIO = FIO;
        this.photoURL = photoURL;
        this.rate = rate;
        this.workingDay = workingDay;
        this.userId = userId;
        this.numberofratings = numberofratings;
    }


    public int getNumberofratings() {
        return numberofratings;
    }

    public void setNumberofratings(int numberofratings) {
        this.numberofratings = numberofratings;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(int workingDay) {
        this.workingDay = workingDay;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
