package Controllers;

import DAOs.CategoryDAO;
import DAOs.ProductDAO;
import Helper.ImageHelper;
import Models.Category;
import Models.Product;
import com.google.gson.Gson;
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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author Le Trung Hau - CE180481
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
@MultipartConfig
public class ProductController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }
            ProductDAO productDAO = new ProductDAO();
            ArrayList<Product> productList = null;

            CategoryDAO categoryDAO = new CategoryDAO();
            ArrayList<Category> categoryList = categoryDAO.viewCategory();
            request.setAttribute("categoryList", categoryList);

            switch (action) {
                case "list": {
                    productList = productDAO.viewProductList();
                    System.out.println("doGet: " + action);
                    break;
                }

                case "deleted": {
                    productList = productDAO.viewDeletedProductList();
                    System.out.println("doGet: " + action);
                    break;
                }

                case "delete": {
                    int productID = Integer.parseInt(request.getParameter("productId"));
                    productDAO.removeProduct(productID);
                    System.out.println("doGet: " + action);
                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                }

                case "restore": {
                    int productID = Integer.parseInt(request.getParameter("productId"));
                    boolean restored = productDAO.restoreProduct(productID);
                    if (restored) {
                        request.setAttribute("message", "Product restored successfully.");
                    } else {
                        request.setAttribute("errorMessage", "Failed to restore product.");
                    }
                    System.out.println("doGet: " + action);
                    response.sendRedirect("ProductController?action=deleted&page=Product");
                    return;
                }

                case "deleteFinal": {
                    int productID = Integer.parseInt(request.getParameter("productId"));
                    productDAO.removeProductFinal(productID);
                    System.out.println("doGet: " + action);
                    response.sendRedirect("ProductController?action=deleted&page=Product");
                    return;
                }

                case "listControl": {
                    productList = productDAO.viewProductListControl();
                    System.out.println("doGet: " + action);
                    break;
                }

                case "getCategories": {
                    System.out.println("doGet: " + action);
                    Gson gson = new Gson();
                    String categoryListJson = gson.toJson(categoryList);
                    response.setContentType("application/json");
                    response.getWriter().write(categoryListJson);

                    return; //return de bo qua khuc sau, dung co sua no lai
                }

                case "search": {
                    System.out.println("doGet: " + action);
                    String searchName = request.getParameter("searchName");
                    String categoryIdStr = request.getParameter("categoryId");

                    try {
                        if (categoryIdStr != null && !categoryIdStr.isEmpty() && searchName != null && !searchName.isEmpty()) {
                            //search by both
                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchProductsByNameAndCategory(searchName, categoryId);
                        } else if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                            //search by category only
                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchProductsByCategory(categoryId);
                        } else if (searchName != null && !searchName.isEmpty()) {
                            //search by name only
                            productList = productDAO.searchProductsByName(searchName);
                        } else {
                            //default
                            productList = productDAO.viewProductList();
                        }

                        if (productList.isEmpty()) {
                            request.setAttribute("noResultsMessage", "No products found matching your criteria.");
                            productList = productDAO.viewProductList();
                        }
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid category ID.");
                        productList = productDAO.viewProductList();// error = show all
                    } catch (SQLException e) {
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, "SQL Exception during search", e);
                        //request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                        //RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        //dispatcher.forward(request, response);
                        //return; //stop
                    }
                    break;
                }

                case "searchDeleted": {
                    System.out.println("doGet: " + action);
                    String searchName = request.getParameter("searchName");
                    String categoryIdStr = request.getParameter("categoryId");

                    try {

                        if (categoryIdStr != null && !categoryIdStr.isEmpty() && searchName != null && !searchName.isEmpty()) {

                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchDeletedProductsByNameAndCategory(searchName, categoryId);

                        } else if (categoryIdStr != null && !categoryIdStr.isEmpty()) {

                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchDeletedProductsByCategory(categoryId);
                        } else if (searchName != null && !searchName.isEmpty()) {

                            productList = productDAO.searchDeletedProductsByName(searchName);
                        } else {

                            productList = productDAO.viewDeletedProductList();
                        }

                        if (productList.isEmpty()) {
                            request.setAttribute("noResultsMessage", "No deleted products found matching your criteria.");
                            productList = productDAO.viewDeletedProductList();
                        }
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid category ID.");

                        return;
                    } catch (SQLException e) {
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, "SQL Exception during deleted search", e);
                        //request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                        //RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        //dispatcher.forward(request, response);
                        //return;
                    }

                    request.setAttribute("productList", productList);
                    break;
                }

                default: {
                    productList = productDAO.viewProductList();
                    System.out.println("doGet: " + action);
                    break;
                }
            }
            request.setAttribute("productList", productList);
            System.out.println("Navigation");
            switch (action) {
                case "listControl": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("adminControl.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                case "deleted":
                case "searchDeleted": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("restoreProduct.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                default: {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("productManagement.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "listControl" : action;
        ProductDAO productDAO = new ProductDAO();
        
        System.out.println("doPost: " + action);
        switch (action) {
            case "add": {
                
                String imageUrl = ImageHelper.saveImage(request.getPart("image"), "pro", getServletContext().getRealPath("/"));
                if (imageUrl != null) {
                    
                    String productName = request.getParameter("productName");
                    String description = request.getParameter("description");
                    float price = Float.parseFloat(request.getParameter("price"));
                    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                    int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                    
                    Product newProduct = new Product(0, productName, description, price, imageUrl, categoryId, stockQuantity, null, null);
                    productDAO.addProduct(newProduct);
                    
                    response.sendRedirect("ProductController?action=list&page=Product");
                } else {
                    request.setAttribute("errorMessage", "Image upload failed.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("productManagement.jsp");
                    dispatcher.forward(request, response);
                }
                return;
            }
            case "update": {
                
                int productId = Integer.parseInt(request.getParameter("productId"));
                String updatedProductName = request.getParameter("productName");
                String updatedDescription = request.getParameter("description");
                float updatedPrice = Float.parseFloat(request.getParameter("price"));
                int updatedCategoryId = Integer.parseInt(request.getParameter("categoryId"));
                int updatedStockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                
                String updatedImageUrl = ImageHelper.saveImage(request.getPart("image"), "pro", getServletContext().getRealPath("/"));
                
                if (updatedImageUrl == null && request.getPart("image").getSize() > 0) {
                    request.setAttribute("errorMessage", "Image upload failed.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("productManagement.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
                if (updatedImageUrl == null) {
                    
                    Product existingProduct = productDAO.readProduct(productId);
                    String path = "img" + File.separator + "pro" + File.separator + "default.jpg";
                    updatedImageUrl = (existingProduct != null) ? existingProduct.getImageURL() : path;
                }
                
                Product updatedProduct = new Product(productId, updatedProductName, updatedDescription, updatedPrice, updatedImageUrl, updatedCategoryId, updatedStockQuantity, null, null);
                productDAO.updateProduct(updatedProduct);
                response.sendRedirect("ProductController?action=list&page=Product");
                return;
            }
            case "bulkAction": {

                String[] selectedProducts = request.getParameterValues("selectedProducts");
                String bulkAction = request.getParameter("bulkRestore") != null ? "restore" : (request.getParameter("bulkDelete") != null ? "deleteFinal" : null);
                if (selectedProducts != null && bulkAction != null) {

                    System.out.println("doPost: " + bulkAction);

                    for (String productIdStr : selectedProducts) {
                        try {
                            int productId = Integer.parseInt(productIdStr);
                            if ("restore".equals(bulkAction)) {
                                productDAO.restoreProduct(productId);
                            } else {
                                productDAO.removeProductFinal(productId);

                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing product ID: " + e.getMessage());

                        }
                    }
                }
                response.sendRedirect("ProductController?action=deleted&page=Product");
                return;
            }
            default: {
                System.out.println("doPost: " + action);
                response.sendRedirect("ProductController?action=deleted&page=Product");
            }

        }

    }

    @Override
    public String getServletInfo() {
        return "Product controller thingy";
    }
}
