package Controllers;

import DAOs.ProductDAO;
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
import java.io.File;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
        try (PrintWriter out = response.getWriter()) {
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
        String path = request.getRequestURI();
        String page = "";
        if (path.contains("Control")) {
            page = "Control";
        } else if (path.contains("Product")) {
            page = "Product";
        }
        try {
            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }
            ProductDAO productDAO = new ProductDAO();
            ArrayList<Product> productList = null;
            switch (action) {
                case "list":
                    productList = productDAO.viewProductList();
                    if ("Product".equals(request.getParameter("page"))) {
                        request.setAttribute("productList", productList);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("productManagement.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        Gson gson = new Gson();
                        String jsonResponse = gson.toJson(productList);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(jsonResponse);
                        return;
                    }
                    break;
                case "searchByName":
                    String searchName = request.getParameter("searchName");
                    if (searchName != null) {
                        productList = productDAO.searchProductsByName(searchName);
                    }
                    break;
                case "searchByCategory":
                    String categoryIdStr = request.getParameter("categoryId");
                    if (categoryIdStr != null) {
                        try {
                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchProductsByCategory(categoryId);
                        } catch (NumberFormatException e) {
                            request.setAttribute("errorMessage", "Invalid category ID.");
                        }
                    }
                    break;
                case "deleted":
                    productList = productDAO.viewDeletedProductList();
                    break;
                case "delete": {
                    int productID = Integer.parseInt(request.getParameter("productId"));
                    productDAO.removeProduct(productID);
                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                }
                case "listControl": {
                    productList = productDAO.viewProductListControl();
                    break;
                }
                default:
                    productList = productDAO.viewProductList();
                    break;
            }
            request.setAttribute("productList", productList);
            if (page.equals("Control")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("adminControl.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("productManagement.jsp");
                dispatcher.forward(request, response);
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
        String path = request.getRequestURI();
        String page = "";

        if (path.contains("Control")) {
            page = "Control";
        } else if (path.contains("Product")) {
            page = "Product";
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "listControl";
        }

        try {
            ProductDAO productDAO = new ProductDAO();

            Part filePart = request.getPart("image");
            String originalFileName = getFileName(filePart);

            switch (action) {
                case "add": {
                    String productName = request.getParameter("productName");
                    String description = request.getParameter("description");

                    if (productName == null || productName.trim().isEmpty()) {
                        request.setAttribute("errorMessage", "Product name is required.");
                        redirectWithError(request, response, page);
                        return;
                    }

                    float price;
                    try {
                        price = Float.parseFloat(request.getParameter("price"));
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid price format.");
                        redirectWithError(request, response, page);
                        return;
                    }

                    int categoryId;
                    try {
                        categoryId = Integer.parseInt(request.getParameter("categoryId"));
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid category ID format.");
                        redirectWithError(request, response, page);
                        return;
                    }

                    int stockQuantity;
                    try {
                        stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid stock quantity format.");
                        redirectWithError(request, response, page);
                        return;
                    }

                    String newFileName = "";
                    if (filePart != null && filePart.getSize() > 0) {
                        String contentType = filePart.getContentType();
                        if (!isValidImageType(contentType)) {
                            request.setAttribute("errorMessage", "Invalid image type. Please upload a JPG, JPEG, or PNG file.");
                            redirectWithError(request, response, page);
                            return;
                        }

                        String fileExtension = getFileExtension(originalFileName);
                        newFileName = "product_" + System.currentTimeMillis() + fileExtension;

                        InputStream imageStream = filePart.getInputStream();
                        String relativeUploadPath = "/img/pro/" + newFileName;
                        String fullUploadPath = getServletContext().getRealPath(relativeUploadPath);

                        File targetFile = new File(fullUploadPath);
                        File parentDir = targetFile.getParentFile();
                        if (!parentDir.exists()) {
                            parentDir.mkdirs();
                        }

                        try (OutputStream out = new FileOutputStream(fullUploadPath)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = imageStream.read(buffer)) > 0) {
                                out.write(buffer, 0, length);
                            }
                        } catch (IOException e) {
                            System.err.println("Error saving image: " + e.getMessage());
                            e.printStackTrace();
                            request.setAttribute("errorMessage", "Error saving image.");
                            redirectWithError(request, response, page);
                            return;
                        }
                    } else {

                        newFileName = "default.jpg";
                    }

                    String imageUrl = "img/pro/" + newFileName;
                    Product newProduct = new Product(0, productName, description, price, imageUrl,
                            categoryId, stockQuantity, null, null);
                    productDAO.addProduct(newProduct);

                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                }

                case "update": {
                    int productId;
                    try {
                        productId = Integer.parseInt(request.getParameter("productId"));
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid Product ID");
                        request.getRequestDispatcher("productManagement.jsp").forward(request, response);
                        return;
                    }
                    String updatedProductName = request.getParameter("productName");
                    String updatedDescription = request.getParameter("description");
                    float updatedPrice;
                    try {
                        updatedPrice = Float.parseFloat(request.getParameter("price"));
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid price format.");
                        request.getRequestDispatcher("productManagement.jsp").forward(request, response);
                        return;
                    }
                    int updatedCategoryId;
                    try {
                        updatedCategoryId = Integer.parseInt(request.getParameter("categoryId"));
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid category ID format.");
                        request.getRequestDispatcher("productManagement.jsp").forward(request, response);
                        return;
                    }
                    int updatedStockQuantity;
                    try {
                        updatedStockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Invalid stock quantity format.");
                        request.getRequestDispatcher("productManagement.jsp").forward(request, response);
                        return;
                    }
                    String updatedImageUrl;
                    String fileName = getFileName(filePart);
                    if (fileName == null || fileName.isEmpty()) {
                        Product existingProduct = productDAO.readProduct(productId);
                        if (existingProduct != null) {
                            updatedImageUrl = existingProduct.getImageURL();
                        } else {
                            System.err.println("Error: Product with ID " + productId + " not found in the database.");
                            updatedImageUrl = "img/pro/default.jpg";
                            response.sendRedirect("editProductForm.jsp?error=productNotFound&productId=" + productId);
                            return;
                        }
                    } else {
                        fileName = new File(fileName).getName();
                        updatedImageUrl = "img/pro/" + fileName;
                        if (filePart != null && filePart.getSize() > 0) {
                            String fullImagePath = getServletContext().getRealPath("/") + updatedImageUrl;
                            try (OutputStream out = new FileOutputStream(fullImagePath)) {
                                byte[] buffer = new byte[1024];
                                int length;
                                try (InputStream imageStream = filePart.getInputStream()) {
                                    while ((length = imageStream.read(buffer)) > 0) {
                                        out.write(buffer, 0, length);
                                    }
                                }
                            }
                        }
                    }
                    Product updatedProduct = new Product(productId, updatedProductName, updatedDescription,
                            updatedPrice, updatedImageUrl, updatedCategoryId,
                            updatedStockQuantity, null, null);
                    productDAO.updateProduct(updatedProduct);
                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Product controller thingy";
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return ""; // Or handle null cases as needed
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }

    private boolean isValidImageType(String contentType) {
        // Add more image types as needed
        return contentType != null
                && (contentType.equals("image/jpeg")
                || contentType.equals("image/png"));
    }

    private void redirectWithError(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        if (page.equals("Control")) {
            request.getRequestDispatcher("adminControl.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("productManagement.jsp").forward(request, response);
        }
    }
    
    private String getFileName(Part part) {
        if (part != null && part.getHeader("content-disposition") != null) {
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
        }
        return null;
    }
}