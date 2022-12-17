package com.ducut.barbershop.models;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@Entity
public class Masters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String FIO, photoURL;
    private double rate;

    private int workingDay;
    private int numberofratings;

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

/*public static int findOrders(Number masterId) {

        Orders o = new Orders();

        try {
            String url = "jdbc:mysql://127.0.0.1:3308/orders_database";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            *//* SELECT MAX(`id`) FROM `orders`;*//*

            ResultSet rs;
            int count = 0;
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                rs = statement.executeQuery("SELECT COUNT(*) FROM masters_reviews WHERE master_id == "+ masterId +" ");
                if(rs.next())
                {
                    count = rs.getInt(1);
                }
                return count;
              *//*  lastId = statement.executeQuery("SELECT MAX(`id`) FROM `orders`");
                lastId++;*//*
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
    }*/
}
