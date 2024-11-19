/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CategoryDAO;
import Models.Category;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CategoryController/*")
public class CategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployeeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI(),
                contextPath = request.getContextPath();
        System.out.println("Requested Path: " + path);
        System.out.println("Context Path: " + contextPath);

        if (path.startsWith("/CategoryController")) {
            if (path.contains("delete")) {
                System.out.println("delete");
                doPost(request, response);
            } else if (path.contains("search")) {
                String parentCategoryIdStr = request.getParameter("parentCategoryId");
                if (parentCategoryIdStr != null) {
                    try {
                        int parentCategoryId = Integer.parseInt(parentCategoryIdStr);
                        CategoryDAO dao = new CategoryDAO();
                        ArrayList<Category> categoryList = dao.searchByParentCategoryId(parentCategoryId);
                        request.setAttribute("categoryList", categoryList);
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid Parent Category ID.");
                    }
                } else {
                    request.setAttribute("errorMessage", "Parent Category ID is missing.");
                }
            } else {
                // System.out.println("list");
                ArrayList<Category> categoryList = new DAOs.CategoryDAO().getCategoryWithParentName();
                request.setAttribute("categoryList", categoryList);
            }
            RequestDispatcher ds = request.getRequestDispatcher("categoryManagement.jsp");
            ds.forward(request, response);
            return;

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
        HttpSession session = request.getSession();
        String path = request.getRequestURI(),
                contextPath = request.getContextPath();
        System.out.println("Requested Path: " + path);
        System.out.println("Context Path: " + contextPath);
        String categoryID = request.getParameter("categoryID"),
                categoryName = request.getParameter("categoryName"),
                parentCategoryID = request.getParameter("parentCategoryID");
        System.out.println(categoryID);
        System.out.println(categoryName);
        System.out.println(parentCategoryID);
        if (path.startsWith("/CategoryController")) {
            DAOs.CategoryDAO DAO = new DAOs.CategoryDAO();
            if (path.contains("add")) {
                // Khi thêm mới:
                Integer parentId; // Giá trị mặc định là 0
                if (parentCategoryID != null && !parentCategoryID.isEmpty()) {
                    try {
                        parentId = Integer.parseInt(parentCategoryID);
                    } catch (NumberFormatException e) {
                        // Xử lý lỗi nếu parentCategoryID không phải là số
                        session.setAttribute("errorMessage", "Parent Category ID Wrong.");
                        RequestDispatcher ds = request.getRequestDispatcher("categoryManagement.jsp");
                        ds.forward(request, response);
                        return;
                    }
                } else {
                    parentId = null;
                }
                Category category = new Category(categoryName, parentId);
                String result = DAO.addOrUpdateCategory(category); // Call the new method

                if (result != null) { // There was an error
                    session.setAttribute("errorMessage", result);
                }
            } else if (path.contains("edit")) {
                Category category;

                if (categoryID != null && !categoryID.isEmpty() && !categoryID.equals(parentCategoryID)) {
                    // Nếu có categoryID, tức là đang edit
                    category = new Category(Integer.parseInt(categoryID), categoryName,
                            parentCategoryID != null && !parentCategoryID.isEmpty() ? Integer.parseInt(parentCategoryID) : 0);

                    DAO.updateCategory(category);
                } else if (categoryID == null && categoryID.isEmpty()) {
                    // Nếu không có categoryID, tức là đang thêm mới
                    category = new Category(0, categoryName,
                            parentCategoryID != null && !parentCategoryID.isEmpty() ? Integer.parseInt(parentCategoryID) : 0);

                    DAO.updateCategory(category);
                } else if (categoryID.equals(parentCategoryID)) {

                    session.setAttribute("errorMessage", "CategoryID do not equal ParentCategoryID. Please try again.");
                    response.sendRedirect(request.getContextPath() + "/CategoryController");
                    return;
                }
            } else if (path.contains("delete")) {
                try {
                    Category category = new Category(Integer.parseInt(categoryID), categoryName,
                            parentCategoryID != null && !parentCategoryID.isEmpty() ? Integer.parseInt(parentCategoryID) : 0);
                    if (!DAO.removeCategory(category.getCategoryId())) {
                        session.setAttribute("errorMessage", "Cannot delete Category because it is the parent of another Category");
                    } else {
                        session.setAttribute("errorMessage", "Remove success");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
                    session.setAttribute("errorMessage", "Database error during delete."); // Use session attribute
                    response.sendRedirect(request.getContextPath() + "/CategoryController"); // Redirect after setting the error
                    return; // Important: Return to prevent further processing.
                }
            }
            response.sendRedirect(request.getContextPath() + "/CategoryController");
            return;
        }
    }
}
