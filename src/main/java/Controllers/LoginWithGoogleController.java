/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
@WebServlet(name = "LoginWithGoogleController", urlPatterns = {"/LoginWithGoogleController"})
public class LoginWithGoogleController extends HttpServlet {

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
        String code = request.getParameter("code") == null ? "" : request.getParameter("code");
        if (!code.isEmpty()) {
            System.out.println(code);
            String[] infor = new Helper.LoginWithGoogle().getInforFromCode(code);
            for (int i = 0; i < infor.length; i++) {
                System.out.println(infor[i]);

            }
            int userID = new DAOs.AccountDAO().findUserIDByEmail(infor[0]);
            System.out.println(userID);
            User user = new DAOs.UserDAO().getUserData(userID);
            if (user != null) {
                if (user.isIsActive()) {
                    request.getSession().setAttribute("userID", userID);
                    // Redirect based on user role
                    if (user.getRoleID() == 1) {
                        response.sendRedirect("dashboard.jsp");
                    } else if (user.getRoleID() == 2) {
                        response.sendRedirect("homepage.jsp");
                    } else {
                        response.sendRedirect("dashboard.jsp");
                    }
                } else {
                    request.getRequestDispatcher("login.jsp?check=false").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("login.jsp?check=false").forward(request, response);
            }
        } else {
            response.sendRedirect(new Helper.LoginWithGoogle().getGoogleOAuthLoginURL());
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
