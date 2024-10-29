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
                                                <td>${product.categoryID}</td>
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
        <script>
                                                           document.addEventListener('DOMContentLoaded', function () {
                                                               attachEditButtonListeners();
                                                               attachFormValidation();
                                                           });

                                                           function attachEditButtonListeners() {
                                                               const editButtons = document.querySelectorAll('.editProductBtn');
                                                               editButtons.forEach(button => {
                                                                   button.addEventListener('click', function (event) {
                                                                       const modal = document.getElementById('editProductModal');
                                                                       const form = modal.querySelector('form');

                                                                       const productId = this.getAttribute('data-product-id');
                                                                       const productName = this.getAttribute('data-product-name');
                                                                       const description = this.getAttribute('data-product-description');
                                                                       const price = this.getAttribute('data-product-price');
                                                                       const categoryId = this.getAttribute('data-product-categoryid');
                                                                       const stockQuantity = this.getAttribute('data-product-stockquantity');

                                                                       form.querySelector('[name="productId"]').value = productId;
                                                                       form.querySelector('[name="productName"]').value = productName;
                                                                       form.querySelector('[name="description"]').value = description;
                                                                       form.querySelector('[name="price"]').value = price;
                                                                       form.querySelector('[name="categoryId"]').value = categoryId;
                                                                       form.querySelector('[name="stockQuantity"]').value = stockQuantity;
                                                                   });
                                                               });
                                                           }

                                                           function attachFormValidation() {
                                                               const addProductForm = document.querySelector('#addProductModal form');
                                                               const editProductForm = document.querySelector('#editProductModal form');

                                                               if (addProductForm) {
                                                                   addProductForm.addEventListener('submit', function (event) {
                                                                       event.preventDefault();

                                                                       if (!validateProductForm(this)) {
                                                                           return;
                                                                       } else {
                                                                           this.submit();
                                                                       }
                                                                   });
                                                               }

                                                               if (editProductForm) {
                                                                   editProductForm.addEventListener('submit', function (event) {
                                                                       event.preventDefault();

                                                                       if (!validateProductForm(this)) {
                                                                           return;
                                                                       } else {
                                                                           this.submit();
                                                                       }
                                                                   });
                                                               }
                                                           }

                                                           function validateProductForm(form) {
                                                               const productNameInput = form.querySelector('[name="productName"]');
                                                               const priceInput = form.querySelector('[name="price"]');
                                                               const categoryIdInput = form.querySelector('[name="categoryId"]');
                                                               const stockQuantityInput = form.querySelector('[name="stockQuantity"]');
                                                               const imageInput = form.querySelector('[name="image"]');

                                                               const productName = productNameInput.value;
                                                               if (!isValidProductName(productName)) {
                                                                   alert("Invalid product name. At least 1 letter & 80% valid characters.");
                                                                   return false;
                                                               }

                                                               const price = parseFloat(priceInput.value);
                                                               if (!isValidPrice(price)) {
                                                                   alert("Price must be between 0 and 10,000,000.");
                                                                   return false;
                                                               }

                                                               const categoryId = parseInt(categoryIdInput.value);

                                                               const stockQuantity = parseInt(stockQuantityInput.value);
                                                               if (!isValidStockQuantity(stockQuantity)) {
                                                                   alert("Stock quantity must be between 0 and 10,000,000.");
                                                                   return false;
                                                               }

                                                               const imageFile = imageInput.files[0];
                                                               if (imageFile && !isValidImageType(imageFile.type)) {
                                                                   alert("Invalid image type. Upload JPG, JPEG, or PNG.");
                                                                   return false;
                                                               }

                                                               return true;
                                                           }

                                                           function isValidProductName(productName) {
                                                               if (typeof productName !== 'string') {
                                                                   return false;
                                                               }

                                                               if (productName === null || productName.trim() === "") {
                                                                   return false;
                                                               }

                                                               if (!/[a-zA-Z]/.test(productName)) {
                                                                   return false;
                                                               }

                                                               let specialAndNumberCount = 0;
                                                               for (let i = 0; i < productName.length; i++) {
                                                                   const char = productName.charAt(i);
                                                                   if (!((char >= 'a' && char <= 'z') || (char >= 'A' && char <= 'Z') || char === ' ')) {
                                                                       specialAndNumberCount++;
                                                                   }
                                                               }

                                                               const combinedPercentage = (specialAndNumberCount / productName.length) * 100;
                                                               return combinedPercentage <= 20;
                                                           }

                                                           function isValidPrice(price) {
                                                               return price >= 0 && price <= 10000000;
                                                           }

                                                           function isValidStockQuantity(stockQuantity) {
                                                               return stockQuantity >= 0 && stockQuantity <= 10000000;
                                                           }

                                                           function isValidImageType(contentType) {
                                                               const allowedTypes = ["image/jpeg", "image/png"];
                                                               return allowedTypes.includes(contentType);
                                                           }

                                                           function searchProduct() {
                                                               var input, filter, table, tr, td, i, txtValue;
                                                               input = document.getElementById("productSearchInput");
                                                               filter = input.value.toUpperCase();
                                                               table = document.getElementById("productListTable");
                                                               tr = table.getElementsByTagName("tr");
                                                               for (i = 0; i < tr.length; i++) {
                                                                   td = tr[i].getElementsByTagName("td")[2];
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
        </script>
    </body>
</html>