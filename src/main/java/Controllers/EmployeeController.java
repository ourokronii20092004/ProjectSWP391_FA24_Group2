/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Helper.ImageHelper;
import Models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
@WebServlet("/EmployeeController/*")
@MultipartConfig
public class EmployeeController extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployeeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        String path = request.getRequestURI(),
                contextPath = request.getContextPath();
        System.out.println("Requested Path: " + path);
        System.out.println("Context Path: " + contextPath);

        if (path.startsWith("/EmployeeController")) {
            if (path.contains("deactivate")) {
                System.out.println("deactivate");
                doPost(request, response);
            } else if (path.contains("restore")) {
                System.out.println("restore");
                doPost(request, response);
            } else {
                System.out.println("list");
                ArrayList<User> empList = new DAOs.EmployeeDAO().viewEmployeeList();
                request.setAttribute("empList", empList);
                RequestDispatcher ds = request.getRequestDispatcher("EmployeeManagement.jsp");
                ds.forward(request, response);
                return;
            }
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

        User emp = new User(Integer.parseInt(userID), username, null, null, email, firstName, lastName, phoneNumber, address, null, 3, true, null, null);
        if (path.startsWith("/EmployeeController")) {
            DAOs.EmployeeDAO DAO = new DAOs.EmployeeDAO();
            if (path.contains("add")) {
                byte[] salt = Hash.HashFunction.generateSalt();
                password = Hash.HashFunction.hashPassword(password, salt);
                emp.setSalt(Hash.HashFunction.getSaltStringType(salt));
                emp.setPassword(password);
                Part img = request.getPart("pic");
                String imageUrl = ImageHelper.saveImage(img, "pro", getServletContext().getRealPath("/"));
                emp.setImgURL(imageUrl);
                if (DAO.addEmployee(emp)) {
                    request.getSession().setAttribute("action", "add");
                } else {
                    request.getSession().setAttribute("action", "notAdd");
                }

            } else if (path.contains("edit")) {
                Part img = request.getPart("pic") == null ? null : request.getPart("pic");
                if (img == null) {
                    String imageUrl = ImageHelper.saveImage(img, "pro", getServletContext().getRealPath("/"));
                    emp.setImgURL(imageUrl);
                }
                if (DAO.updateEmployee(emp)) {
                    request.getSession().setAttribute("action", "edit");
                } else {
                    request.getSession().setAttribute("action", "notEdit");
                }

            } else if (path.contains("deactivate")) {
                if (DAO.removeEmployee(emp.getId())) {
                    request.getSession().setAttribute("action", "remove");
                } else {
                    request.getSession().setAttribute("action", "notRemove");
                }
            } else if (path.contains("restore")) {
                if (DAO.restoreEmployee(emp.getId())) {
                    request.getSession().setAttribute("action", "restore");
                } else {
                    request.getSession().setAttribute("action", "notRestore");
                }
            }
        }
        response.sendRedirect("/EmployeeController");
    }
}
