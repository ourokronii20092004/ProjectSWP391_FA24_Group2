/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.UserDAO;


import Models.User;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author phanp
 */
@WebServlet("/CustomerController/*")
public class CustomerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI(),
                contextPath = request.getContextPath();
        System.out.println("Requested Path: " + path);
        System.out.println("Context Path: " + contextPath);

        if (path.startsWith("/CustomerController")) {
            if (path.contains("deactivate")) {
                System.out.println("deactivate");
                doPost(request, response);
            } else {
                System.out.println("list");
                ArrayList<User> cusList = new DAOs.CustomerDAO().viewCustomerList();
                request.setAttribute("cusList", cusList);
                RequestDispatcher ds = request.getRequestDispatcher("customerManagement.jsp");
                ds.forward(request, response);
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI(),
                contextPath = request.getContextPath();
        System.out.println("Requested Path: " + path);
        System.out.println("Context Path: " + contextPath);
        String userID = request.getParameter("userID") == null ? "-1" : request.getParameter("userID"),
                username = request.getParameter("username"),
                password = request.getParameter("password"),
                address = request.getParameter("address"),
                email = request.getParameter("email"),
                firstName = request.getParameter("firstName"),
                lastName = request.getParameter("lastName"),
                phoneNumber = request.getParameter("phoneNumber");
        System.out.println(userID);
        System.out.println(username);
        System.out.println(password);
        System.out.println(address);
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(phoneNumber);
        System.out.println(email);
        File pic = null;
        User cus = new User(Integer.parseInt(userID), username, null, null, email, firstName, lastName, phoneNumber, address, null, 2, true, null, null);
        String action = (String) request.getSession().getAttribute("action");
        if (path.startsWith("/customerController")) {
            DAOs.CustomerDAO DAO = new DAOs.CustomerDAO();
            if (path.contains("add")) {
                byte[] salt = Hash.HashFunction.generateSalt();
                password = Hash.HashFunction.hashPassword(password, salt);
                cus.setSalt(Hash.HashFunction.getSaltStringType(salt));
                cus.setPassword(password);
                DAO.addCustomer(cus);
            } else if (path.contains("edit")) {
                DAO.updateCustomer(cus);

            } else if (path.contains("deactivate")) {
                System.out.println(cus.getId());
                DAO.removeCustomer(cus.getId());
            }
        }
        response.sendRedirect("/CustomerController");
    }
}
