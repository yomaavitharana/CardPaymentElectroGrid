
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


@WebServlet(name = "DeleteCard", urlPatterns = {"/DeleteCard"})
public class DeleteCard extends HttpServlet {

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
                   request.setAttribute("alert", "DataBase not connected!");
                RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
                rd.forward(request, response);
            }
     
            String query = "delete from payments where `id`=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
         
            preparedStmt.setInt(1, Integer.parseInt(id));
            
            preparedStmt.execute();
            con.close();
            request.setAttribute("alert", "Card Details Deleted Successfully!");
                RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
                rd.forward(request, response);
            
        } catch (Exception e) {
              request.setAttribute("alert", "Card Details Deleting Failed!");
                RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
                rd.forward(request, response);
            System.err.println(e.getMessage());
        }
        }
    }

}
