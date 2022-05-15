/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CardAPI", urlPatterns = { "/CardAPI" })
public class CardAPI extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cardName = request.getParameter("card");
		String name = request.getParameter("name");
		String date = request.getParameter("date");
		String cvc = request.getParameter("cvc");

		if (!name.isEmpty() || !cardName.isEmpty() || !date.isEmpty() || !cvc.isEmpty()) {
			Card card = new Card();
			boolean isAdded = card.add(cardName, name, date, cvc);
			if (isAdded) {
				request.setAttribute("alertMsg", "Card Details added Successfully!");
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("alertMsg", "Details adding Failed!");
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Card card = new Card();
		ResultSet result = card.getAll();
		if (result != null) {
			request.setAttribute("cardResult", result);
			RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
			rd.include(request, response);
		} else {
			request.setAttribute("alert", "Details not found!");
			RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
			rd.forward(request, response);
		}

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map paras = getParasMap(request);
		String id = paras.get("cardId").toString();
		String cardInfo = paras.get("card").toString();
		String name = paras.get("name").toString();
		String date = paras.get("date").toString();
		String cvc = paras.get("cvc").toString();

		System.out.println("ID :: " + id);
		System.out.println("card :: " + cardInfo);
		System.out.println("name :: " + name);
		System.out.println("date :: " + date);
		System.out.println("cvc :: " + cvc);
		if (!id.isEmpty() && !cardInfo.isEmpty() && !name.isEmpty() && !date.isEmpty() && !cvc.isEmpty()) {

			Card card = new Card();
			System.out.println(Integer.parseInt(id));
			boolean isUpdated = card.update(cardInfo, name, date, cvc, Integer.parseInt(id));
			System.out.println("isUP " + isUpdated);
			if (isUpdated) {
				request.setAttribute("alert", "Record is updated successfully!");
				RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("alert", "Record could not be updated!");
				RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
				rd.forward(request, response);
			}
		} else {
			request.setAttribute("alert", "All fields are required and cannot be empty");
			RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map paras = getParasMap(request);
		String id = paras.get("cardId").toString();
		System.out.println(request);
		System.out.println("ID :: " + id);
		if (!id.isEmpty()) {

			Card card = new Card();
			System.out.println(Integer.parseInt(id));
			boolean isDeleted = card.delete(Integer.parseInt(id));

			if (isDeleted) {
				request.setAttribute("alert", "Record is deleted successfully!");
				RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("alert", "Record could not be deleted!");
				RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
				rd.forward(request, response);
			}
		} else {
			request.setAttribute("alert", "ID not found");
			RequestDispatcher rd = request.getRequestDispatcher("table.jsp");
			rd.forward(request, response);
		}
	}

	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {
		}
		return map;
	}
}
