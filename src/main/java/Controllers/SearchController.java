/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAOs.ProductDAO;
import Models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/search")
public class SearchController extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");
        if (keyword != null) {
            try {
                ArrayList<Product> searchResult = productDAO.searchProduct(keyword);
                request.setAttribute("searchResult", searchResult);
                request.getRequestDispatcher("searchResult.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("error", e);
            }
        } else {            
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}