<%-- 
    Document   : cart
    Created on : Oct 20, 2024, 4:40:35 PM
    Author     : Nguyen Nhat Dang - CE180010 
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shopping Cart</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/cart.css">
    </head>

    <body>
        <div class="d-flex flex-column min-vh-100">
            <!-- Header -->
            <header class="d-flex justify-content-between align-items-center p-3 border-bottom">
                <a href="#" class="d-flex align-items-center text-decoration-none">
                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">
                    <a href="#" class="text-decoration-none text-muted">Home</a>
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

            <!-- Cart -->
            <div class="container mt-4">
                <h1 class="mb-4">Your Shopping Cart</h1>

                <!-- Iterate through cart items -->
                <c:forEach var="item" items="${cartList}">
                    <!-- Với mỗi mục giỏ hàng, lấy sản phẩm tương ứng sử dụng chỉ số trong productList -->

                    <div class="card mb-3">
                        <div class="row g-0 align-items-center">
                            <div class="col-md-1">
                                <!-- Ô checkbox để chọn sản phẩm -->
                                <input type="checkbox" class="form-check-input product-checkbox" name="selectedItems" value="${item.cartItemID}">
                            </div>
                            <div class="col-md-2">
                                <img src="" class="img-fluid rounded-start product-image" alt="Hình ảnh sản phẩm">
                            </div>
                            <div class="col-md-7">
                                <div class="card-body">
                                    <h5 class="card-title">${item.productName}</h5>
                                    <p class="card-text">${item.description}</p>
                                </div>
                            </div>
                            <div class="col-md-2 d-flex flex-column justify-content-between">
                                <div class="d-flex justify-content-between align-items-center">
                                    <p class="price">₫${item.price}</p>
                                </div>
                                <div class="input-group mb-3">
                                    <!-- Form để cập nhật số lượng -->
                                    <form action="/CartController" method="post" class="d-flex">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="cartItemId" value="${item.cartItemID}">
                                        <button class="btn btn-outline-secondary" type="submit" name="change" value="-">-</button>
                                        <input type="number" class="form-control" name="quantity" value="${item.quantity}" min="1">
                                        <button class="btn btn-outline-secondary" type="submit" name="change" value="+">+</button>
                                    </form>
                                </div>
                                <!-- Nút để xóa một mục cụ thể -->
                                <form action="/CartController" method="post">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="cartItemId" value="${item.cartItemID}">
                                    <button class="btn delete-btn btn-sm" type="submit">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>


                <!-- Select All and Total Payment Section -->
                <div class="card p-3 mt-3 d-flex justify-content-between align-items-center">
                    <div>
                        <input type="checkbox" class="form-check-input" id="select-all">
                        <label for="select-all" class="select-all">Select All</label>
                        <!-- Button to delete selected items -->
                        <form action="/CartController" method="post" id="deleteSelectedForm">
                            <input type="hidden" name="action" value="deleteSelected">
                            <button class="btn delete-btn btn-sm ms-3" type="submit">Delete Selected</button>
                        </form>
                    </div>
                    <div class="d-flex align-items-center mt-3">
                        <form action="/OrderController" method="post">
                            <button class="btn buy-now" type="submit">Buy</button>
                        </form>
                    </div>
                </div>
            </div>

        </div>

        <script>
            // Select all checkbox functionality
            document.getElementById('select-all').addEventListener('click', function () {
                const checkboxes = document.querySelectorAll('.product-checkbox');
                checkboxes.forEach(checkbox => checkbox.checked = this.checked);
            });
        </script>
    </body>

</html>
