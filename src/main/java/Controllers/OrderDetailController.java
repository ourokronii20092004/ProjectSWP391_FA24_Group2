/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.OrderDAO;
import DAOs.ProductDAO;
import Models.Order;
import Models.OrderItem;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
@WebServlet(name = "OrderDetailController", urlPatterns = {"/OrderDetailController"})
public class OrderDetailController extends HttpServlet {

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
        if (path.equals(contextPath + "/OrderDetailController")) {
            System.out.println("Detail");
            int orderID = Integer.parseInt(request.getParameter("orderID"));
            Models.Order order = new DAOs.OrderDAO().readOrder(orderID);
            if (order != null) {
                request.setAttribute("order", order);
                RequestDispatcher ds = request.getRequestDispatcher("OrderDetailForm.jsp");
                ds.forward(request, response);
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
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        String action = request.getParameter("action");
        System.out.println("action: " + action);
        OrderDAO orDAO = new OrderDAO();
        if (action.equalsIgnoreCase("Waitting")) {
            System.out.println("Confirm action:" + orDAO.updateOrderStatus(orderID, action));
        } else if (action.equalsIgnoreCase("Canceled")) {
            ProductDAO pro = new ProductDAO();
            System.out.println("Canceled action:" + orDAO.updateOrderStatus(orderID, action));
            Order or = orDAO.readOrder(orderID);
            for (OrderItem o : or.getOrderItemList()) {
                pro.updateProductStockRefuse(o.getProduct().getProductID(), o.getQuantity());
                System.out.println("Restock: " + o.getProduct().getProductName());
            }

        } else if (action.equalsIgnoreCase("Confirm")) {
            System.out.println("Confirm action:" + orDAO.updateOrderStatus(orderID, action));
        }
        response.sendRedirect("/OrderDetailController?orderID=" + orderID);

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
