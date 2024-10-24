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

            .product-card {
                width: 250px;
                margin-right: 10px;
                display: inline-block;
            }

            .product-card img {
                height: 200px;
                width: 100%;
                object-fit: cover;
            }

            .product-card .card-text {
                /* Truncate long descriptions with ellipsis */
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                text-decoration: none;
            }
            .view-more-card {
                width: 150px;
                margin-right: 10px;
                display: inline-block;
                text-align: center;
                text-decoration: none;
            }

            .view-more-card .card-body {
                padding-top: 110px;
            }

            .view-more-card img {
                width: 70px;
                height: 70px;
                margin: 0 auto;
                padding: auto;
                display: block;
            }

            .view-more-card .card-title {
                font-size: 1rem;
                text-decoration: none;
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
                                <div class="row"> 
                                    <c:forEach items="${productList}" var="p" begin="0" end="4">
                                        <div class="card product-card">
                                            <img src="${p.imageURL}" class="card-img-top" alt="${p.productName}">
                                            <div class="card-body">
                                                <h5 class="card-title">${p.productName}</h5>
                                                <p class="card-text"> Created: ${p.createdAt}</p>
                                                <p class="card-text"> Updated: ${p.updatedAt}</p>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <div class="card view-more-card" onclick="goToProductManagement()">
                                        <div class="card-body">
                                            <img src="img/icon/external-link.svg" alt="View More">  
                                        </div>
                                    </div>

                                </div>

                            </div>
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
            <iframe src="adminFooter.jsp" height="70px"></iframe>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <script>
                                        function goToProductManagement() {
                                            window.location.href = 'ProductController?action=list&page=Product';
                                        }
        </script>
    </body>
</html>