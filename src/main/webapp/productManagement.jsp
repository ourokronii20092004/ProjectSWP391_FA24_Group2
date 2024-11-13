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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            body {
                background-color: #f8f9fa;
                color: #343a40;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
            }
            .container-fluid {
                padding: 2rem 1rem;
                flex: 1;
                overflow-y: auto;
                height: calc(100vh - 130px);

            }
            .card {
                border: none;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
                padding: 2rem;
                background-color: white;
            }
            .card-title {
                color: #261d6a;
                font-weight: 600;
                margin-bottom: 1.5rem;
            }
            #productListTable {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 1rem;
                table-layout: fixed;


            }
            #productListTable th, #productListTable td {
                padding: 1.5rem 1rem;
                border-bottom: 1px solid #dee2e6;
                text-align: left;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;

            }
            #productListTable img {
                width: 70px;
                height: 70px;
                object-fit: cover;
                border-radius: 5px;
                margin-right: 1rem;
            }

            #productListTable th:first-child, #productListTable td:first-child {
                width: 5%;
            } /* ID */
            #productListTable th:nth-child(2), #productListTable td:nth-child(2) {
                width: 10%;
            } /* Image */
            #productListTable th:nth-child(3), #productListTable td:nth-child(3) {
                width: 18%;
            } /* Name */
            #productListTable th:nth-child(4), #productListTable td:nth-child(4) {
                width: 22%;
            } /* Description */
            #productListTable th:nth-child(5), #productListTable td:nth-child(5) {
                width: 10%;
            } /* Price */
            #productListTable th:nth-child(6), #productListTable td:nth-child(6) {
                width: 10%;
            } /* Category ID */
            #productListTable th:nth-child(7), #productListTable td:nth-child(7) {
                width: 10%;
            } /* Stock */
            #productListTable th:last-child, #productListTable td:last-child {
                width: 15%;
            } /* Actions */




            #productListTable input[type="checkbox"] {
                transform: scale(1.5);
                accent-color: #261d6a;
            }
            .table-responsive {
                overflow-x: auto;
            }
            .btn-primary {
                background-color: #261d6a;
                border-color: #261d6a;
            }
            footer {
                background-color: #3E3E3E;
                color: white;
                text-align: center;
                padding: 1rem 0;
                margin-top: auto;
            }

            iframe {
                border: none;
                width: 100%;
            }

            html, body {
                overflow-y: auto;
            }

            .title-and-buttons {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 1.5rem;
            }
            .search-and-filter {
                display: flex;
                align-items: center;
                gap: 1rem;
            }
            .search-button {
                background: none;
                border: none;
                padding: 0;
                cursor: pointer;
            }
            .search-button img {
                width: 20px;
                height: 20px;
            }

        </style>
    </head>
    <body>
        <iframe src="adminNavbar.jsp" height="60px"></iframe>
        <div class="container-fluid">
            <div class="card">
                <div class="title-and-buttons">
                    <h5 class="card-title">Product Management</h5>
                    <div>
                        <a href="ProductController?action=deleted&page=Product" class="btn btn-secondary me-2">Restore Products Page</a>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addProductModal">Add Product</button>

                    </div>
                </div>

                <c:if test="${not empty noResultsMessage}">
                    <div class="alert alert-warning" role="alert">
                        ${noResultsMessage}
                    </div>
                </c:if>

                <form action="ProductController" method="GET">
                    <input type="hidden" name="action" value="search">
                    <div class="search-and-filter">
                        <input type="text" name="searchName" placeholder="Search for products..." class="form-control">
                        <select name="categoryId" class="form-select">
                            <option value="">All Categories</option>
                            <c:forEach items="${categoryList}" var="category">
                                <option value="${category.categoryId}">${category.categoryName}</option> 
                            </c:forEach>
                        </select>
                        <button type="submit" class="search-button">
                            <img src="img/icon/search.svg" alt="Search">
                        </button>
                    </div>

                </form>


                <div class="table-responsive mt-3">
                    <table class="table" id="productListTable">
                        <thead>
                            <tr>
                                <th style="width: 10%;">Image</th>
                                <th style="width: 5%;">ID</th>
                                <th style="width: 10%;">Name</th>
                                <th style="width: 35%;">Description</th>
                                <th style="width: 10%;">Price</th>
                                <th style="width: 10%;">Category</th>
                                <th style="width: 10%;">Stock</th>
                                <th style="width: 10%;">Actions</th>
                            </tr>
                        </thead>
                        <tbody id="productTableBody">
                            <c:forEach items="${productList}" var="product"> 
                                <tr>
                                    <td>
                                        <c:if test="${not empty product.imageURL}">
                                            <img src="${product.imageURL}" alt="${product.productName}"> 
                                        </c:if>
                                    </td>
                                    <td>${product.productID}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.description}</td>
                                    <td>${product.price}</td>
                                    <td class="category-id">${product.categoryID}</td>
                                    <td>${product.stockQuantity}</td>
                                    <td>
                                        <button class="btn btn-sm btn-warning editProductBtn"
                                                data-bs-toggle="modal"
                                                data-bs-target="#editProductModal"
                                                data-product-id="${product.productID}"
                                                data-product-name="${product.productName}"
                                                data-product-description="${product.description}"
                                                data-product-price="${product.price}"
                                                data-product-categoryid="${product.categoryID}"
                                                data-product-stockquantity="${product.stockQuantity}"
                                                data-product-imageurl="${product.imageURL}">Edit</button>

                                        <a href="ProductController?action=delete&productId=${product.productID}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach> 
                        </tbody>
                    </table>
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
                                    <label for="addProduct_categoryId" class="form-label">Category:</label>
                                    <select class="form-select" id="addProduct_categoryId" name="categoryId" required></select> 
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
                                    <label for="editProduct_categoryId" class="form-label">Category:</label>
                                    <select class="form-select" id="editProduct_categoryId" name="categoryId" required></select>
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
    </div>
    <footer>
        <iframe src="adminFooter.jsp" height="70px"></iframe>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="js/ProductManagement.js"></script>
</body>
</html>