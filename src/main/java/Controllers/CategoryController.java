/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAOs.CategoryDAO;
import Models.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tran Le Gia Huy - CE180068
 */
@WebServlet(name="CategoryController", urlPatterns={"/categoryController"})
public class CategoryController extends HttpServlet {
   


     private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        try {
            categoryDAO = new CategoryDAO();
        } catch (SQLException e) {
            throw new ServletException("error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showForm(request, response);
                    break;
                case "delete":
                    deleteCategory(request, response);
                    break;
                case "list":
                    listCategories(request, response);
                    break;
                default:
                    listCategories(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "create":
                    createCategory(request, response);
                    break;
                case "update":
                    updateCategory(request, response);
                    break;
                default:
                    listCategories(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
         ArrayList<Category> categoryList = categoryDAO.getAllCategories();
        request.setAttribute("listCategory", categoryList);
        request.getRequestDispatcher("listCategory.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("categoryForm.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category existingCategory = categoryDAO.readCategory(id);
        request.setAttribute("category", existingCategory);
        request.getRequestDispatcher("categoryForm.jsp").forward(request, response);
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        int parentId = Integer.parseInt(request.getParameter("parentId"));

        Category newCategory = new Category(0, name, parentId);
        categoryDAO.addCategory(newCategory);
        response.sendRedirect("category?action=list");
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int parentId = Integer.parseInt(request.getParameter("parentId"));

        Category category = new Category(id, name, parentId);
        categoryDAO.updateCategory(category);
        response.sendRedirect("category?action=list");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.removeCategory(id);
        response.sendRedirect("category?action=list");
    }
}