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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/cart.css">
        <style>
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
                <header class="d-flex justify-content-between align-items-center p-3 border-bottom">
                    <a href="#" class="d-flex align-items-center text-decoration-none">
                        <span class="h5 ms-2">PAMB</span>
                    </a>
                    <nav class="d-none d-lg-flex gap-4">
                        <a href="homepage.jsp" class="nav-link active">Home</a>
                        <a href="#" class="nav-link active">Shop</a>
                        <a href="#" class="nav-link active">About</a>
                        <a href="#" class="nav-link active">Contact</a>
                    </nav>
                    <div class="d-flex align-items-center gap-3">
                        <a class="nav-link" href="#">Hello, <%= name %></a>
                    </div>
                </header>

                <div class="container">
                    <h2 class="card-title">Order Confirmation</h2>

                    <%
                        ArrayList<OrderItem> orderItemsList = (ArrayList<OrderItem>) request.getAttribute("orderItemsList");
                        int voucherID = (Integer) request.getAttribute("voucherID");

                        if (orderItemsList != null && !orderItemsList.isEmpty()) {
                            double totalPrice = 0; 
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

                        </select>
                    </div>

                    <form action="OrderController" method="post" class="mt-3">  <%-- Corrected form action --%>
                        <input type="hidden" name="action" value="createOrder"> <%-- Use a more specific action name --%>
                        <input type="hidden" name="totalAmount" value="<%= totalPrice %>"> <%-- Send total amount --%>

                        <%-- You can also include hidden input fields for selected items if needed: --%>
                        <%
                        for (OrderItem item : orderItemsList) {
                        %>
                        <input type="hidden" name="productID[]" value="<%= item.getProduct().getProductID() %>">
                        <input type="hidden" name="quantity[]" value="<%= item.getQuantity() %>">
                        <%
                        }
                        %>

                        <input type="hidden" id="voucherIDInput" name="voucherID" value="0"> <%-- Initialize voucherID to 0 --%>
                        <button class="btn btn-primary btn-large" type="submit">Place Order</button>
                    </form>
                    <%
                        } else {
                    %>
                    <p>Không có mặt hàng nào trong đơn hàng.</p>
                    <%
                        }
                    %>

                    <script>
                        function applyVoucher() {
                            var voucherSelect = document.getElementById("voucherSelect");
                            var selectedVoucher = voucherSelect.options[voucherSelect.selectedIndex];
                            var totalPrice;
                            var finalPrice = totalPrice;

                            if (selectedVoucher.value !== "0") {
                                var discountType = selectedVoucher.getAttribute("data-discount-type");
                                var discountValue = parseFloat(selectedVoucher.getAttribute("data-discount-value"));

                                if (discountType === "0") {  // Fixed amount discount
                                    finalPrice = totalPrice - discountValue;
                                } else if (discountType === "1") {  // Percentage discount
                                    finalPrice = totalPrice * (1 - discountValue / 100);
                                }
                            }

                            document.getElementById("totalPrice").innerText = finalPrice.toFixed(2) + " VND";
                            document.getElementById("finalPriceInput").value = finalPrice.toFixed(2);
                            document.getElementById("voucherIDInput").value = selectedVoucher.value; // Update voucher ID in hidden input
                        }
                    </script>

                </div>
            </div>
        </div>
    </body>
</html>