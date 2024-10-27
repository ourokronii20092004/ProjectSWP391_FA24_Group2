/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CartDAO;
import Models.CartItem;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        request.getRequestDispatcher("cart.jsp").forward(request, response);
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
        int userID =(int)request.getSession().getAttribute("userID");
        //int userID = 2;
        CartDAO cartDAO = new CartDAO();
        //check session
//       if (userID != 1 || userID != 2 || userID != 3 ) {
//            // Nếu userID không có, chuyển về trang đăng nhập
//            response.sendRedirect("/LoginController");
//            return;
//        }
        try {
            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "list":
                    // Retrieve list of cart items
                    ArrayList<CartItem> listCart = cartDAO.viewCartItemList(userID);
                    // Set attributes for cart and products
                    request.setAttribute("cartList", listCart);

                    break;
                case "update":
                    int cartItemID = Integer.parseInt(request.getParameter("cartItemId"));
                    int updatedQuantity = Integer.parseInt(request.getParameter("quantity"));

                    // Update the quantity in the database
                    cartDAO.updateCartItemQuantity(cartItemID, updatedQuantity);

                    // Refresh the cart list after updating
                    listCart = cartDAO.viewCartItemList(userID);
                    request.setAttribute("cartList", listCart);
                    break;

                case "deleteSelected":
                    String selectedItems = request.getParameter("selectedItems");
                    if (selectedItems != null && !selectedItems.isEmpty()) {
                        String[] cartItemIDs = selectedItems.split(",");
                        for (String id : cartItemIDs) {
                            cartDAO.removeCartItem(Integer.parseInt(id));
                        }
                    }
                    listCart = cartDAO.viewCartItemList(userID);
                    request.setAttribute("cartList", listCart);
                    break;

                case "add":
                    int productID = Integer.parseInt(request.getParameter("productID"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    cartDAO.addCartItem(userID,productID,quantity);
                    request.setAttribute("successMessage", "Item added to cart successfully!");
                    listCart = cartDAO.viewCartItemList(userID);
                    request.setAttribute("cartList", listCart);
                    break;

                case "delete":
                    int deleteCartItemID = Integer.parseInt(request.getParameter("cartItemId"));
                    cartDAO.removeCartItem(deleteCartItemID);
                    listCart = cartDAO.viewCartItemList(userID);
                    request.setAttribute("cartList", listCart);
                    break;

                default:
                    // Always retrieve the cart list in case of other actions
                    listCart = cartDAO.viewCartItemList(userID);
                    request.setAttribute("cartList", listCart);
                    break;
            }

            // Forward the updated list to the cart page
            RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
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
