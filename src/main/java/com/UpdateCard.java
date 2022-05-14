package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateCard", urlPatterns = {"/UpdateCard"})
public class UpdateCard extends HttpServlet {

    private Connection connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/payment", "root", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String card = request.getParameter("card");
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String cvc = request.getParameter("cvc");

        if (!id.isEmpty()) {
            try {
                Connection con = connect();
                if (con == null) {
                    request.setAttribute("alertMsg", "DataBase not connected!");
                    RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
                    rd.forward(request, response);
                }
                String query = "UPDATE payments SET `card`=?,`name`=?,`date`=?,`cvc`=? WHERE `id`=?";
                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setString(1, card);
                preparedStmt.setString(2, name);
                preparedStmt.setString(3, date);
                preparedStmt.setString(4, cvc);
                preparedStmt.setInt(5, Integer.parseInt(id));
                preparedStmt.execute();
                con.close();
                request.setAttribute("alertMsg", "Card Details Updated Successfully!");
                RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                request.setAttribute("alertMsg", "Card Details upadating failed!");
                RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
                rd.forward(request, response);
            }
        }
    }

}
