<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shopping Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/cart.css">
        <style>
            /* Styles to make buttons larger and stick them in place */
            body {
                background-color: #F5F7FB;
                color: #1C1554;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
                height: 100%;
            }
            .card-container {
                margin-top: 20px;
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
            .cart-actions {
                position: sticky;
                top: 0;
                z-index: 2;
                background-color: #f8f9fa;
                padding: 15px;
            }
            .btn-large {
                font-size: 1em;
                padding: 10px 20px;
            }
        </style>
    </head>

    <body>
        <!-- Header -->
        <div class="container-fluid fullpagecontent">
            <div class="col-md-12">
                <header class="d-flex justify-content-between align-items-center p-3 border-bottom">
                    <a href="#" class="d-flex align-items-center text-decoration-none">
                        <span class="h5 ms-2">PAMB</span>
                    </a>
                    <nav class="d-none d-lg-flex gap-4">
                        <a href="homepage.jsp" class="text-decoration-none text-muted">Home</a>
                        <a href="#" class="text-decoration-none text-muted">Shop</a>
                        <a href="#" class="text-decoration-none text-muted">About</a>
                        <a href="#" class="text-decoration-none text-muted">Contact</a>
                    </nav>
                    <div class="d-flex align-items-center gap-3">
                        <a class="nav-link" href="#">Hello, Nguyen Nhat Dang</a>
                        <a href="/CartController">
                            <img src="../img/icon/shopping-cart.svg" alt="Cart" class="cart-icon">
                        </a>
                    </div>
                </header>
                <h6 class="card-title">Your Shopping Cart</h6>
                <div class="container">

                    <!-- Cart Actions Row -->
                    <div class="cart-actions d-flex justify-content-between align-items-center border-bottom mb-3">
                        <form action="/CartController" method="post" id="deleteSelectedForm" class="d-inline" 
                              onsubmit="return confirmRemoveSelected()">
                            <input type="hidden" name="action" value="deleteSelected">
                            <input type="hidden" id="selectedItemsInput" name="selectedItems">
                            <button class="btn btn-danger btn-large" type="submit">Remove Selected</button>
                        </form>
                        <form action="/OrderController" method="post">
                            <button class="btn btn-primary btn-large" type="submit">Buy Now</button>
                        </form>
                    </div>

                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th scope="col"><input type="checkbox" id="select-all"></th>
                                <th scope="col">Image</th>
                                <th scope="col">Product Name</th>
                                <th scope="col">Description</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Price</th>
                                <th scope="col">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartList}">
                                <tr>
                                    <td><input type="checkbox" class="form-check-input product-checkbox" name="selectedItems" value="${item.cartItemID}"></td>
                                    <td><img src="D:/image/4a5be7e32326b23d789ec4bd16c0c17a.jpg" class="img-fluid product-image" alt="Product Image" width="50"></td>
                                    <td>${item.productName}</td>
                                    <td>${item.description}</td>
                                    <td>
                                        <form action="/CartController" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="cartItemId" value="${item.cartItemID}">
                                            <input type="number" class="form-control quantity-input" name="quantity" value="${item.quantity}" min="1" max="${item.stockQuantity}">
                                            <button class="btn btn-sm btn-primary update-btn mt-2 ms-4" type="submit" style="display: none;">Update</button>
                                        </form>
                                    </td>
                                    <td>₫${item.price * item.quantity}</td>
                                    <td>
                                        <form action="/CartController" method="post" class="d-inline" onsubmit="return confirmRemoveSingle()">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="cartItemId" value="${item.cartItemID}">
                                            <button class="btn btn-sm btn-danger delete-btn" type="submit">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <script>
            function confirmRemoveSelected() {
                const selectedItems = document.getElementById('selectedItemsInput').value;
                if (selectedItems === "") {
                    alert("Please select at least one item to remove.");
                    return false;
                }
                return confirm("Are you sure you want to remove the selected items?");
            }

            function confirmRemoveSingle() {
                return confirm("Are you sure you want to remove this item?");
            }

            document.getElementById('select-all').addEventListener('click', function () {
                const checkboxes = document.querySelectorAll('.product-checkbox');
                checkboxes.forEach(checkbox => checkbox.checked = this.checked);
                updateSelectedItems();
            });

            document.querySelectorAll('.product-checkbox').forEach(checkbox => {
                checkbox.addEventListener('change', updateSelectedItems);
            });

            function updateSelectedItems() {
                const selectedItems = [];
                document.querySelectorAll('.product-checkbox:checked').forEach(checkbox => {
                    selectedItems.push(checkbox.value);
                });
                document.getElementById('selectedItemsInput').value = selectedItems.join(',');
            }

            document.querySelectorAll('.quantity-input').forEach(input => {
                input.addEventListener('input', function () {
                    const updateButton = this.closest('form').querySelector('.update-btn');
                    updateButton.style.display = 'inline-block';
                });
            });
        </script>

    </body>
</html>
