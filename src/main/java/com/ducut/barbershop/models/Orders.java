package com.ducut.barbershop.models;

import org.hibernate.criterion.Order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String time;

    private Long serviceTypeId;
    private Long customerId, masterId;

    public Orders() {
    }

    public Orders(Long id, String date, String time, Long serviceTypeId, Long customerId, Long masterId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.serviceTypeId = serviceTypeId;
        this.customerId = customerId;
        this.masterId = masterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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


   public static void addRow(Number customer_id, Number master_id, Date date, Number service_type_id) {

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

                if(rs.next())
                {
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
                int rows = statement.executeUpdate("INSERT INTO `orders` (`id` , `customer_id`, `master_id`, `date`, `service_type_id`) VALUES ('"+ lastId +"','"+customer_id+"', '"+master_id+"', '"+date+"', '"+service_type_id+"')");
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
