/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;


public class Card {

    private Connection connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/payment", "root", "chaiya9865");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public boolean add(String card, String name, String date, String cvc) {
        boolean isAdded = false;
        try {
            Connection con = connect();
            if (con == null) {
                System.err.println("Connection not established");
            } else {
                String query = " insert into payments(`id`,`card`,`name`,`date`,`cvc`)"
                        + " values(?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setInt(1, 0);
                preparedStmt.setString(2, card);
                preparedStmt.setString(3, name);
                preparedStmt.setString(4, date);
                preparedStmt.setString(5, cvc);
                preparedStmt.execute();
                isAdded = true;
                con.close();
                return isAdded;

            }
        } catch (Exception e) {

            System.err.println("Card Details could not be added!");
        }
        return isAdded;
    }

    public ResultSet getAll() {
        ResultSet result = null;
        Connection con = connect();
        if (con == null) {

            System.err.println("Connection not established");
        } else {
            try {
                String query = "SELECT * FROM payments";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                return preparedStmt.executeQuery(query);
            } catch (Exception e) {
                System.err.println("Card payments information.");
            }
        }
        return result;
    }

    public boolean delete(int id) {
        boolean isDeleted = false;
        try {
            Connection con = connect();
            if (con == null) {

            } else {
                String query = "delete from payments where `id`=?";
                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setInt(1, id);

                preparedStmt.execute();
                isDeleted = true;
                con.close();
                return isDeleted;
            }

        } catch (Exception e) {
            System.err.println("Card payments information.");
        }
        return isDeleted;
    }

    public boolean update(String card, String name, String date, String cvc, int id) {
        boolean isUpdated = false;
        try {
            Connection con = connect();
            if (con == null) {

                System.err.println("Connection not established");
            } else {
                String query = "UPDATE payments SET `card`=?,`name`=?,`date`=?,`cvc`=? WHERE `id`=?";
                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setString(1, card);
                preparedStmt.setString(2, name);
                preparedStmt.setString(3, date);
                preparedStmt.setString(4, cvc);
                preparedStmt.setInt(5, id);
                preparedStmt.execute();
                con.close();

                return true;
            }

        } catch (Exception e) {
            System.err.println("Update failed");
        }
        return isUpdated;
    }
}
