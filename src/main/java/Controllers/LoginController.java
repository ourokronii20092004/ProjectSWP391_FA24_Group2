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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

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
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
            int userID = accountDAO.findUserID(acc.getUserName());
            User user = userDAO.getUserData(userID);
            HttpSession session = request.getSession();
            session.setAttribute("userID", userID);
            if (user.isIsActive()) {
                // Handle Remember Me
                String remember = request.getParameter("remember");
                if ("on".equals(remember)) {
                    // Create cookies for username and password
                    Cookie usernameCookie = new Cookie("username", username);
                    Cookie passwordCookie = new Cookie("password", password);
                    usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    passwordCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    response.addCookie(usernameCookie);
                    response.addCookie(passwordCookie);
                } else {
                    // If "Remember Me" is not selected, clear the cookies
                    Cookie usernameCookie = new Cookie("username", "");
                    Cookie passwordCookie = new Cookie("password", "");
                    usernameCookie.setMaxAge(0);
                    passwordCookie.setMaxAge(0);
                    response.addCookie(usernameCookie);
                    response.addCookie(passwordCookie);
                }

                // Redirect based on user role
                if (user.getRoleID() == 1) {

                    response.sendRedirect("/DashboardController");
                } else if (user.getRoleID() == 2) {
                    response.sendRedirect("/MainPageController");
                } else {
                    response.sendRedirect("/DashboardController");
                }
            } else {
                request.getRequestDispatcher("login.jsp?check=false").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("login.jsp?check=false").forward(request, response);
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
