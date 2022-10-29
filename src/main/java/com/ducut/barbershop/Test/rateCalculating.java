/*
package com.ducut.barbershop.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class rateCalculating {

    private static final String url = "jdbc:mysql://localhost:3308/orders_database";
    private static final String user = "root";
    private static final String password = "";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static int rate, masterRateId;

    public static int getRate(int masterId) {
        String quary = "select * from masters_reviews";

        try {

            con = DriverManager.getConnection(url, user, password);

            stmt = con.createStatement();

            rs = stmt.executeQuery(quary);

            while (rs.next()) {
                rate = rs.getInt(1);
                masterRateId = rs.getInt(3);
                if ()
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { */
/*can't do anything *//*
 }
            try {
                stmt.close();
            } catch (SQLException se) { */
/*can't do anything *//*
 }
            try {
                rs.close();
            } catch (SQLException se) { */
/*can't do anything *//*
 }
        }

        return rate;
    }
}
*/
