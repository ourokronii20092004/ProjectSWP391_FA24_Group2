<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Control Panel | PAMP</title>
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
                                <button id="showAddProductFormBtn" class="btn btn-primary mb-3">Add Product</button>

                                <input type="text" id="productSearchInput" onkeyup="searchProduct()" placeholder="Search for products..">

                                <!-- Add Product Form (initially hidden) -->
                                <div id="addProductForm" style="display: none;"> 
                                    <form action="ProductController" method="POST" enctype="multipart/form-data">
                                        <input type="hidden" name="action" value="add">
                                        <div class="mb-3">
                                            <label for="productName" class="form-label">Product Name:</label>
                                            <input type="text" class="form-control" id="productName" name="productName" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="description" class="form-label">Description:</label>
                                            <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="price" class="form-label">Price:</label>
                                            <input type="number" class="form-control" id="price" name="price" step="0.01" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="categoryId" class="form-label">Category ID:</label>
                                            <input type="number" class="form-control" id="categoryId" name="categoryId" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="stockQuantity" class="form-label">Stock Quantity:</label>
                                            <input type="number" class="form-control" id="stockQuantity" name="stockQuantity" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="image" class="form-label">Image:</label>
                                            <input type="file" class="form-control" id="image" name="image" accept="image/*"> 
                                        </div>
                                        <button type="submit" class="btn btn-success">Add Product</button>
                                    </form>
                                </div>

                                <table class="table table-striped" id="productListTable">
                                    <thead>
                                        <tr>
                                            <th>Image</th>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Price</th>
                                            <th>Category ID</th>
                                            <th>Stock</th> 
                                            <th>Actions</th> 
                                        </tr>
                                    </thead>
                                    <tbody id="productTableBody">
                                        <c:forEach items="${productList}" var="p">
                                            <tr>
                                                <td>
                                                    <c:if test="${p.imageURL != null}">
                                                        <img src="${p.imageURL}" alt="${p.productName}" height="50">
                                                    </c:if>
                                                </td>
                                                <td>${p.productID}</td>
                                                <td>${p.productName}</td>
                                                <td>${p.description}</td>
                                                <td>${p.price}</td>
                                                <td>${p.categoryID}</td>
                                                <td>${p.stockQuantity}</td>
                                                <td>
                                                    <button class="btn btn-sm btn-warning editProductBtn" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#editProductModal" 
                                                            data-product-id="${p.productID}"
                                                            data-product-name="${p.productName}"
                                                            data-product-description="${p.description}"
                                                            data-product-price="${p.price}"
                                                            data-product-categoryid="${p.categoryID}"
                                                            data-product-stockquantity="${p.stockQuantity}"
                                                            data-product-imageurl="${p.imageURL}">Edit</button>

                                                    <a href="ProductController?action=delete&productId=${p.productID}" 
                                                       class="btn btn-sm btn-danger"
                                                       onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
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
                                <input type="hidden" name="productId" id="editProductId">
                                <div class="mb-3">
                                    <label for="editProductName" class="form-label">Product Name:</label>
                                    <input type="text" class="form-control" id="editProductName" name="productName" required>
                                </div>
                                <!-- Add other fields for editing (description, price, etc.) similarly -->
                                <div class="mb-3">
                                    <label for="editDescription" class="form-label">Description:</label>
                                    <textarea class="form-control" id="editDescription" name="description" rows="3" required></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="editPrice" class="form-label">Price:</label>
                                    <input type="number" class="form-control" id="editPrice" name="price" step="0.01" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editCategoryId" class="form-label">Category ID:</label>
                                    <input type="number" class="form-control" id="editCategoryId" name="categoryId" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editStockQuantity" class="form-label">Stock Quantity:</label>
                                    <input type="number" class="form-control" id="editStockQuantity" name="stockQuantity" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editImage" class="form-label">Image:</label>
                                    <input type="file" class="form-control" id="editImage" name="image" accept="image/*"> 
                                </div>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>         

            <!-- Category Management -->
            <div class="row card-container">
                <div class="col-md-12">
                    <h6 class="card-title">Category Management</h6>
                    <div class="card">
                        <div class="row">
                            <div class="col-md-12">
                                <button id="showAddCategoryFormBtn" class="btn btn-primary mb-3">Add Category</button>

                                <c:if test="${not empty message}">
                                    <div class="alert alert-success" role="alert">${message}</div>
                                </c:if>
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger" role="alert">${errorMessage}</div>
                                </c:if>

                                <div id="addCategoryForm" style="display: none;">
                                    <form action="categoryController" method="POST">
                                        <input type="hidden" name="action" value="create">
                                        <div class="mb-3">
                                            <label for="categoryName" class="form-label">Category Name:</label>
                                            <input type="text" class="form-control" id="categoryName" name="categoryName" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="parentCategoryId" class="form-label">Parent Category ID (optional):</label>
                                            <input type="number" class="form-control" id="parentCategoryId" name="parentCategoryId">
                                        </div>
                                        <button type="submit" class="btn btn-success">Add Category</button>
                                    </form>
                                </div>

                                <table class="table table-striped" id="categoryListTable">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Parent Category ID</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${categoryList}" var="category">
                                            <tr>
                                                <td>${category.categoryId}</td>
                                                <td>${category.categoryName}</td>
                                                <td>${category.parentCategoryID}</td>
                                                <td>
                                                    <button class="btn btn-sm btn-warning editCategoryBtn"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#editCategoryModal"
                                                            data-category-id="${category.categoryId}"
                                                            data-category-name="${category.categoryName}"
                                                            data-category-parent-id="${category.parentCategoryID}">Edit</button>
                                                    <a href="categoryController?action=delete&categoryId=${category.categoryId}"
                                                       class="btn btn-sm btn-danger"
                                                       onclick="return confirm('Bạn có chắc chắn muốn xóa Category này không?')">Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Edit Category Modal -->
            <div class="modal fade" id="editCategoryModal" tabindex="-1" aria-labelledby="editCategoryModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editCategoryModalLabel">Edit Category</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="categoryController" method="POST">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="categoryId" id="editCategoryId">
                                <div class="mb-3">
                                    <label for="editCategoryName" class="form-label">Category Name:</label>
                                    <input type="text" class="form-control" id="editCategoryName" name="categoryName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="editParentCategoryId" class="form-label">Parent Category ID (optional):</label>
                                    <input type="number" class="form-control" id="editParentCategoryId" name="parentCategoryId">
                                </div>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>


            <!-- User Management -->
            <div class="row card-container">
                <div class="col-md-12">
                    <h6 class="card-title">User Management</h6>
                    <div class="card">
                        <div class="row">
                            <div class="col-md-6">
                                <button id="showAddUserFormBtn" class="btn btn-primary mb-3">Add User</button>
                                <div id="addUserForm">
                                    <!-- Form to add new user -->
                                </div>
                                <div id="editUserForm">
                                    <!-- Form to edit existing user -->
                                </div>
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Username</th>
                                            <th>Email</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody id="userList">
                                        <!-- User data will be loaded here -->
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-6">
                                <!-- You can add a preview or details section here -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Comments/Ratings Management -->
            <div class="row card-container">
                <div class="col-md-12">
                    <h6 class="card-title">Comments/Ratings Management</h6>
                    <div class="card">
                        <div class="row">
                            <div class="col-md-6">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>User</th>
                                            <th>Comment</th>
                                            <th>Rating</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody id="commentList">
                                        <!-- Comment data will be loaded here -->
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-6">
                                <div id="editCommentForm">
                                    <!-- Form to edit/approve/reject comments -->
                                </div>
                                <!-- You can add a preview or details section here -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <footer>
            <iframe src="adminFooter.jsp" height="100px"></iframe>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <script>

                                                           // JavaScript for handling data loading, form submissions, and dynamic updates will go here

                                                           // Add cat form
                                                           document.getElementById('showAddCategoryFormBtn').addEventListener('click', function () {
                                                               document.getElementById('addCategoryForm').style.display = 'block';
                                                               document.getElementById('editCategoryForm').style.display = 'none';
                                                           });

                                                           // Show
                                                           document.getElementById('showAddProductFormBtn').addEventListener('click', function () {
                                                               document.getElementById('addProductForm').style.display = 'block';
                                                           });

                                                           // Search Product
                                                           function searchProduct() {
                                                               var input, filter, table, tr, td, i, txtValue;
                                                               input = document.getElementById("productSearchInput");
                                                               filter = input.value.toUpperCase();
                                                               table = document.getElementById("productListTable");
                                                               tr = table.getElementsByTagName("tr");

                                                               for (i = 0; i < tr.length; i++) {
                                                                   td = tr[i].getElementsByTagName("td")[2]; // by name
                                                                   if (td) {
                                                                       txtValue = td.textContent || td.innerText;
                                                                       if (txtValue.toUpperCase().indexOf(filter) > -1) {
                                                                           tr[i].style.display = "";
                                                                       } else {
                                                                           tr[i].style.display = "none";
                                                                       }
                                                                   }
                                                               }
                                                           }

                                                           // edit pro modal 
                                                           const editProductModal = document.getElementById('editProductModal');
                                                           editProductModal.addEventListener('show.bs.modal', function (event) {
                                                               const button = event.relatedTarget;
                                                               const productId = button.getAttribute('data-product-id');
                                                               const productName = button.getAttribute('data-product-name');
                                                               const productDescription = button.getAttribute('data-product-description');
                                                               const productPrice = button.getAttribute('data-product-price');
                                                               const productCategoryId = button.getAttribute('data-product-categoryid');
                                                               const productStockQuantity = button.getAttribute('data-product-stockquantity');
                                                               const productImageURL = button.getAttribute('data-product-imageurl');

                                                               document.getElementById('editProductId').value = productId;
                                                               document.getElementById('editProductName').value = productName;
                                                               document.getElementById('editDescription').value = productDescription;
                                                               document.getElementById('editPrice').value = productPrice;
                                                               document.getElementById('editCategoryId').value = productCategoryId;
                                                               document.getElementById('editStockQuantity').value = productStockQuantity;
                                                               // them data cho may cai modal
                                                           });

                                                           // Edit Category Modal
                                                           const editCategoryModal = document.getElementById('editCategoryModal');
                                                           editCategoryModal.addEventListener('show.bs.modal', function (event) {
                                                               const button = event.relatedTarget;
                                                               const categoryId = button.getAttribute('data-category-id');
                                                               const categoryName = button.getAttribute('data-category-name');
                                                               const parentCategoryId = button.getAttribute('data-category-parent-id');

                                                               document.getElementById('editCategoryId').value = categoryId;
                                                               document.getElementById('editCategoryName').value = categoryName;
                                                               document.getElementById('editParentCategoryId').value = parentCategoryId;
                                                           });
        </script>
    </body>
</html>