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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
        <!-- Alert for Error Messages -->
        <c:if test="${not empty sessionScope.errorMessage}">
            <script>
                //Immediately display an alert on page load if error message exists
                const errorMessage = "${sessionScope.errorMessage}";
                if (errorMessage) {
                    Swal.fire({
                        text: errorMessage
                    });
                    // Remove the error message from the session after displaying
                    // This prevents it from appearing again if the user refreshes the page
                <% session.removeAttribute("errorMessage"); %>
                }
            </script>
        </c:if>

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

                                <div class="row">
                                    <label for="parentCategoryFilter">Filter by Parent Category:</label>
                                    <select id="parentCategoryFilter" class="form-control" onchange="filterCategories()">
                                        <option value="">All</option>
                                        <c:forEach items="${categoryList}" var="c">
                                            <c:if test="${c.parentCategoryID == null || c.parentCategoryID == 0}">
                                                <option value="${c.categoryId}">${c.categoryName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                                <input type="hidden" name="action" value="search">


                            </div>
                            <table class="table table-striped" id="categoryList">                             
                                <tr>
                                    <th class="col-md-1">ID</th>
                                    <th class="col-md-3">Category Name</th>
                                    <th class="col-md-2">Actions</th>
                                </tr>                       
                                <!-- User data will be loaded here -->      

                                <c:forEach items="${categoryList}" var="c">                                 
                                    <tr>
                                        <td class="col-md-1">${c.categoryId}</td>
                                        <td class="col-md-3">${c.categoryName}</td>                   
                                        <td class="col-md-2">
                                            <button data-bs-toggle="modal" 
                                                    data-bs-target="#editCategory"
                                                    data-category-id ="${c.categoryId}"
                                                    data-category-name="${c.categoryName}"
                                                    data-parent-category-id="${c.parentCategoryID}"
                                                    class="btn btn-secondary">Update</button>
                                            <button data-bs-toggle="modal" 
                                                    data-bs-target="#categoryDetail"
                                                    data-category-id="${c.categoryId}"
                                                    data-category-name="${c.categoryName}"
                                                    data-parent-category-id="${c.parentCategoryID}"
                                                    data-parent-category-name="${c.parentCategoryName}"
                                                    class="btn btn-info">Detail</button>       
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
                                <select class="form-control" name="parentCategoryID" id="ParentCategoryID">
                                    <option value="">None</option> 
                                    <c:forEach items="${categoryList}" var="category"> 
                                        <c:if test="${category.parentCategoryID == null || category.parentCategoryID == 0}"> <%-- Kiểm tra Category cha --%>
                                            <option value="${category.categoryId}" ${category.categoryId == c.parentCategoryID ? 'selected' : ''}>${category.categoryName}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
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
                            <input type="hidden" name="editingCategoryID" id="editingCategoryID" value="${editingCategoryID}">
                            <div class="form-group">
                                <label for="categoryId" class="form-label">ID:</label>
                                <input type="text" name="categoryID" id="editCategoryID" class="form-control"  readonly="" >
                            </div>
                            <div class="form-group">
                                <label for="categoryName" class="form-label">Category Name</label>
                                <input type="text" name="categoryName" id="editCategoryName" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="parentCategoryID" class="form-label">Parent Category ID</label>
                                <select class="form-control" name="parentCategoryID" id="editParentCategoryID">
                                    <option value="">None</option>   
                                    <c:forEach items="${categoryList}" var="category">                    
                                        <c:if test="${category.categoryId != editParentCategoryID && (category.parentCategoryID == null || category.parentCategoryID == 0)}"> <%-- Kiểm tra Category cha --%>
                                            <option value="${category.categoryId}" ${category.categoryId == c.parentCategoryID ? 'selected' : ''}> ${category.categoryName}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
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
        <!-- Modal Detail -->
        <div class="modal fade" id="categoryDetail" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Category Details</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="detailCategoryID" class="form-label">ID:</label>
                            <input type="text" id="detailCategoryID" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label for="detailCategoryName" class="form-label">Category Name:</label>
                            <input type="text" id="detailCategoryName" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label for="detailParentCategoryID" class="form-label">Parent Category:</label>
                            <input type="text" id="detailParentCategoryID" class="form-control" readonly>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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

            document.getElementById('editCategoryID').value = id;
            document.getElementById('editCategoryName').value = categoryName;
            document.getElementById('editParentCategoryID').value = parentCategoryID;
            // them data cho may cai modal
        });

        const categoryDetail = document.getElementById('categoryDetail');
        categoryDetail.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-category-id');
            const categoryName = button.getAttribute('data-category-name');
            const parentCategoryID = button.getAttribute('data-parent-category-id');
            const parentCategoryName = button.getAttribute('data-parent-category-name');

            document.getElementById('detailCategoryID').value = id;
            document.getElementById('detailCategoryName').value = categoryName;

            // Hiển thị "None" nếu ParentCategoryID bằng null hoặc 0
            const parentCategoryInput = document.getElementById('detailParentCategoryID');
            parentCategoryInput.value = parentCategoryName || 'None';
        });

        function filterCategories() {
            let parentCategoryId, filter, table, tr, td, i, txtValue;
            parentCategoryId = document.getElementById('parentCategoryFilter').value;
            filter = parentCategoryId; // The filter is the selected parentCategoryID
            table = document.getElementById("categoryList");
            tr = table.getElementsByTagName("tr");

            for (i = 1; i < tr.length; i++) { //Start from index 1 to skip header row
                td = tr[i].getElementsByTagName("td")[0]; // Assuming ParentCategoryID is in the first column (index 0)
                if (td) {
                    txtValue = td.textContent || td.innerText;
                    if (filter === "" || txtValue === filter) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }
            }
        }
    </script>
</html>
