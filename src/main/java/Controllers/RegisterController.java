/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 *
 * @author CE181515 - Phan Viet Phat
 */
@WebServlet("/RegisterController/*")
public class RegisterController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI(),
                contextPath = request.getContextPath();
        System.out.println("Requested Path: " + path);
        System.out.println("Context Path: " + contextPath);
        if (path.equals(contextPath + "/RegisterController")) {
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (path.contains(contextPath + "/RegisterController/Confirm/")) {
            doPost(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI(),
                contextPath = request.getContextPath();
        System.out.println("Requested Path: " + path);
        System.out.println("Context Path: " + contextPath);
        if (path.equalsIgnoreCase("/RegisterController/Create")) {
            String username = request.getParameter("username"),
                    password = request.getParameter("password"),
                    confirmPassword = request.getParameter("confirmPassword"),
                    email = request.getParameter("email");
            System.out.println(username);
            System.out.println(password);
            System.out.println(confirmPassword);
            System.out.println(email);
            if (password.equals(confirmPassword)) {
                byte[] salt = Hash.HashFunction.generateSalt();
                password = Hash.HashFunction.hashPassword(password, salt);
                Models.User newuser = new Models.User(0, username, Hash.HashFunction.getSaltStringType(salt), password, email, "", "", "", "", "", 2, false, null, null);
                if (new DAOs.CustomerDAO().addCustomer(newuser) == true) {
                    request.getSession().setAttribute("action", "success");
                    new Helper.SendEmail().sendConfirmEmailForm(newuser);
                } else {
                    request.getSession().setAttribute("action", "fail");
                }
                response.sendRedirect("/RegisterController");
            } else {
                request.getSession().setAttribute("action", "password");
                response.sendRedirect("/RegisterController");
            }
        } else if (path.contains(contextPath + "/RegisterController/Confirm/")) {
            String username = request.getParameter("username");
            System.out.println(username);
            String password = path.split("Confirm")[1].substring(1);
            System.out.println(password);
            DAOs.AccountDAO accDAO = new AccountDAO();
            Models.Account acc = accDAO.findUserbyUsername(username);
            if (acc != null) {
                if (!acc.isIsActive() && acc.getPassword().equals(password)) {
                    accDAO.recoverAcconut(accDAO.findUserID(username));
                }
                response.sendRedirect("/LoginController?check=register");
            }
        } else {
            response.sendRedirect("index.jsp");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
