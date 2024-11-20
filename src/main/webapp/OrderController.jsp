<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="Models.OrderItem"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="DAOs.VoucherDAO"%>
<%@page import="Models.Voucher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Confirmation</title>
        <!--        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
                integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
                crossorigin="anonymous">
        </script>
        <link rel="stylesheet" href="../css/cart.css">
        <style>
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
            /* Styles for the page layout */
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
        </style>
    </head>
    <body>
        <%
            int userID = (int) request.getSession().getAttribute("userID");
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserData(userID);
            String name = user.getUserName();
        %>

        <div class="container-fluid fullpagecontent">
            <div class="col-md-12">
                <header class="d-flex justify-content-between align-items-center p-3" style="background-color: #D3FFA3; border-bottom: 2px solid black;">
                    <a href="MainPageController" class="d-flex align-items-center text-decoration-none">
                        <span class="h5 ms-2">PAMB</span>
                    </a>
                    <nav class="d-none d-lg-flex gap-4">
                        <a href="/MainPageController" class="nav-link active">Home</a>
                        <a href="#" class="nav-link active">About</a>
                        <a href="#" class="nav-link active">Contact</a>

                    </nav>
                    <div class="d-flex align-items-center gap-3">
                        <div class="avatar-container ms-auto d-flex align-items-center" >
                            <form action="/CartController" method="POST" style="display: inline;">
                                <button type="submit" style="border: none;
                                        background: none;
                                        padding: 0;">
                                    <img src="../img/icon/shopping-cart.svg" alt="Cart" class="cart-icon me-3">
                                </button>
                            </form>

                            <img src="<%= (user.getImgURL() != null && !user.getImgURL().isEmpty()) ? user.getImgURL() : "/img/avt/user.png" %>" alt="Avatar" class="avatar me-2 ms-2" id="avatarButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <!-- me-3 tạo margin-right cho avatar -->

                            <!-- Menu thả xuống -->
                            <div class="dropdown-menu dropdown-menu-left custom-dropdown" aria-labelledby="avatarButton">
                                <a class="user-greeting">Hello, <%= name %></a>
                                <a class="dropdown-item" href="CustomerProfileController">Profile</a>
                                <a class="dropdown-item" href="LogoutController">Logout</a>
                            </div>


                        </div>
                    </div>
                </header>

                <div class="container mt-3">
                    <h2 class="card-title">Order Confirmation</h2>

                    <%
                        double totalPrice = 0; 
                        ArrayList<OrderItem> orderItemsList = (ArrayList<OrderItem>) request.getAttribute("orderItemsList");

                        if (orderItemsList != null && !orderItemsList.isEmpty()) {
                            totalPrice = 0; 
                    %>

                    <table class="table table-bordered table-striped mt-3">
                        <thead class="table-light">
                            <tr>
                                <th scope="col">Product Name</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Price</th>
                                <th scope="col">Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (OrderItem item : orderItemsList) {
                                    double subtotal = item.getPriceAtPurchase() * item.getQuantity();
                                    totalPrice += subtotal;
                            %>
                            <tr>
                                <td><%= item.getProduct().getProductName() %></td>
                                <td><%= item.getQuantity() %></td>
                                <td><%= item.getPriceAtPurchase() %> VND</td>
                                <td><%= subtotal %> VND</td>
                            </tr>
                            <%
}
                            %>
                        </tbody>
                    </table>

                    <div class="mt-3">
                        <strong>Total: <span id="totalPrice"><%= totalPrice %> VND</span></strong>
                    </div>

                    <div class="mt-3">
                        <strong>Voucher:</strong>
                        <select id="voucherSelect" class="form-select" onchange="applyVoucher()">
                            <option value="0" data-discount-type="0" data-discount-value="0">No Voucher</option> <%-- Added data attributes for consistency --%>     
                            <%
                                VoucherDAO voucherDAO = new VoucherDAO();
                                ArrayList<Voucher> vouchers = voucherDAO.getVoucherList(true);
                                if (vouchers != null) {
                                    for (Voucher voucher : vouchers) {
                            %>
                            <option value="<%= voucher.getVoucherID() %>" 
                                    data-discount-type="<%= voucher.isType() ? 1 : 0 %>" 
                                    data-discount-value="<%= voucher.getValue() %>"> 
                                <%= voucher.getVoucherName() %></option>
                                <%
                                    }  
                                    }
                                %>
                        </select>
                    </div>

                    <form action="OrderController" method="post" class="mt-3">  
                        <input type="hidden" name="action" value="createOrder"> 
                        <input type="hidden" name="totalAmount" id="totalAmount" value="<%= totalPrice %>"> 

                        <%-- You can also include hidden input fields for selected items if needed: --%>
                        <%
                        for (OrderItem item : orderItemsList) {
                        %>
                        <input type="hidden" name="productID[]" value="<%= item.getProduct().getProductID() %>">
                        <input type="hidden" name="quantity[]" value="<%= item.getQuantity() %>">
                        <%
                        }
                        %>
                        <input type="hidden" id="voucherIDInput" name="voucherID" value="0"> 

                        <button class="btn btn-primary btn-large" type="submit">Place Order</button>
                    </form>
                    <%
                        } else {
                    %>
                    <p>No Item</p>
                    <%
                        }
                    %>

                    <script>
                        function applyVoucher() {
                            const voucherSelect = document.getElementById("voucherSelect");
                            const selectedVoucher = voucherSelect.options[voucherSelect.selectedIndex];
                            let totalPrice = <%= totalPrice %>;
                            let finalPrice = totalPrice;
                            let voucherID = 0; // Khởi tạo voucherID

                            if (selectedVoucher) {
                                voucherID = selectedVoucher.value;
                                if (voucherID !== "0") {
                                    const discountType = selectedVoucher.getAttribute("data-discount-type");
                                    const discountValue = parseFloat(selectedVoucher.getAttribute("data-discount-value"));

                                    if (discountType === "0") {
                                        finalPrice = totalPrice - discountValue < 0 ? 0 : totalPrice - discountValue;
                                    } else if (discountType === "1") {
                                        finalPrice = totalPrice - (totalPrice * discountValue);
                                    }
                                }
                            }

                            document.getElementById("totalPrice").innerText = finalPrice.toFixed(2) + " VND";
                            document.getElementById("totalAmount").value = finalPrice;
                            document.getElementById("voucherIDInput").value = voucherID;
                        }

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
                    </script>

                </div>
            </div>
        </div>
    </body>
</html>