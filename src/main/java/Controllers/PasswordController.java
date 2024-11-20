/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Hash.HashFunction;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author phanp
 */
@WebServlet(name = "PasswordController", urlPatterns = {"/PasswordController"})
public class PasswordController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        System.out.println(request.getParameter("action"));
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equalsIgnoreCase("forgetPassword")) {
                request.getRequestDispatcher("forgetPassword.jsp").forward(request, response);
            } else if (request.getParameter("action").equalsIgnoreCase("changePassword")) {
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            } else if (request.getParameter("action").equalsIgnoreCase("noUser")) {
                request.setAttribute("action", "noUser");
                request.getRequestDispatcher("forgetPassword.jsp").forward(request, response);
            } else {
                response.sendRedirect("/MainPageController");
            }
        } else {
            String userID = String.valueOf(request.getSession().getAttribute("userID"));
            System.out.println(userID);
            String password = request.getParameter("oldPassword"),
                    newPassword = request.getParameter("newPassword"),
                    confirmPassword = request.getParameter("confirmPassword");
            User user = new DAOs.UserDAO().getUserData(Integer.parseInt(userID));
            if (!HashFunction.comparePasswords(password, user.getSalt(), user.getPassword())) {
                // Mật khẩu cũ không đúng
                request.setAttribute("error", "Old password is incorrect.");
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            } else if (password.equals(newPassword)) {
                // Mật khẩu mới trùng với mật khẩu cũ
                request.setAttribute("error", "New password must not be the same as the old password.");
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
} else if (!newPassword.equals(confirmPassword)) {
                // Mật khẩu mới và xác nhận không khớp
                request.setAttribute("error", "New password and confirm password do not match.");
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            } else if (HashFunction.comparePasswords(password, user.getSalt(), user.getPassword()) && newPassword.equalsIgnoreCase(confirmPassword)) {
                byte[] salt = HashFunction.generateSalt();
                user.setPassword(HashFunction.hashPassword(newPassword, salt));
                new DAOs.AccountDAO().updatePassword(user.getId(), user.getPassword(), HashFunction.getSaltStringType(salt));
                new Helper.SendEmail().sendConfirmChangePassword(user);
                response.sendRedirect("/LogoutController");
            } else {
                request.getRequestDispatcher("forgetPassword.jsp").forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = new DAOs.AccountDAO().findUserIDByEmail(request.getParameter("email"));
        System.out.println(userID);
        User user = new DAOs.UserDAO().getUserData(userID);
        if (user != null) {
            if (user.isIsActive()) {
                String newPassword = generateRandomPassword(10);
                System.out.println(newPassword);
                byte[] salt = Hash.HashFunction.generateSalt();
                String password = Hash.HashFunction.hashPassword(newPassword, salt);

                new DAOs.AccountDAO().updatePassword(userID, password, HashFunction.getSaltStringType(salt));
                user.setPassword(newPassword);
                new Helper.SendEmail().sendPasswordIfUserForget(user);
                response.sendRedirect("/PasswordController?action=forgetPassword");
                return;
            }
            response.sendRedirect("/PasswordController");
        } else {
            response.sendRedirect("/PasswordController?action=noUser");
        }
    }

    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

}