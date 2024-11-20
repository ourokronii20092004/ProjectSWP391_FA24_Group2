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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                case "list":
                    productList = productDAO.viewProductList();
                    break;
                case "deleted":
                    productList = productDAO.viewDeletedProductList();
                    break;
                case "restore":
                    int productIDRestore = Integer.parseInt(request.getParameter("productId"));
                    productDAO.restoreProduct(productIDRestore);
                    response.sendRedirect("ProductController?action=deleted&page=Product");
                    return;
                case "deleteFinal":
                    int productIDDeleteFinal = Integer.parseInt(request.getParameter("productId"));
                    productDAO.removeProductFinal(productIDDeleteFinal);
                    response.sendRedirect("ProductController?action=deleted&page=Product");
                    return;
                case "listControl":
                    productList = productDAO.viewProductListControl();
                    break;
                case "getCategories":
                    Gson gson = new Gson();
                    String categoryListJson = gson.toJson(categoryList);
                    response.setContentType("application/json");
                    response.getWriter().write(categoryListJson);
                    return;
                case "search":
                    String searchName = request.getParameter("searchName");
                    String categoryIdStr = request.getParameter("categoryId");
                    try {
                        if (((categoryIdStr == null || categoryIdStr.isEmpty()) || searchName == null) || searchName.isEmpty()) {
                            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                                int categoryId = Integer.parseInt(categoryIdStr);
                                productList = productDAO.searchProductsByCategory(categoryId);
                            } else if (searchName != null && !searchName.isEmpty()) {
                                productList = productDAO.searchProductsByName(searchName);
                            } else {
                                productList = productDAO.viewProductList();
                            }
                        } else {
                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchProductsByNameAndCategory(searchName, categoryId);
                        }
                        if (productList.isEmpty()) {
                            request.setAttribute("noResultsMessage", "No products found matching your criteria.");
                            productList = productDAO.viewProductList();
                        }
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid category ID.");
                        productList = productDAO.viewProductList();
                    } catch (SQLException e) {
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, "SQL Exception during search", e);
                    }
                    break;
                case "searchDeleted":
                    String searchDeletedName = request.getParameter("searchName");
                    String categoryDeletedIdStr = request.getParameter("categoryId");
                    try {
                        if (categoryDeletedIdStr != null && !categoryDeletedIdStr.isEmpty() && searchDeletedName != null && !searchDeletedName.isEmpty()) {
                            int categoryId = Integer.parseInt(categoryDeletedIdStr);
                            productList = productDAO.searchDeletedProductsByNameAndCategory(searchDeletedName, categoryId);
                        } else if (categoryDeletedIdStr != null && !categoryDeletedIdStr.isEmpty()) {
                            int categoryId = Integer.parseInt(categoryDeletedIdStr);
                            productList = productDAO.searchDeletedProductsByCategory(categoryId);
                        } else if (searchDeletedName != null && !searchDeletedName.isEmpty()) {
                            productList = productDAO.searchDeletedProductsByName(searchDeletedName);
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
                    }
                    request.setAttribute("productList", productList);
                    break;

                default:
                    productList = productDAO.viewProductList();
                    break;
            }
            request.setAttribute("productList", productList);

            switch (action) {
                case "listControl":
                    RequestDispatcher dispatcherControl = request.getRequestDispatcher("adminControl.jsp");
                    dispatcherControl.forward(request, response);
                    break;
                case "deleted":
                case "searchDeleted":
                    RequestDispatcher dispatcherDeleted = request.getRequestDispatcher("restoreProduct.jsp");
                    dispatcherDeleted.forward(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("productManagement.jsp");
                    dispatcher.forward(request, response);
                    break;
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
        switch (action) {
            case "add":
                if (validateProductAdd(request)) {
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
                        return;
                    } else {
                        request.getSession().setAttribute("errorMessage", "Image upload failed.");
                    }
                }
                response.sendRedirect("ProductController");
                return;
            case "update":
                if (validateProductEdit(request)) {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    String updatedProductName = request.getParameter("productName");
                    String updatedDescription = request.getParameter("description");
                    float updatedPrice = Float.parseFloat(request.getParameter("price"));
                    int updatedCategoryId = Integer.parseInt(request.getParameter("categoryId"));
                    int updatedStockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                    Part imagePart = request.getPart("image");
                    String updatedImageUrl;

                    if (imagePart != null && imagePart.getSize() > 0) {
                        updatedImageUrl = ImageHelper.saveImage(imagePart, "pro", getServletContext().getRealPath("/"));
                        if (updatedImageUrl == null) {
                            request.getSession().setAttribute("errorMessage", "Image upload failed.");
                            response.sendRedirect("ProductController");
                            return;
                        }
                    } else {
                        String path = "img" + File.separator + "pro" + File.separator + "default.jpg";
                        Product existingProduct = productDAO.readProduct(productId);
                        updatedImageUrl = (existingProduct != null) ? existingProduct.getImageURL() : path;
                    }
                    Product updatedProduct = new Product(productId, updatedProductName, updatedDescription, updatedPrice, updatedImageUrl, updatedCategoryId, updatedStockQuantity, null, null);
                    productDAO.updateProduct(updatedProduct);
                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                }
                response.sendRedirect("ProductController");
                return;
            case "bulkAction":
                String[] selectedProducts = request.getParameterValues("selectedProducts");
                String bulkAction = request.getParameter("bulkRestore") != null ? "restore" : (request.getParameter("bulkDelete") != null ? "deleteFinal" : null);
                if (selectedProducts != null && bulkAction != null) {
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
            case "delete":
                int productID = Integer.parseInt(request.getParameter("productId"));
                if (!productDAO.removeProduct(productID)) {
                    request.getSession().setAttribute("errorMessage", "Product delete failed.");
                    response.sendRedirect("ProductController");
                    return;
                }
                response.sendRedirect("ProductController?action=list&page=Product");
                return;
            default:
                response.sendRedirect("ProductController?action=list&page=Product");
        }
    }

    private boolean validateProductAdd(HttpServletRequest request) {
        String productName = request.getParameter("productName");
        String priceStr = request.getParameter("price");
        String categoryIdStr = request.getParameter("categoryId");
        String stockQuantityStr = request.getParameter("stockQuantity");
        Part imagePart = null;
        try {
            imagePart = request.getPart("image");
        } catch (IOException | ServletException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!isValidProductName(productName)) {
            request.getSession().setAttribute("errorMessage", "Invalid product name. At least 1 letter & 80% valid characters.");
            return false;
        }
        if (!isValidPrice(priceStr)) {
            request.getSession().setAttribute("errorMessage", "Price must be between 0 and 10,000,000.");
            return false;
        }
        if (!isValidStockQuantity(stockQuantityStr)) {
            request.getSession().setAttribute("errorMessage", "Stock quantity must be between 0 and 10,000,000.");
            return false;
        }
        if (!isValidCategoryId(categoryIdStr)) {
            request.getSession().setAttribute("errorMessage", "Please select a category.");
            return false;
        }
        if (imagePart == null || imagePart.getSize() <= 0) {
            request.getSession().setAttribute("errorMessage", "Please select an image.");
            return false;
        } else if (!isValidImageType(imagePart)) {
            request.getSession().setAttribute("errorMessage", "Invalid image type. Upload JPG, JPEG, or PNG.");
            return false;
        }
        return true;
    }

    private boolean validateProductEdit(HttpServletRequest request) {
        String productName = request.getParameter("productName");
        String priceStr = request.getParameter("price");
        String categoryIdStr = request.getParameter("categoryId");
        String stockQuantityStr = request.getParameter("stockQuantity");
        Part imagePart = null;
        try {
            imagePart = request.getPart("image");
        } catch (IOException | ServletException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!isValidProductName(productName)) {
            request.getSession().setAttribute("errorMessage", "Invalid product name. At least 1 letter & 80% valid characters.");
            return false;
        }

        if (!isValidPrice(priceStr)) {
            request.getSession().setAttribute("errorMessage", "Price must be between 0 and 10,000,000.");
            return false;
        }

        if (!isValidStockQuantity(stockQuantityStr)) {
            request.getSession().setAttribute("errorMessage", "Stock quantity must be between 0 and 10,000,000.");
            return false;
        }

        if (!isValidCategoryId(categoryIdStr)) {
            request.getSession().setAttribute("errorMessage", "Please select a category.");
            return false;
        }

        if (imagePart != null && imagePart.getSize() > 0 && !isValidImageType(imagePart)) {
            request.getSession().setAttribute("errorMessage", "Invalid image type. Upload JPG, JPEG, or PNG.");
            return false;
        }
        return true;
    }

    private boolean isValidProductName(String productName) {
        if (productName == null) {
            return false;
        }
        if (!productName.matches(".*[a-zA-Z].*")) {
            return false;
        }

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(productName);
        int specialCharCount = 0;
        while (m.find()) {
            specialCharCount++;
        }
        return (double) specialCharCount / productName.length() <= 0.2;
    }

    private boolean isValidPrice(String priceStr) {
        try {
            float price = Float.parseFloat(priceStr);
            return price >= 0 && price <= 10000000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidStockQuantity(String stockQuantityStr) {
        try {
            int stockQuantity = Integer.parseInt(stockQuantityStr);
            return stockQuantity >= 0 && stockQuantity <= 10000000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidImageType(Part imagePart) {
        String contentType = imagePart.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jpg"));
    }

    private boolean isValidCategoryId(String categoryIdStr) {
        try {
            int categoryId = Integer.parseInt(categoryIdStr);
            ProductDAO productDAO = new ProductDAO();
            return productDAO.isValidCategoryId(categoryId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getServletInfo() {
        return "Product controller thingy";
    }
}
