/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author Le Trung Hau - CE180481
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})

public class ProductController extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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
            /*
            list: gom het tru may cai da xoa
            null: gom het tru may cai da xoa
            default: gom het tru may cai da xoa
            searchByName: search gan giong ten
            searchByCategory: search id category
            deleted: gom het may cai da xoa
            */
            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }

            ProductDAO productDAO = new ProductDAO();
            ArrayList<Product> productList = null;

            switch (action) {
                case "list":
                    productList = productDAO.viewProductList();
                    break;
                case "searchByName":
                    String searchName = request.getParameter("searchName");
                    if (searchName != null) {
                        productList = productDAO.searchProductsByName(searchName);
                    }
                    break;
                case "searchByCategory":
                    // NAY SEARCH = ID, KHONG PHAI SEARCH TEN CATEGORY=))
                    String categoryIdStr = request.getParameter("categoryId");
                    if (categoryIdStr != null) {
                        try {
                            int categoryId = Integer.parseInt(categoryIdStr);
                            productList = productDAO.searchProductsByCategory(categoryId);
                        } catch (NumberFormatException e) {
                            // EXCEPTION
                            request.setAttribute("errorMessage", "Invalid category ID.");
                            //productList = productDAO.viewProductList();
                        }
                    }
                    break;
                case "deleted":
                    productList = productDAO.viewDeletedProductList();
                    break;
                case "delete": {
                    int productID = Integer.parseInt(request.getParameter("productId"));
                    productDAO.removeProduct(productID);
                    response.sendRedirect("ProductController?action=list");
                    return; //IMPORTANT, TESTING
                }
                // SAU NAY THEM CASES THI THEM O DAY
                default:
                    productList = productDAO.viewProductList();
                    break;
            }

            request.setAttribute("productList", productList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("adminControl.jsp");

            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            // EXCEPTION
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp"); //LAM THEM TRANG ERROR, CHUA CO 404 THI PHAI
            dispatcher.forward(request, response);
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
        //processRequest(request, response);

        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }
        
        try {
            ProductDAO productDAO = new ProductDAO();

            switch (action) {
                case "add": {
                    String productName = request.getParameter("productName");
                    String description = request.getParameter("description");
                    
                    // INPUT INVALID TEST
                    if (productName == null || productName.trim().isEmpty()) {
                        // HANDLE ERROR HERE
                        request.setAttribute("errorMessage", "Product name is required.");
                        request.getRequestDispatcher("adminControl.jsp").forward(request, response);
                        return;//
                    } 

                    float price = Float.parseFloat(request.getParameter("price"));
                    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                    int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

                    //IMG UPLOAD HANDLER
                    Part filePart = request.getPart("image");
                    String fileName = getFileName(filePart);
                    if (fileName == null || fileName.isEmpty()) {
                        fileName = "default.jpg"; //defaulr
                    } else {
                        fileName = new File(fileName).getName();
                    }
                    String imageUrl = "img/" + fileName;

                    //
                    Product newProduct = new Product(0, productName, description, price, imageUrl, categoryId, stockQuantity, null, null);
                    productDAO.addProduct(newProduct);
                    
                    //save
                    if (filePart != null && filePart.getSize() > 0) {
                        String uploadPath = getServletContext().getRealPath("/img") + File.separator + fileName;
                        filePart.write(uploadPath);
                    }
                    break;
                }
                case "update": {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    String updatedProductName = request.getParameter("productName");
                    String updatedDescription = request.getParameter("description");
                    float updatedPrice = Float.parseFloat(request.getParameter("price"));
                    int updatedCategoryId = Integer.parseInt(request.getParameter("categoryId"));
                    int updatedStockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

                    //IMG UPLOAD HANDLER
                    Part updateFilePart = request.getPart("image");
                    String updatedFileName = getFileName(updateFilePart);

                    if (updatedFileName == null || updatedFileName.isEmpty()) {
                        //DANG FIX
                        //updatedFileName = productDAO.readProduct(productId).getImageURL();
                    } else {
                        updatedFileName = "img/" + new File(updatedFileName).getName();
                    }

                    Product updatedProduct = new Product(productId, updatedProductName, updatedDescription, updatedPrice, updatedFileName, updatedCategoryId, updatedStockQuantity, null, null);
                    productDAO.updateProduct(updatedProduct);

                    if (updateFilePart != null && updateFilePart.getSize() > 0) {
                        String uploadPath = getServletContext().getRealPath("/img") + File.separator + new File(updatedFileName).getName();
                        updateFilePart.write(uploadPath);
                    }
                    break;
                }
                
                //DELETE IS ON TESTING
                /*case "delete": { 
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    Product productToDelete = productDAO.readProduct(productId);
                    String imageUrl = productToDelete.getImageURL(); 
                    boolean productDeleted = productDAO.removeProduct(productId); 
                    if (productDeleted) {
                        deleteImageFile(imageUrl);
                    } else {
                        System.err.println("Error deleting product with ID: " + productId);
                    }
                    break;
                }*/
            }

            response.sendRedirect("ProductController?action=list");

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Helper method to extract file name from Part
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

    // Helper method to delete an image file
    private void deleteImageFile(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String imagePath = getServletContext().getRealPath("/") + imageUrl;
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                boolean deleted = imageFile.delete();
                if (!deleted) {
                    //deletion error
                    System.err.println("Error deleting image: " + imagePath);
                }
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Product controller thingy";
    }// </editor-fold>

}