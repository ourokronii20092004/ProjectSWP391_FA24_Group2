/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAOs.RatingDAO;
import DAOs.UserDAO;
import Models.Product;
import Models.Rating;
import Models.User;
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
@WebServlet(name="ManageRatingController", urlPatterns={"/ManageRatingController"})
public class ManageRatingController extends HttpServlet {
    private final RatingDAO ratingDAO = new RatingDAO();
    private static final Logger LOGGER = Logger.getLogger(RatingController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("productDetailManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        try {
            switch (action) {
                case "add":
                    handleAddRating(request, response);
                    break;
                case "deleteRating":
                    handleDeleteRating(request, response);
                    break;
                case "list":
                    handleListRatings(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    break;
            }

        } catch (SQLException | NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, "Error handling rating action: " + action, ex);
            handleError(request, response, "Database error: " + ex.getMessage());
        }
    }

    private void handleAddRating(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Integer userID = (Integer) request.getSession().getAttribute("userID");
        if (userID == null) {
            response.sendRedirect("LoginController");
            return;
        }

        int productID = Integer.parseInt(request.getParameter("productID")); // Assume productID is sent as a parameter
        System.out.println("handleAddRating :" + productID);
        // Check if the user has already rated this product
        Rating existingRating = ratingDAO.getRatingForUserAndProduct(userID, productID);
        if (existingRating != null) {
            // User already has a rating for this product; update it instead
            request.setAttribute("ratingID", existingRating.getRatingID()); // Use the existing rating ID for update
            handleUpdateRating(request, response);
        } else {
            // User has not rated this product; proceed with adding a new rating
            Rating rating = buildRatingFromRequest(request, userID);
            ratingDAO.addRating(rating);

            // Reload rating list for the product
            ArrayList<Rating> listRating = ratingDAO.viewAllRating(rating.getProductID());
            request.setAttribute("ratingList", listRating);
            //update sao tong
            int totalRatings = listRating.size();
            double averageRating = listRating.stream()
                    .mapToInt(Rating::getRatingValue)
                    .average()
                    .orElse(0.0);

            request.setAttribute("totalRatings", totalRatings);
            request.setAttribute("averageRating", averageRating);
            request.setAttribute("productID", productID);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("productDetailManagement.jsp");
            dispatcher.forward(request, response);
        }
    }

// Handle updating a rating
    private void handleUpdateRating(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Integer userID = (Integer) request.getSession().getAttribute("userID");
        if (userID == null) {
            response.sendRedirect("LoginController");
            return;
        }

        Rating rating = buildRatingFromRequest(request, userID);
        ratingDAO.updateRating(rating);
        // Reload rating list for the product
        ArrayList<Rating> listRating = ratingDAO.viewAllRating(rating.getProductID());
        request.setAttribute("ratingList", listRating);
       
        String productIDParam = request.getParameter("productID");
        int productID = Integer.parseInt(productIDParam);
        //update sao tong
        int totalRatings = listRating.size();
        double averageRating = listRating.stream()
                .mapToInt(Rating::getRatingValue)
                .average()
                .orElse(0.0);

        request.setAttribute("totalRatings", totalRatings);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("productID", productID);
        // Reload the rating list for the product after update

        // Forward to productDetails.jsp with updated ratings
        RequestDispatcher dispatcher = request.getRequestDispatcher("productDetailManagement.jsp");
        dispatcher.forward(request, response);
    }

private void handleDeleteRating(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {

    String ratingIDParam = request.getParameter("ratingID");
    String productIDParam = request.getParameter("productID");

    if (ratingIDParam == null || productIDParam == null) {
        handleError(request, response, "Missing required parameters: ratingID or productID");
        return;
    }

    try {
        int ratingID = Integer.parseInt(ratingIDParam);
        int productID = Integer.parseInt(productIDParam);

        ratingDAO.deleteRatingAsEmp(ratingID);

        // Reload the rating list for the product after deletion
        ArrayList<Rating> listRating = ratingDAO.viewAllRating(productID);
        request.setAttribute("ratingList", listRating);

        // Update the total ratings and average rating
        int totalRatings = listRating.size();
        double averageRating = listRating.stream()
                .mapToInt(Rating::getRatingValue)
                .average()
                .orElse(0.0);

        request.setAttribute("totalRatings", totalRatings);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("productID", productID);

        // Forward to productDetails.jsp with updated ratings
        RequestDispatcher dispatcher = request.getRequestDispatcher("productDetailManagement.jsp");
        dispatcher.forward(request, response);

    } catch (NumberFormatException ex) {
        handleError(request, response, "Invalid productID or ratingID format.");
    }
}


    private void handleListRatings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String productID = request.getParameter("productID");
            System.out.println("handleListRatings: " + productID);
            if (productID != null) {
                Product pro = new DAOs.ProductDAO().readProduct(Integer.parseInt(productID));
                ArrayList<Rating> listRating = ratingDAO.viewAllRating(Integer.parseInt(productID));
                int totalRatings = listRating.size();
                double averageRating = listRating.stream()
                        .mapToInt(Rating::getRatingValue)
                        .average()
                        .orElse(0.0); // Tính trung bình số sao

                // Đặt các giá trị này vào request
                request.setAttribute("product", pro);
                request.setAttribute("ratingList", listRating);
                request.setAttribute("totalRatings", totalRatings);
                request.setAttribute("averageRating", averageRating);
                request.setAttribute("productID", productID);

                // Chuyển hướng đến trang JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("productDetailManagement.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error listing ratings", ex);
            handleError(request, response, "Database error: " + ex.getMessage());
        }
    }

    private Rating buildRatingFromRequest(HttpServletRequest request, int userID) {
        String ratingID = request.getParameter("ratingID");
        String productID = request.getParameter("productID");
        String ratingValue = request.getParameter("ratingValue");
        String comment = request.getParameter("comment");
        String createdAt = request.getParameter("createdAt");

        Date dateCreated = (createdAt != null && !createdAt.isEmpty())
                ? Date.valueOf(createdAt)
                : new Date(System.currentTimeMillis());

        return new Rating(
                ratingID != null ? Integer.parseInt(ratingID) : 0,
                userID,
                productID != null ? Integer.parseInt(productID) : 0,
                ratingValue != null ? Integer.parseInt(ratingValue) : 0,
                comment,
                dateCreated
        );
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Rating Controller for handling product rating actions";
    }
}
