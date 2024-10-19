/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAOs.CartDAO;
import DAOs.RatingDAO;
import Models.CartItem;
import Models.Rating;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Nhat Dang - CE180010 
 */
@WebServlet(name="CartController", urlPatterns={"/kj"})
public class CartController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        Models.User user = (Models.User) request.getSession().getAttribute("user");
        // Retrieve form data
        String cartItemID = request.getParameter("cartItemID");
        String userID = request.getParameter("userID");
        String productID = request.getParameter("productID");
        String quantity = request.getParameter("quantity");
        
        DAOs.CartDAO cartDAO = new CartDAO();
        ArrayList<CartItem> listCartItem = cartDAO.viewCartItemList(user.getId());
        //Set product list as a request attribute
        request.setAttribute("productList", listCartItem);
        // Forward the request to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Models.User user = (Models.User) request.getSession().getAttribute("user");
        int userID = user.getId();
        // Retrieve form data
        String cartItemID = request.getParameter("cartItemID");
        String productID = request.getParameter("productID");
        String quantity = request.getParameter("quantity");

        CartDAO cartDAO = new CartDAO();
        CartItem cartItem = new CartItem(Integer.parseInt(cartItemID), userID, Integer.parseInt(productID), Integer.parseInt(quantity));
        ArrayList<CartItem> listCart;
        try {
            listCart = cartDAO.viewCartItemList(userID);
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "list":
                    listCart = cartDAO.viewCartItemList(userID);
                    request.setAttribute("cartList", listCart);
                    break;
                case "add":
                    cartDAO.addCartItem(cartItem);
                    break;
                case "delete":
                    cartDAO.removeCartItem(cartItemID);
                    break;

                default:
                    listCart = cartDAO.viewCartItemList(userID);
                    request.setAttribute("cartList", listCart);
                    break;
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            // EXCEPTION
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
