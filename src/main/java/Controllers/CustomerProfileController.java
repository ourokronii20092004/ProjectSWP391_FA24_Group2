/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CustomerDAO;

import Helper.ImageHelper;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.Part;

/**
 *
 * @author phanp
 */
@WebServlet(name = "CustomerProfileController", urlPatterns = {"/CustomerProfileController"})

@MultipartConfig

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

        String userID = request.getParameter("userID") == null ? "-1" : request.getParameter("userID");
        String EditFirstName = request.getParameter("editFirstName");
        String EditLastName = request.getParameter("editLastName");
        String EditEmail = request.getParameter("EditEmail");
        String EditPhonenumber = request.getParameter("editPhoneNumber");
        String EditAddress = request.getParameter("editAddress");
        System.out.println(EditFirstName);
        System.out.println(EditLastName);
        System.out.println(EditEmail);
        System.out.println(EditPhonenumber);
        System.out.println(EditAddress);

        CustomerDAO dao = new CustomerDAO();
        User obj = new User(Integer.parseInt(userID), EditEmail, EditFirstName, EditLastName, EditPhonenumber, null, EditAddress, null, null);
        System.out.println(userID);

        if (request.getParameter("saveButton") != null) {

            if (!dao.updateCustomer(obj)) {
                response.sendRedirect("/err" + obj);
            } else {
                response.sendRedirect("/CustomerProfileController");
            }

        } else if (request.getParameter("saveAvatarButton") != null) {
            Part img = request.getPart("changeAvatarButton");
            String imageUrl = ImageHelper.saveImage(img, "cus", getServletContext().getRealPath("/"));
            obj.setImgURL(imageUrl);
            if (!dao.updateIMG(obj)) {
                response.sendRedirect("/err" + obj);
            } else {
                response.sendRedirect("/CustomerProfileController");
            }
        }

    }
}
