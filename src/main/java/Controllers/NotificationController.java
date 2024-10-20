/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAOs.NotificationDAO;
import Models.Notification;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class NotificationController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NotificationController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NotificationController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
            /*
            null: xem het
            viewAll: xem het (admin)
            viewByUser: xem het cua user
            markAsRead: da xem
            markAllAsRead: da xem het
            default: xem het (admin)
            */
            
            
            String action = request.getParameter("action");
            if (action == null) {
                action = "viewAll";
            }

            NotificationDAO notificationDAO = new NotificationDAO();
            ArrayList<Notification> notifications = null;

            switch (action) {
                case "viewAll":
                    notifications = notificationDAO.viewNotificationList();
                    break;
                case "viewByUser":
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    notifications = notificationDAO.getNotificationsByUserId(userId);
                    break;
                case "markAsRead":
                    int notificationId = Integer.parseInt(request.getParameter("notificationId"));
                    notificationDAO.markAsRead(notificationId);
                    // CHUYEN HUONG
                    response.sendRedirect(request.getContextPath() + "/notifications"); 
                    // NGUNG XU LI
                    return;
                case "markAllAsRead":
                    int userIdToMark = Integer.parseInt(request.getParameter("userId"));
                    notificationDAO.markAllAsRead(userIdToMark);
                    // CHUEYN HUONG
                    response.sendRedirect(request.getContextPath() + "/notifications");
                    // NGUNG XU LI
                    return;
                // THEM CASES O DAY
                default:
                    notifications = notificationDAO.viewNotificationList();
                    break;
            }

            request.setAttribute("notifications", notifications);
            RequestDispatcher dispatcher = request.getRequestDispatcher("adminNotification.jsp"); // Or your JSP page
            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
            // Handle the exception, e.g., display an error page
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            // Handle NumberFormatException (e.g., invalid notificationId or userId)
            request.setAttribute("errorMessage", "Invalid input: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
        processRequest(request, response);
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
