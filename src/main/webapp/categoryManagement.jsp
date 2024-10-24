<%-- 
    Document   : categoryManagement
    Created on : 23-10-2024, 21:34:19
    Author     : Tran Le Gia Huy - CE180068
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Category Management</title>
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
                height: 100%;
            }
            .footerIframe {
                border: none;
                width: 100%;
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
        </style>
    </head>

    <body>
        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <!-- User Management -->
        <div class="container-fluid fullpagecontent">
            <div class="col-md-12">
                <h6 class="card-title">Category Management</h6>
                <div class="card">
                    <div class="row">
                        <div>
                            <div class="row">
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary mb-3 col-md-2" data-bs-toggle="modal" data-bs-target="#addCategory">
                                    Add Category
                                </button>

                                <input type="text" id="input" onkeyup="searchFunction()" placeholder="Search for names..">
                            </div>
                            <table class="table table-striped" id="categoryList">                             
                                <tr>
                                    <th class="col-md-1">ID</th>
                                    <th class="col-md-3">Category Name</th>
                                    <th class="col-md-2">Parent Category ID</th>                             
                                    <th class="col-md-2">Actions</th>
                                </tr>                       
                                <!-- User data will be loaded here -->      

                                <c:forEach items="${categoryList}" var="c">                                 
                                    <tr>
                                        <td class="col-md-1">${c.categoryId}</td>
                                        <td class="col-md-3">${c.categoryName}</td>
                                        <td class="col-md-2">${c.parentCategoryID}</td>                                   
                                        <td class="col-md-2">
                                            <button data-bs-toggle="modal" 
                                                    data-bs-target="#editCategory"
                                                    data-category-id ="${c.categoryId}"
                                                    data-category-name="${c.categoryName}"
                                                    data-parent-category-id="${c.parentCategoryID}"
                                                    class="btn btn-secondary">Update</button>
                                            <a onclick="return confirm('Are you sure you want to delete this category: ${c.categoryName} | ID: ${c.categoryId}?')" href="/CategoryController/delete?categoryID=${c.categoryId}" class="btn btn-danger">Remove</a>   
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>                       
                    </div>
                </div>
            </div>
        </div>


        <!-- Modal Add -->
        <div class="modal fade" id="addCategory" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Add Category</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="categoryForm" action="CategoryController/add" method="post">
                            <div class="form-group">
                                <label for="categoryName" class="form-label">Category Name</label>
                                <input type="text" name="categoryName" class="form-control" id="categoryName" placeholder="Category Name" required>
                            </div>
                            <div class="form-group">
                                <label for="parentCategoryID" class="form-label">Parent Category ID</label>
                                <input type="text" class="form-control" name="parentCategoryID"  placeholder="Parent Category ID">
                            </div>
                            <div class=" form-group d-flex justify-content-center">
                                <input type="submit" class="btn btn-primary me-3 p-2" value="Add Category"/>
                                <button type="button" class="btn btn-secondary me-3 p-2" data-bs-dismiss="modal">Close</button>   
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">                                         
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal Edit -->
        <div class="modal fade" id="editCategory" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Category's detail information</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="categoryForm" action="CategoryController/edit" method="post">
                            <div class="form-group">
                                <label for="categoryId" class="form-label">ID:</label>
                                <input type="text" name="categoryID" id="categoryID" class="form-control"  readonly="" >
                            </div>
                            <div class="form-group">
                                <label for="categoryName" class="form-label">Category Name</label>
                                <input type="text" name="categoryName" id="categoryName" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="parentCategoryID" class="form-label">Parent Category ID</label>
                                <input type="text" name="parentCategoryID" id="parentCategoryID" class="form-control">
                            </div>
                            <div class=" form-group d-flex justify-content-center">
                                <input type="submit" class="btn btn-primary me-3 p-2" value="Update"/>
                                <button type="button" class="btn btn-secondary me-3 p-2" data-bs-dismiss="modal">Close</button>   
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">                                         
                    </div>
                </div>
            </div>
        </div>
        <footer>
            <iframe class="footerIframe" src="adminFooter.jsp" height="70px"></iframe>
        </footer>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous">
    </script>
    <script>
        function searchFunction() {
            // Declare variables
            var input, filter, table, td, tr, i, txtValue;
            input = document.getElementById('input');
            filter = input.value.toUpperCase();
            table = document.getElementById("categoryList");
            tr = table.rows;
            // Loop through all list items, and hide those who don't match the search query
            for (i = 0; i < tr.length; i++) {
                td = tr[i].getElementsByTagName("td")[1];
                // Check if td exists
                if (td) {
                    txtValue = td.textContent.toUpperCase();
                    if (txtValue.indexOf(filter) > -1) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }
            }
        }

        // edit employee modal 
        const editCategory = document.getElementById('editCategory');
        editCategory.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-category-id');
            const categoryName = button.getAttribute('data-category-name');
            const parentCategoryID = button.getAttribute('data-parent-category-id');
            document.getElementById('categoryID').value = id;
            document.getElementById('categoryName').value = categoryName;
            document.getElementById('parentCategoryID').value = parentCategoryID;
            // them data cho may cai modal
        });

    </script>
</html>
