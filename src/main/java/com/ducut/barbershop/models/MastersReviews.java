package com.ducut.barbershop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.DecimalFormat;

@Entity
public class MastersReviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long masterId;
    private String description, name;

    private Date date;
    private double rate;


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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public static void addReview(Number master_id, String description, Number rate, String name, Date date) {
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
                rs = statement.executeQuery("SELECT MAX(`id`) FROM `masters_reviews`");

                if(rs.next())
                {
                    lastId = rs.getInt(1);
                    lastId++;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                int rows = statement.executeUpdate("INSERT INTO `masters_reviews` (`id` , `description`, `master_id`, `rate`, `name`, `date`) VALUES ('"+ lastId +"','"+description+"', '"+master_id+"', '"+rate+"', '"+name+"', '"+date+"')");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            int numberOfRatings = 0;
            int newNumberOfRatings = 0;
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                rs = statement.executeQuery("SELECT `numberofratings` FROM `masters` WHERE id = "+master_id+" ");

                if(rs.next())
                {
                    numberOfRatings = rs.getInt(1);
                    newNumberOfRatings = numberOfRatings + 1;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                int rows = statement.executeUpdate("UPDATE `masters` SET `numberofratings` = '"+newNumberOfRatings+"' WHERE `masters`.`id` = "+master_id+" ");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            double currentRate = 0, newRate = 0;
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                rs = statement.executeQuery("SELECT `rate` FROM `masters` WHERE id = "+master_id+" ");

                if(rs.next())
                {
                    currentRate = rs.getDouble(1);
                    newRate = ((currentRate*numberOfRatings) + rate.doubleValue())/newNumberOfRatings;
                    newRate = (double)Math.round(newRate * 100d) / 100d;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                int rows = statement.executeUpdate("UPDATE `masters` SET `rate` = '"+newRate+"' WHERE `masters`.`id` = "+master_id+" ");
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
