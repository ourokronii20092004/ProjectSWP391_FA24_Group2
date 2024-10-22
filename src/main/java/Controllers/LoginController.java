/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.UserDAO;
import Models.Account;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            if (user.getRoleID() == 1) {
                // Admin role
                response.sendRedirect("dashboard.jsp");
            } else if (user.getRoleID() == 2) {
                // Other role
                response.sendRedirect("blabla.jsp");
            } else {
                // Default role, or another user role
                response.sendRedirect("blabla.jsp");
            }
        } else {
            // User is not logged in
            response.sendRedirect("login.jsp?check=false");
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

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Account acc = DAOs.LoginDAO.Validate(username, password);

        if (acc != null) {
            AccountDAO accountDAO = new AccountDAO();
            UserDAO userDAO = new UserDAO();

            // Find user by username and load full user data
            int userID = accountDAO.findUserID(acc.getUserName());
            User user = userDAO.getUserData(userID);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on role (handled in doGet)
            response.sendRedirect(request.getContextPath() + "/your-servlet-url");

        } else {
            // Invalid login credentials
            response.sendRedirect("login.jsp?check=false");
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
