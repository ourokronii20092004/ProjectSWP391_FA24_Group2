<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Restore Products | PAMP</title>
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
            }
            #productListTable th, #productListTable td {
                padding: 1.5rem 1rem;
                border-bottom: 1px solid #dee2e6;
                text-align: left;
            }
            #productListTable img {
                width: 70px;
                height: 70px;
                object-fit: cover;
                border-radius: 5px;
                margin-right: 1rem;
            }
            #productListTable input[type="checkbox"] {
                transform: scale(1.5);
                accent-color: #261d6a;
                margin-left: auto;
            }
            #productListTable th:first-child, #productListTable td:first-child {
                width: 5%;
            }
            #productListTable th:nth-child(2), #productListTable td:nth-child(2) {
                width: 10%;
            }
            #productListTable th:nth-child(3), #productListTable td:nth-child(3) {
                width: 25%;
            }
            #productListTable th:nth-child(4), #productListTable td:nth-child(4) {
                width: 15%;
            }
            #productListTable th:nth-child(5), #productListTable td:nth-child(5) {
                width: 15%;
            }
            #productListTable th:last-child, #productListTable td:last-child {
                width: 5%;
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
            .bulk-actions {
                display: flex;
                gap: 1rem;
                margin-bottom: 1rem;
            }
            iframe {
                border: none;
                width: 100%;
            }
            .back-button-container {
                margin-bottom: 1rem;
            }
            html, body {
                overflow-y: auto;
            }
            .container-fluid {
                overflow-y: auto;
                height: calc(100vh - 130px);
            }
            .top-actions {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 1.5rem;
            }
        </style>
    </head>
    <body>

        <iframe src="adminNavbar.jsp" height="60px"></iframe>
        <div class="container-fluid">
            <div class="card">
                <h5 class="card-title">Restore Products</h5>
                <form action="ProductController" method="POST" id="productActionsForm">
                    <input type="hidden" name="action" value="bulkAction">
                    <div class="top-actions">
                    <div class="bulk-actions">
                        <button type="submit" class="btn btn-success me-2" name="bulkRestore" value="restore">Restore Selected</button> <%-- Added right margin --%>
                        <button type="submit" class="btn btn-danger" name="bulkDelete" value="deleteFinal" onclick="return confirm('This action is irreversible. Are you sure you want to permanently delete selected products?')">Delete Selected</button>
                    </div>

                    <div class="back-button-container">
                        <a href="ProductController?action=list&page=Product" class="btn btn-primary">Back to Product List</a>
                    </div>


                </div>
                    <input type="text" id="productSearchInput" onkeyup="searchProduct()" placeholder="Search for products..." class="form-control mb-3">
                    <div class="table-responsive">
                        <table class="table" id="productListTable">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Image</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Category ID</th>
                                    <th><input type="checkbox" id="selectAllCheckbox"></th> 
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${productList}" var="product">
                                    <c:if test="${product.stockQuantity == -1}">
                                        <tr>
                                            <td>${product.productID}</td>
                                            <td><img src="${product.imageURL}" alt="${product.productName}"></td>
                                            <td>${product.productName}</td>
                                            <td>${product.price}</td>
                                            <td>${product.categoryID}</td>
                                            <td><input type="checkbox" name="selectedProducts" value="${product.productID}"></td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
        <footer>
            <iframe src="adminFooter.jsp" height="70px"></iframe>
        </footer>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/ProductManagement.js"></script>
        <script>
                        const selectAllCheckbox = document.getElementById('selectAllCheckbox');
                        const individualCheckboxes = document.querySelectorAll('input[name="selectedProducts"]');
                        selectAllCheckbox.addEventListener('change', function () {
                            individualCheckboxes.forEach(checkbox => {
                                checkbox.checked = this.checked;
                            });
                        });
        </script>
    </body>
</html>