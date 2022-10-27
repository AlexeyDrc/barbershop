package com.ducut.barbershop.models;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Masters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String FIO, photoURL;
    private double rate;

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

}
