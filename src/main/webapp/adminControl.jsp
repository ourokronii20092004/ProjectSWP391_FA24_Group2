<%-- 
    Document   : adminControl
    Created on : Oct 20, 2024, 2:48:55 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

            <!-- Category Management -->
            <div class="row card-container">
                <div class="col-md-12">
                    <h6 class="card-title">Category Management</h6>
                    <div class="card">
                        <div class="row">
                            <div class="col-md-6">
                                <button id="showAddCategoryFormBtn" class="btn btn-primary mb-3">Add Category</button>
                                <div id="addCategoryForm">
                                    <!-- Form to add new category -->
                                    <input type="text" id="categoryName" placeholder="Category Name"
                                           class="form-control mb-2">
                                    <button id="addCategoryBtn" class="btn btn-success">Add</button>
                                </div>
                                <div id="editCategoryForm">
                                    <!-- Form to edit existing category -->
                                    <input type="hidden" id="editCategoryId">
                                    <input type="text" id="editCategoryName" placeholder="Category Name"
                                           class="form-control mb-2">
                                    <button id="updateCategoryBtn" class="btn btn-warning">Update</button>
                                </div>
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody id="categoryList">
                                        <!-- Category data will be loaded here -->
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

            <!-- Product Management -->
            <div class="row card-container">
                <div class="col-md-12">
                    <h6 class="card-title">Product Management</h6>
                    <div class="card">
                        <div class="row">
                            <div class="col-md-6">
                                <button id="showAddProductFormBtn" class="btn btn-primary mb-3">Add Product</button>
                                <div id="addProductForm">
                                    <!-- Form to add new product -->
                                </div>
                                <div id="editProductForm">
                                    <!-- Form to edit existing product -->
                                </div>
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Image</th>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody id="productList">
                                        <!-- Product data will be loaded here -->
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
            <iframe src="adminFooter.jsp" height="50px"></iframe>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <script>
            // JavaScript for handling data loading, form submissions, and dynamic updates will go here

            // Example: Function to show the Add Category form
            document.getElementById('showAddCategoryFormBtn').addEventListener('click', function () {
                document.getElementById('addCategoryForm').style.display = 'block';
                document.getElementById('editCategoryForm').style.display = 'none';
            });

            // ... (add other JavaScript code for interactions and data handling) ...
        </script>
    </body>
</html>
