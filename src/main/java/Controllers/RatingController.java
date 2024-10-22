/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RatingDAO;
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
@WebServlet(name = "RatingController", urlPatterns = {"/RatingController"})
public class RatingController extends HttpServlet {

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
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
        Models.User user = (Models.User) request.getSession().getAttribute("user");
        int userID = user.getId();
        String ratingID = request.getParameter("ratingID");
        String productID = request.getParameter("productID");
        String ratingValue = request.getParameter("ratingValue");
        String comment = request.getParameter("comment");
        String createdAt = request.getParameter("createdAt");

        RatingDAO ratingDAO = new RatingDAO();
        Rating rating = new Rating(Integer.parseInt(ratingID), userID, Integer.parseInt(productID), Integer.parseInt(ratingValue), comment, Date.valueOf(createdAt));
        ArrayList<Rating> listRating;
        try {
            listRating = ratingDAO.viewAllRating(rating.getProductID());
        } catch (SQLException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "list":
                    listRating = ratingDAO.viewAllRating(rating.getProductID());
                    request.setAttribute("ratingList", listRating);
                    break;
                case "add":
                    ratingDAO.addRating(rating);
                    break;
                case "updateRating":
                    ratingDAO.updateRating(rating);
                    break;
                case "deleteRating":
                    ratingDAO.deleteRating(Integer.parseInt(ratingID), userID);
                    break;
                case "deleteAsEmployee":
                    ratingDAO.deleteAllRating(Integer.parseInt(ratingID));
                    break;

                default:
                    listRating = ratingDAO.viewAllRating(rating.getProductID());
                    request.setAttribute("ratingList", listRating);
                    break;
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("rating.jsp");
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
