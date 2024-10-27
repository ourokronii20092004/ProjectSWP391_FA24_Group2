<%-- 
    Document   : productManagement.jsp
    Created on : Oct 24, 2024, 9:42:29 AM
    Author     : Le Trung Hau - CE180481
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Management | PAMP</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            body {
                background-color: #F5F7FB;
                color: #1C1554;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
            }

            .card-container {
                margin-top: 20px;
            }

            .card-title {
                color: #888;
                margin-bottom: 10px;
            }

            .card {
                background-color: #FFFFFF;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                border: none;
                padding: 20px;
            }

            .fullpagecontent {
                flex: 1;
            }

            footer {
                background-color: #3E3E3E;
                color: #FFFFFF;
                text-align: center;
                padding: 1rem 0;
            }

            iframe {
                border: none;
                width: 100%;
            }

            /* Hide forms initially */
            #addCategoryForm,
            #editCategoryForm,
            #addProductForm,
            #editProductForm,
            #addUserForm,
            #editUserForm,
            #editCommentForm {
                display: none;
            }


            #productListTable img {
                height: 50px;
                width: auto;
            }
        </style>
    </head>
    <body>
        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <div class="container-fluid fullpagecontent">
            <!-- Product Management -->
            <div class="row card-container">
                <div class="col-md-12">
                    <h6 class="card-title">Product Management</h6>
                    <div class="card">
                        <div class="row">
                            <div class="col-md-12">

                                <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addProductModal">
                                    Add Product
                                </button>

                                <!-- Search Bar -->
                                <input type="text" id="productSearchInput" onkeyup="searchProduct()" placeholder="Search for products...">

                                <!-- Product Table -->
                                <table class="table table-striped" id="productListTable">
                                    <thead>
                                        <tr>
                                            <th style="width: 5%;">Image</th>
                                            <th style="width: 5%;">ID</th>
                                            <th style="width: 10%;">Name</th>
                                            <th style="width: 40%;">Description</th>
                                            <th style="width: 10%;">Price</th>
                                            <th style="width: 10%;">Category ID</th>
                                            <th style="width: 10%;">Stock</th>
                                            <th style="width: 10%;">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody id="productTableBody">
                                        <!-- Product data will be loaded here -->
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Add Product Modal -->
            <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addProductModalLabel">Add Product</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="ProductController" method="POST" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="add"> 
                                <div class="mb-3">
                                    <label for="addProduct_productName" class="form-label">Product Name:</label>
                                    <input type="text" class="form-control" id="addProduct_productName" name="productName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="addProduct_description" class="form-label">Description:</label>
                                    <textarea class="form-control" id="addProduct_description" name="description" rows="3" required></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="addProduct_price" class="form-label">Price:</label>
                                    <input type="number" class="form-control" id="addProduct_price" name="price" step="0.01" required>
                                </div>
                                <div class="mb-3">
                                    <label for="addProduct_categoryId" class="form-label">Category ID:</label>
                                    <input type="number" class="form-control" id="addProduct_categoryId" name="categoryId" required>
                                </div>
                                <div class="mb-3">
                                    <label for="addProduct_stockQuantity" class="form-label">Stock Quantity:</label>
                                    <input type="number" class="form-control" id="addProduct_stockQuantity" name="stockQuantity" required>
                                </div>
                                <div class="mb-3">
                                    <label for="addProduct_image" class="form-label">Image:</label>
                                    <input type="file" class="form-control" id="addProduct_image" name="image" accept="image/*">
                                </div>
                                <button type="submit" class="btn btn-success">Add Product</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Edit Product Modal -->
            <div class="modal fade" id="editProductModal" tabindex="-1" aria-labelledby="editProductModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editProductModalLabel">Edit Product</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="ProductController" method="POST" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="productId" id="editProduct_productId">
                                <div class="mb-3">
                                    <label for="editProduct_productName" class="form-label">Product Name:</label>
                                    <input type="text" class="form-control" id="editProduct_productName" name="productName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editProduct_description" class="form-label">Description:</label>
                                    <textarea class="form-control" id="editProduct_description" name="description" rows="3" required></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="editProduct_price" class="form-label">Price:</label>
                                    <input type="number" class="form-control" id="editProduct_price" name="price" step="0.01" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editProduct_categoryId" class="form-label">Category ID:</label>
                                    <input type="number" class="form-control" id="editProduct_categoryId" name="categoryId" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editProduct_stockQuantity" class="form-label">Stock Quantity:</label>
                                    <input type="number" class="form-control" id="editProduct_stockQuantity" name="stockQuantity" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editProduct_image" class="form-label">Image:</label>
                                    <input type="file" class="form-control" id="editProduct_image" name="image" accept="image/*">
                                </div>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <footer>
            <iframe src="adminFooter.jsp" height="70px"></iframe>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <script src="js/productManagement.js"></script>
    </body>
</html>