package Controllers;

import DAOs.RatingDAO;
import Models.Rating;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RatingController", urlPatterns = {"/RatingController"})
public class RatingController extends HttpServlet {

    private final RatingDAO ratingDAO = new RatingDAO();
    private static final Logger LOGGER = Logger.getLogger(RatingController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("productDetails.jsp").forward(request, response);
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
                case "deleteAsEmployee":
                    handleDeleteAsEmployee(request, response);
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
        int productID = 1;
        //int productID = Integer.parseInt(request.getParameter("productID")); // Assume productID is sent as a parameter

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

            // Forward to productDetails.jsp with updated data
            RequestDispatcher dispatcher = request.getRequestDispatcher("productDetails.jsp");
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

        // Reload the rating list for the product after update
        ArrayList<Rating> listRating = ratingDAO.viewAllRating(rating.getProductID());
        request.setAttribute("ratingList", listRating);

        // Forward to productDetails.jsp with updated ratings
        RequestDispatcher dispatcher = request.getRequestDispatcher("productDetails.jsp");
        dispatcher.forward(request, response);
    }

// Handle deleting a rating
    private void handleDeleteRating(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Integer userID = (Integer) request.getSession().getAttribute("userID");
        if (userID != null) {
            String ratingID = request.getParameter("ratingID");
            if (ratingID != null) {
                // Fetch the productID associated with the rating before deleting
                int productID = ratingDAO.getProductIDByRatingID(Integer.parseInt(ratingID));
                if (productID != -1) {  // Check if a valid productID is returned
                    ratingDAO.deleteRating(Integer.parseInt(ratingID), userID);

                    // Reload the rating list for the product after deletion
                    ArrayList<Rating> listRating = ratingDAO.viewAllRating(productID);
                    request.setAttribute("ratingList", listRating);

                    // Forward to productDetails.jsp with updated ratings
                    RequestDispatcher dispatcher = request.getRequestDispatcher("productDetails.jsp");
                    dispatcher.forward(request, response);
                } else {
                    handleError(request, response, "Rating not found or invalid");
                }
            }
        } else {
            response.sendRedirect("LoginController");
        }
    }

    private void handleDeleteAsEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String ratingID = request.getParameter("ratingID");
        if (ratingID != null) {
            ratingDAO.deleteAllRating(Integer.parseInt(ratingID));
        }

    }

    private void handleListRatings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // String productID = request.getParameter("productID");
            String productID = "1";
            if (productID != null) {
                ArrayList<Rating> listRating = ratingDAO.viewAllRating(Integer.parseInt(productID));
                request.setAttribute("ratingList", listRating);
                RequestDispatcher dispatcher = request.getRequestDispatcher("productDetails.jsp");
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
        //String productID = request.getParameter("productID");
        String productID = "1";
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
