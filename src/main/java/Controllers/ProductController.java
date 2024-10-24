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

/**
 *
 * @author Le Trung Hau - CE180481
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})

@MultipartConfig //handling file upload

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
        String path = request.getRequestURI();
        String page = "";

        if (path.contains("Control")) {
            page = "Control";
        } else if (path.contains("Product")) {
            page = "Product";
        }

        try {
            /*
        list: gom het tru may cai da xoa
        null: gom het tru may cai da xoa
        default: gom het tru may cai da xoa
        searchByName: search gan giong ten
        searchByCategory: search id category
        deleted: gom het may cai da xoa
        listControl: list top 5 gan nhat cho trang Control
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
                    //product management page
                    if ("Product".equals(request.getParameter("page"))) {
                        request.setAttribute("productList", productList);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("productManagement.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        //return json data
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
                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                    //IMPORTANT, TESTING
                }
                case "listControl": {
                    productList = productDAO.viewProductListControl();
                    break;
                }

                // SAU NAY THEM CASES THI THEM O DAY
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

            String imageUploadPath = getServletContext().getRealPath("/img/pro");
            File imageUploadDir = new File(imageUploadPath);
            if (!imageUploadDir.exists()) {
                imageUploadDir.mkdirs();
            }
            Part filePart = request.getPart("image");
            String fileName = getFileName(filePart);

            switch (action) {
                case "add": {
                    String productName = request.getParameter("productName");
                    String description = request.getParameter("description");

                    if (productName == null || productName.trim().isEmpty()) {
                        request.setAttribute("errorMessage", "Product name is required.");
                        if (page.equals("Control")) {
                            request.getRequestDispatcher("adminControl.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("productManagement.jsp").forward(request, response);
                        }
                        return;
                    }

                    float price = Float.parseFloat(request.getParameter("price"));
                    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                    int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

                    if (fileName == null || fileName.isEmpty()) {
                        fileName = "default.jpg";
                    } else {
                        fileName = new File(fileName).getName();
                    }

                    String imageUrl = "img/pro/" + fileName;

                    Product newProduct = new Product(0, productName, description, price, imageUrl,
                            categoryId, stockQuantity, null, null);
                    productDAO.addProduct(newProduct);

                    //save
                    if (filePart != null && filePart.getSize() > 0) {
                        String fullImagePath = imageUploadPath + File.separator + fileName;
                        filePart.write(fullImagePath);
                    }
                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                    //break;
                }

                case "update": {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    String updatedProductName = request.getParameter("productName");
                    String updatedDescription = request.getParameter("description");
                    float updatedPrice = Float.parseFloat(request.getParameter("price"));
                    int updatedCategoryId = Integer.parseInt(request.getParameter("categoryId"));
                    int updatedStockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

                    String updatedImageUrl;

                    if (fileName == null || fileName.isEmpty()) {
                        // Keep existing image
                        Product existingProduct = productDAO.readProduct(productId);
                        if (existingProduct != null) {
                            updatedImageUrl = existingProduct.getImageURL();
                        } else {
                            System.err.println("Error: Product with ID " + productId + " not found in the database.");
                            updatedImageUrl = "img/pro/default.jpg";
                            //.....
                        }
                    } else {
                        fileName = new File(fileName).getName();
                        updatedImageUrl = "img/pro/" + fileName;

                        if (filePart != null && filePart.getSize() > 0) {
                            String fullImagePath = imageUploadPath + File.separator + fileName;
                            filePart.write(fullImagePath);
                        }
                    }

                    Product updatedProduct = new Product(productId, updatedProductName, updatedDescription,
                            updatedPrice, updatedImageUrl, updatedCategoryId,
                            updatedStockQuantity, null, null);
                    productDAO.updateProduct(updatedProduct);
                    response.sendRedirect("ProductController?action=list&page=Product");
                    return;
                    //break;
                }

                //DELETE - nuh uh
            }
            if (page.equals("Control")) {
                response.sendRedirect("ProductController?action=listControl");
            } else {
                response.sendRedirect("ProductController?action=list&page=Product");
            }
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
