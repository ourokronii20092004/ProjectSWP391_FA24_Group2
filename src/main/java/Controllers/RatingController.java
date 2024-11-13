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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        // This method can be used to handle common code across GET and POST if needed.
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productID = request.getParameter("productID");

            if (productID == null || productID.isEmpty()) {
                response.sendRedirect("error.jsp");
                return;
            }

            RatingDAO ratingDAO = new RatingDAO();
            ArrayList<Rating> listRating = ratingDAO.viewAllRating(Integer.parseInt(productID));
            request.setAttribute("ratingList", listRating);

            RequestDispatcher dispatcher = request.getRequestDispatcher("productDetails.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Error loading product ratings: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userID = (int) request.getSession().getAttribute("userID");

            String ratingID = request.getParameter("ratingID");
            String productID = request.getParameter("productID");
            String ratingValue = request.getParameter("ratingValue");
            String comment = request.getParameter("comment");
            String createdAt = request.getParameter("createdAt");
            String action = request.getParameter("action");

            if (productID == null || ratingValue == null || comment == null || createdAt == null || action == null) {
                response.sendRedirect("error.jsp");
                return;
            }

            RatingDAO ratingDAO = new RatingDAO();
            Rating rating = new Rating(
                    ratingID != null ? Integer.parseInt(ratingID) : 0,
                    userID,
                    Integer.parseInt(productID),
                    Integer.parseInt(ratingValue),
                    comment,
                    Date.valueOf(createdAt)
            );

            switch (action) {
                case "add":
                    ratingDAO.addRating(rating);
                    break;
                case "updateRating":
                    ratingDAO.updateRating(rating);
                    break;
                case "deleteRating":
                    if (ratingID != null) {
                        ratingDAO.deleteRating(Integer.parseInt(ratingID), userID);
                    }
                    break;
                case "deleteAsEmployee":
                    if (ratingID != null) {
                        ratingDAO.deleteAllRating(Integer.parseInt(ratingID));
                    }
                    break;
                case "list":
                default:
                    // Default case for listing all ratings for the product
                    ArrayList<Rating> listRating = ratingDAO.viewAllRating(rating.getProductID());
                    request.setAttribute("ratingList", listRating);
                    break;
            }

            response.sendRedirect("productDetails.jsp?productID=" + productID); // Redirect after submission
        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Rating Controller for handling product rating actions";
    }
}
