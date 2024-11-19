/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CartDAO;
import DAOs.OrderDAO;
import DAOs.OrderItemDAO;
import DAOs.ProductDAO;
import DAOs.UserDAO;
import DAOs.VoucherDAO;
import Models.CartItem;
import Models.Order;
import Models.OrderItem;
import Models.Voucher;
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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Product; // Import Product
import Models.User;    // Import User
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
@WebServlet(name = "OrderController", urlPatterns = {"/OrderController/*"})
public class OrderController extends HttpServlet {

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

        if (path.equals(contextPath + "/OrderController")) {
            System.out.println("list");
            ArrayList<Order> orderList = new DAOs.OrderDAO().viewAllOrders();
            request.setAttribute("orderList", orderList);
            RequestDispatcher ds = request.getRequestDispatcher("adminSetting.jsp");
            ds.forward(request, response);
        } else {
            response.sendRedirect("/OrderController");
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
        String action = request.getParameter("action");
        if ("buySelected".equals(action)) {
            try {
                handleBuySelected(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("createOrder".equals(action)) {
            try {
                handleCreateOrder(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void handleBuySelected(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String selectedItemsDetails = request.getParameter("selectedItemsDetails");

        System.out.println(selectedItemsDetails);

        if (selectedItemsDetails == null || selectedItemsDetails.isEmpty()) {
            request.setAttribute("errorMessage", "No items selected for purchase.");
            response.sendRedirect("cart.jsp?error=NoItemsSelected");
            return;
        }

        int userID = (int) request.getSession().getAttribute("userID");
        java.sql.Date orderDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        OrderDAO orderDAO = new OrderDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        ProductDAO productDAO = new ProductDAO();

        ArrayList<OrderItem> orderItemsList = new ArrayList<>();
        float totalAmount = 0;
        int newOrderID = orderDAO.getLastInsertedOrderID();
        Order existingOrder = null;

        Order newOrder;
        if (orderDAO.readOrder(newOrderID).getTotalAmount() == 0) {
            // Sử dụng lại Order cũ nếu TotalAmount = 0
            newOrder = orderDAO.readOrder(newOrderID);
        } else {
            newOrder = new Order(
                    0,
                    new UserDAO().getUserData(userID),
                    new ArrayList<>(),
                    null,
                    orderDate,
                    0,
                    "Pending"
            );
            orderDAO.addOrder(newOrder);
            newOrderID = orderDAO.getLastInsertedOrderID();
            System.out.println("newOrderID = " + newOrderID);
            newOrder = orderDAO.readOrder(newOrderID);
        }
        
        System.out.println("total : " + orderDAO.readOrder(newOrderID).getTotalAmount());
        String[] selectedCartItemIds = selectedItemsDetails.split(",");
        CartDAO cartDAO = new CartDAO(); // Initialize cartDAO here

        for (String cartItemIdStr : selectedCartItemIds) {

            try {
                int cartItemId = Integer.parseInt(cartItemIdStr);

                CartItem cartItem = cartDAO.getCartItemByUserID(userID, cartItemId); // Get CartItem details

                if (cartItem != null) {
                    Product product = productDAO.getProductByID(cartItem.getProductID()); // Get Product details

                    if (product != null) {  // Check if product exists

                        OrderItem orderItem = new OrderItem(
                                0, // OrderItemID (auto-incrementing, so set to 0)
                                newOrderID,
                                product,
                                cartItem.getQuantity(),
                                product.getPrice() // Use product price
                        );
                        orderItemDAO.addOrderItem(orderItem); // Add to database

                        totalAmount += product.getPrice() * cartItem.getQuantity(); // Update total amount

                        // Add orderItem to the list (if you need it later)
                        orderItemsList.add(orderItem);

                    } else {
                        // Handle the case where the product is not found (e.g., deleted)
                        System.err.println("Product not found for cartItemId: " + cartItemId);
                        // Consider adding error handling logic here, like redirecting with an error message
                    }
                } else {
                    System.err.println("Cart item not found for cartItemId: " + cartItemId);
                }

            } catch (NumberFormatException e) {
                // Handle parsing error
                System.err.println("Error parsing cart item ID: " + e.getMessage());
            }
        }
System.out.println("total2 : " + orderDAO.readOrder(newOrderID).getTotalAmount());
        newOrder.setTotalAmount(totalAmount);
        //orderDAO.updateOrderTotal(newOrder);

        // Forward to confirmation page
        request.setAttribute("orderItemsList", orderItemsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("OrderController.jsp");
        dispatcher.forward(request, response);
        System.out.println(selectedItemsDetails + "  orderItemID");
        HttpSession session = request.getSession();
        session.setAttribute("selectedItemsDetails", selectedItemsDetails);
        System.out.println("total3 : " + orderDAO.readOrder(newOrderID).getTotalAmount());
    }

    private void handleCreateOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        // Lấy "selectedItemsDetails" từ Session
        String selectedItemsDetails = (String) session.getAttribute("selectedItemsDetails");
        System.out.println(selectedItemsDetails + "  orderItemID");
        String[] selectedCartItemIds = selectedItemsDetails.split(",");
        int userID = (int) request.getSession().getAttribute("userID");
        float totalAmount = Float.parseFloat(request.getParameter("totalAmount"));
        int voucherID = Integer.parseInt(request.getParameter("voucherID"));
        System.out.println(voucherID + "   Voucher");
        System.out.println("Total : " + totalAmount);

        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new ProductDAO();
        int orderID = orderDAO.getLastInsertedOrderID();

        if (orderID != -1) { // Check if orderID was retrieved successfully
            Order orderToUpdate = orderDAO.readOrder(orderID); // Fetch the Order object

            if (orderToUpdate != null) {
                orderToUpdate.setTotalAmount(totalAmount);
                orderToUpdate.setVoucher(new VoucherDAO().getVoucherById(voucherID));
                if (orderDAO.updateOrderByID(orderToUpdate)) { // Use updateOrderByID
                    // Remove cart items (if applicable - make sure CartDAO.removeCartItem is correctly implemented)
                    String[] productIDs = request.getParameterValues("productID[]");
                    String[] quantities = request.getParameterValues("quantity[]");
                    if (productIDs != null && quantities != null && productIDs.length == quantities.length) {
                        for (int i = 0; i < productIDs.length; i++) {
                            try {
                                int productID = Integer.parseInt(productIDs[i]);
                                int quantity = Integer.parseInt(quantities[i]);

                                if (!productDAO.updateProductStock(productID, quantity)) {
                                    System.err.println("Failed to update stock for product ID: " + productID);
                                    response.sendRedirect("error.jsp?message=StockUpdateFailed");
                                    return;
                                }

                            } catch (NumberFormatException e) {
                                System.err.println("Error parsing product ID or quantity: " + e.getMessage());
                                response.sendRedirect("error.jsp?message=ParsingError");
                                return;
                            }
                        }
                    }
                    CartDAO cartDAO = new CartDAO();
                    if (productIDs != null && quantities != null && productIDs.length == quantities.length) {
                        for (String cartItemIdStr : selectedCartItemIds) {
                            int cartItemId = Integer.parseInt(cartItemIdStr);
                            cartDAO.removeCartItem(cartItemId); // Correctly remove items
                        }
                    }
                    response.sendRedirect("MainPageController");
                } else {
                    System.err.println("Failed to update order.");
                    response.sendRedirect("error.jsp?message=OrderUpdateFailed");
                }

            } else {
                System.err.println("Order not found for ID: " + orderID);
                response.sendRedirect("error.jsp?message=OrderNotFound");
            }
        } else {
            System.err.println("Failed to get last inserted order ID.");
            response.sendRedirect("error.jsp?message=OrderIDRetrievalFailed");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "OrderController";
    }// </editor-fold>

}
