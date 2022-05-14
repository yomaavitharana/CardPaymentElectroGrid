
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

@WebServlet(name = "AddCard", urlPatterns = {"/AddCard"})
public class AddCard extends HttpServlet {

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

        String card = request.getParameter("card");
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String cvc = request.getParameter("cvc");

        if (!name.isEmpty() || !card.isEmpty() || !date.isEmpty() || !cvc.isEmpty()) {

            try {
                Connection con = connect();
                if (con == null) {
                    request.setAttribute("alertMsg", "DataBase not connected!");
                    RequestDispatcher rd = request.getRequestDispatcher("Card.jsp");
                    rd.forward(request, response);
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
                    con.close();
                    request.setAttribute("alertMsg", "Card Details added Successfully!");
                    RequestDispatcher rd = request.getRequestDispatcher("Card.jsp");
                    rd.forward(request, response);

                }
            } catch (Exception e) {
                request.setAttribute("alertMsg", "Details adding Failed!");
                RequestDispatcher rd = request.getRequestDispatcher("Card.jsp");
                rd.forward(request, response);

            }

        } else {
            request.setAttribute("alertMsg", "Details adding Failed!");
            RequestDispatcher rd = request.getRequestDispatcher("Card.jsp");
            rd.forward(request, response);
        }
    }
}
