<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
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
            .nav-link {
                position: relative;
                padding-bottom: 10px;
            }

            .nav-link::after {
                content: '';
                position: absolute;
                left: 0;
                bottom: 0;
                width: 0;
                height: 2px;
                background-color: #00BFFF;
                transition: width 0.3s ease;
            }

            .nav-link:hover::after {
                width: 100%;
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
            /* Định dạng ảnh avatar */
            .avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                cursor: pointer;
                border: 2px solid black;
                transition: transform 0.2s ease;
            }

            .avatar:hover {
                transform: scale(1.1);
            }

            .custom-dropdown {
                position: absolute;
                top: 60px; /* Khoảng cách từ avatar xuống */
                right: 0px;
                z-index: 9999; /* Đảm bảo menu luôn ở trên cùng */
                background-color: white;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                display: none; /* Ẩn menu ban đầu */
            }

            .custom-dropdown a.dropdown-item {
                padding: 10px 15px;
                border-radius: 6px;
                transition: background-color 0.2s ease, color 0.2s ease;
            }

            .custom-dropdown a.dropdown-item:hover {
                background-color: #f0f0f5;
                color: #333;
            }

            /* Text styling for "Hello, User" */
            .custom-dropdown a.user-greeting {
                text-decoration: none;
                font-weight: bold;
                color: #6c757d;
                cursor: default;
                padding: 8px 15px;
                display: block;
                text-transform: capitalize;
            }
            .nav-link {
                color: #261d6a;
            }

            .navbar-brand {
                font-weight: bold;
            }

            .nav-link {
                position: relative;
                padding-bottom: 10px;
            }

            .nav-link::after {
                content: '';
                position: absolute;
                left: 0;
                bottom: 0;
                width: 0;
                height: 2px;
                background-color: #43C197;
                transition: width 0.3s ease;
            }

            .nav-link:hover::after {
                width: 100%;
            }

            .nav-link.bell-link {
                padding-bottom: 0;
            }

            .nav-link.bell-link::after {
                display: none;
            }

            .nav-link.bell-link:hover .bell-icon {
                animation: swing 0.8s ease-in-out;
            }
            @keyframes swing {
                0% {
                    transform: rotate(0deg);
                }

                25% {
                    transform: rotate(20deg);
                }

                50% {
                    transform: rotate(-20deg);
                }

                75% {
                    transform: rotate(5deg);
                }

                88% {
                    transform: rotate(-7deg);
                }

                97% {
                    transform: rotate(1deg);
                }

                100% {
                    transform: rotate(0deg);
                }
            }
        </style>
    </head>

    <body>
        <div class="d-flex flex-column min-vh-100">
            <header class="d-flex justify-content-between align-items-center p-3" style="background-color: #D3FFA3; border-bottom: 2px solid black;">
                <a href="/MainPageController" class="d-flex align-items-center text-decoration-none">

                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">
                    <a href="/MainPageController" class="text-decoration-none nav-link active">Home</a>
                    <a href="#" class="text-decoration-none nav-link active">About</a>
                    <a href="#" class="text-decoration-none nav-link active">Contact</a>
                    <a href="#" class="text-decoration-none nav-link active">Vouchers</a>
                </nav>
                <%
    int userID = (int) request.getSession().getAttribute("userID");
    UserDAO userDAO = new UserDAO();
                            
    User user = userDAO.getUserData(userID);
    String name = user.getUserName();
                %>
                <div class="d-flex align-items-center gap-3">
                    <div class="avatar-container ms-auto d-flex align-items-center">

                        <div class="nav-item" style="margin-top: 5px;">
                            <a class="nav-link bell-link position-relative me-2" style="padding-bottom: 5px;" href="#"
                               onclick="parent.location.href = this.href; return false;">
                                <img src="img/icon/bell.svg" alt="Notification" class="bell-icon">
                            </a>
                        </div>

                        <img src="<%= (user.getImgURL() != null && !user.getImgURL().isEmpty()) ? user.getImgURL() : "/img/avt/user.png" %>" alt="Avatar" class="avatar me-2 ms-2" id="avatarButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="margin-left: 15px;"> <!-- me-3 tạo margin-right cho avatar -->

                        <!-- Menu thả xuống -->
                        <div class="dropdown-menu dropdown-menu-left custom-dropdown" aria-labelledby="avatarButton">
                            <a class="user-greeting">Hello, <%= name %></a>
                            <a class="dropdown-item" href="/CustomerProfileController">Profile</a>
                            <a class="dropdown-item" href="LogoutController">Logout</a>
                        </div>
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
                        <form action="/OrderController" method="post" id="buySelected" onsubmit="return prepareOrderDetails()">
                            <input type="hidden" name="action" value="buySelected">
                            <input type="hidden" id="selectedItemsDetailsInput" name="selectedItemsDetails">
                            <button class="btn btn-primary btn-large" type="submit">Buy Now</button>
                        </form>
                    </div>

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

                <table class="table table-bordered table-striped ">
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
                                        <input type="hidden" name="cartItemId" value="${item.cartItemID}"> <input type="number" class="form-control quantity-input" name="quantity" value="${item.quantity}" min="1" max="${item.stockQuantity}">
                                        <button class="btn btn-sm btn-primary update-btn mt-2 ms-4" type="submit" style="display: none;">Update</button>
                                    </form>
                                </td>
                                <td>${item.price * item.quantity} VND</td>
                                <td>
                                    <form action="/CartController" method="post" class="d-inline" onsubmit="return confirmRemoveSingle()">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="cartItemId" value="${item.cartItemID}">
                                        <button class="btn btn-sm btn-danger delete-btn" type="submit">Remove</button>
                                    </form>
                                </td>
                            </tr>

                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartList}">
                                <tr>

                                    <td><input type="checkbox" class="form-check-input product-checkbox" name="selectedItems" value="${item.cartItemID}" data-product-id="${item.productID}" data-price="${item.price}"></td>
                                    <td><img src="D:/image/4a5be7e32326b23d789ec4bd16c0c17a.jpg" class="img-fluid product-image" alt="Product Image" width="50"></td>
                                    <td>${item.productName}</td>
                                    <td>${item.description}</td>
                                    <td>
                                        <form action="/CartController" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="cartItemId" value="${item.cartItemID}"> <input type="number" class="form-control quantity-input" name="quantity" value="${item.quantity}" min="1" max="${item.stockQuantity}">
                                            <button class="btn btn-sm btn-primary update-btn mt-2 ms-4" type="submit" style="display: none;">Update</button>
                                        </form>
                                    </td>
                                    <td>${item.price * item.quantity} VND</td>
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
                const selectedItems = []; // Mảng chứa cartItemID
                document.querySelectorAll('.product-checkbox:checked').forEach(checkbox => {
                    selectedItems.push(checkbox.value); // Thêm giá trị cartItemID
                });
                // Cập nhật input hidden với danh sách cartItemID
                document.getElementById('selectedItemsInput').value = selectedItems.join(',');
                document.getElementById('selectedItemsDetailsInput').value = selectedItems.join(',');
            }

            document.querySelectorAll('.quantity-input').forEach(input => {
                input.addEventListener('input', function () {
                    const updateButton = this.closest('form').querySelector('.update-btn');
                    updateButton.style.display = 'inline-block';
                });
            })
            document.getElementById('avatarButton').addEventListener('click', function (event) {
                var dropdownMenu = document.querySelector('.dropdown-menu');
                dropdownMenu.classList.toggle('show');
            });

            document.addEventListener('click', function (event) {
                const dropdown = document.querySelector('.dropdown-menu');
                const avatarButton = document.querySelector('#avatarButton');

                // Kiểm tra nếu người dùng nhấn ra ngoài dropdown hoặc avatarButton
                if (!avatarButton.contains(event.target) && !dropdown.contains(event.target)) {
                    const dropdownMenu = new bootstrap.Dropdown(avatarButton);
                    dropdownMenu.hide();
                }
            });
            function prepareOrderDetails() {
                const selectedItemsDetails = document.getElementById('selectedItemsDetailsInput').value;

                if (!selectedItemsDetails || selectedItemsDetails.trim() === "") {
                    alert("Please select at least one item before proceeding.");
                    return false;
                }
            }
        </script>


</body>
</html>