/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ProductDAO;
import DAOs.CartDAO;
import Models.CartItem;
import Models.Product;
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
@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
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
        int userID = (int) request.getSession().getAttribute("userID");
        // Retrieve form data

        CartDAO cartDAO = new CartDAO();
        ProductDAO productDAO = new ProductDAO();
        
        try {

            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "list":
                    // Retrieve list of cart items
                    ArrayList<CartItem> listCart = cartDAO.viewCartItemList(userID);

                    // Create a list of products for each cart item
                    ArrayList<Product> listProduct = new ArrayList<>();
                    for (CartItem cartItem : listCart) {
                        Product product = productDAO.getProductByID(cartItem.getProductID());
                        listProduct.add(product);
                    }

                    // Set attributes for cart and products
                    request.setAttribute("cartList", listCart);
                    request.setAttribute("productList", listProduct);
                    break;
                case "add":
                    // Retrieve product ID and quantity from request
                    int productID = Integer.parseInt(request.getParameter("productID"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    // Create a new CartItem for the current user
                    CartItem newItem = new CartItem(0, userID, productID, quantity); 

                    // Add the item to the cart
                    cartDAO.addCartItem(newItem);

                    // Refresh the cart list
                    listCart = cartDAO.viewCartItemList(userID);
                    listProduct = new ArrayList<>();
                    for (CartItem item : listCart) {
                        listProduct.add(productDAO.getProductByID(item.getProductID()));
                    }

                    // Set attributes and forward to the cart page
                    request.setAttribute("cartList", listCart);
                    request.setAttribute("productList", listProduct);
                    break;
                case "delete":
                    // Retrieve CartItemID from request
                    int cartItemID = Integer.parseInt(request.getParameter("cartItemId"));

                    // Remove the cart item
                    cartDAO.removeCartItem(cartItemID);

                    // Refresh the cart list
                    listCart = cartDAO.viewCartItemList(userID);
                    listProduct = new ArrayList<>();
                    for (CartItem item : listCart) {
                        listProduct.add(productDAO.getProductByID(item.getProductID()));
                    }

                    // Set attributes and forward to the cart page
                    request.setAttribute("cartList", listCart);
                    request.setAttribute("productList", listProduct);
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
