/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phanp
 */
@WebServlet(name = "CustomerProfileController", urlPatterns = {"/CustomerProfileController"})
public class CustomerProfileController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/CustomerProfileController")) {
            try {
                String userID = String.valueOf(request.getSession().getAttribute("userID"));
                System.out.println(userID);
                ArrayList<Models.Order> boughtHistory = new DAOs.OrderDAO().viewOrderListByUserID(Integer.parseInt(userID));
                request.setAttribute("boughtHistory", boughtHistory);
                request.getRequestDispatcher("userprofile.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
