package Controllers;

import DAOs.ProductDAO;
import Models.Product;
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
        String path = request.getQueryString();
        System.out.println("doGet: " + path);
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
                    System.out.println("doGet: " + action);
                    break;
                case "searchByName":
                    String searchName = request.getParameter("searchName");
                    if (searchName != null) {
                        productList = productDAO.searchProductsByName(searchName);
                    }
                    System.out.println("doGet: " + action);
                    break;
                case "searchByCategory":
                    String categoryIdStr = request.getParameter("categoryId");
                    if (categoryIdStr != null) {
                        try {
                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchProductsByCategory(categoryId);
                        } catch (NumberFormatException e) {
                            request.setAttribute("errorMessage", "Invalid category ID.");
                            productList = productDAO.viewProductList();
                        }
                    }
                    System.out.println("doGet: " + action);
                    break;
                case "deleted":
                    productList = productDAO.viewDeletedProductList();
                    System.out.println("doGet: " + action);
                    break;
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
                default:
                    productList = productDAO.viewProductList();
                    System.out.println("doGet: " + action);
                    break;
            }
            request.setAttribute("productList", productList);
            if ("listControl".equals(action)) {
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "listControl";
        }
        ProductDAO productDAO = new ProductDAO();
        Part filePart = request.getPart("image");
        String originalFileName = getFileName(filePart);
        switch (action) {
            case "add":
                System.out.println("doPost: add");
                String newFileName = "";
                if (filePart != null && filePart.getSize() > 0) {
                    String contentType = filePart.getContentType();
                    if (!isValidImageType(contentType)) {
                        System.out.println("Product image of the wrong type.");
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
                    try ( OutputStream out = new FileOutputStream(fullUploadPath)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = imageStream.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }
                    } catch (IOException e) {
                        System.err.println("Error saving image: " + e.getMessage());
                    }
                    System.out.println("Product image saved at: " + fullUploadPath);
                } else {
                    newFileName = "default.jpg";
                    System.out.println("Product image not uploaded. Using deafult.jpg");
                }
                String imageUrl = "img/pro/" + newFileName;
                System.out.println("Product image saved at: " + imageUrl);
                String productName = request.getParameter("productName");
                String description = request.getParameter("description");
                float price = Float.parseFloat(request.getParameter("price"));
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                Product newProduct = new Product(0, productName, description, price, imageUrl,
                        categoryId, stockQuantity, null, null);
                productDAO.addProduct(newProduct);
                response.sendRedirect("ProductController?action=list&page=Product");
                break;
            case "update":
                System.out.println("doPost: update");
                int productId = Integer.parseInt(request.getParameter("productId"));
                String updatedProductName = request.getParameter("productName");
                String updatedDescription = request.getParameter("description");
                float updatedPrice = Float.parseFloat(request.getParameter("price"));
                int updatedCategoryId = Integer.parseInt(request.getParameter("categoryId"));
                int updatedStockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
                String updatedImageUrl;
                String fileName = getFileName(filePart);
                if (fileName == null || fileName.isEmpty()) {
                    System.out.println("Product image not uploaded");
                    Product existingProduct = productDAO.readProduct(productId);
                    if (existingProduct != null) {
                        updatedImageUrl = existingProduct.getImageURL();
                        System.out.println("Product image not changed");
                    } else {
                        System.err.println("Error: Product with ID " + productId + " not found in the database.");
                        updatedImageUrl = "img/pro/default.jpg";
                    }
                } else {
                    System.out.println("Product image uploaded");
                    fileName = new File(fileName).getName();
                    updatedImageUrl = "img/pro/" + fileName;
                    System.out.println("Product image saved at: " + updatedImageUrl);
                    if (filePart != null && filePart.getSize() > 0) {
                        String fullImagePath = getServletContext().getRealPath("/") + updatedImageUrl;
                        try ( OutputStream out = new FileOutputStream(fullImagePath)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            try ( InputStream imageStream = filePart.getInputStream()) {
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
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Product controller thingy";
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
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

    private boolean isValidImageType(String contentType) {
        return contentType != null
                && (contentType.equals("image/jpeg")
                || contentType.equals("image/png"));
    }
}

