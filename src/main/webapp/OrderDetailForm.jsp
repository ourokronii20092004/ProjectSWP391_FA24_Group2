<%-- 
    Document   : OrderDetailForm
    Created on : Nov 10, 2024, 2:55:24 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Employee Management</title>
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
            .orderTitle{
                font-size: 50px;
            }
            .headOrder{
                font-weight: bold;
                font-size: 20px
            }
        </style>
    </head>
    <body>
        <%
  int userID = (int) request.getSession().getAttribute("userID");
  UserDAO userDAO = new UserDAO();
                            
  User user = userDAO.getUserData(userID);
  String name = user.getUserName();
  if(user.getRoleID() == 2){
        %>
        <!-- Navigation Bar -->
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
                <div class="d-flex align-items-center gap-3">
                    <div class="avatar-container ms-auto d-flex align-items-center">

                        <div class="nav-item">
                            <a class="nav-link bell-link position-relative me-2" style="padding-bottom: 5px;" href="#"
                               onclick="parent.location.href = this.href; return false;">
                                <img src="img/icon/bell.svg" alt="Notification" class="bell-icon">
                            </a>
                        </div>

                        <form action="/CartController" method="POST" style="display: inline;">
                            <button type="submit" style="border: none;
                                    background: none;
                                    padding: 0;">
                                <img src="../img/icon/shopping-cart.svg" alt="Cart" class="cart-icon me-3">
                            </button>
                        </form>

                        <img src="<%= user.getImgURL() %>" alt="Avatar" class="avatar me-2 ms-2" id="avatarButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="margin-left: 15px;"> <!-- me-3 tạo margin-right cho avatar -->

                        <!-- Menu thả xuống -->
                        <div class="dropdown-menu dropdown-menu-left custom-dropdown" aria-labelledby="avatarButton">
                            <a class="user-greeting">Hello, <%= name %></a>
                            <a class="dropdown-item" href="/CustomerProfileController">Profile</a>
                            <a class="dropdown-item" href="LogoutController">Logout</a>
                        </div>
                    </div>
                </div>
            </header>
            <%
                }else{
            %>
            <iframe src="adminNavbar.jsp" height="60px"></iframe>
                <%
                    }
                %>
            <!-- Order Form -->
            <br><br>
            <div class="container align-items-center">

                <div class="card ">
                    <div class="text-center orderTitle">Order Detail</div>
                    <br>
                    <c:set var="order" value="${requestScope.order}"></c:set>
                        <div class="row">
                            <div>          
                                <div class="row align-items-center headOrder">
                                    <div class="col-md-3 align-items-center">                             
                                        ID: ${order.orderID}                         
                                </div>
                                <div class="col-md-3 align-items-center">
                                    Customer: ${order.user.userName}
                                </div>
                                <div class="col-md-3 align-items-center">
                                    Customer: ${order.orderDate}
                                </div>
                            </div>
                            <br><br>
                            <table class="table table-striped" id="empList">                                         
                                <tr>
                                    <th class="col-md-1">Product</th>
                                    <th class="col-md-1">Quantity</th>
                                    <th class="col-md-1">Price</th>
                                    <th class="col-md-1">Total</th>
                                </tr>   

                                <c:set var="totalPrice" value="${0}"></c:set>
                                <c:forEach items="${requestScope.order.orderItemList}" var="o">                                   
                                    <tr>
                                        <td class="col-md-1">${o.product.productName}</td>                                       
                                        <td class="col-md-1">${o.quantity}</td>
                                        <td class="col-md-1">${o.priceAtPurchase}</td>  
                                        <c:set var="total" value="${o.quantity * o.priceAtPurchase}"></c:set>
                                        <td class="col-md-1">${total} VND</td>  
                                        <c:set var="totalPrice" value="${total + totalPrice}"></c:set>
                                        </tr>                          
                                </c:forEach>
                                <tr>
                                    <th class="col-md-1"></th> 
                                    <th class="col-md-1"></th>  
                                    <th class="col-md-1">Total price</th>   
                                    <td class="col-md-1"> ${totalPrice} VND</td>   
                                </tr>
                                <tr>
                                    <c:set var="voucher" value="${order.voucher}"></c:set>
                                        <th class="col-md-1"></th> 
                                        <th class="col-md-1">Voucher code</th>  

                                    <c:choose> 
                                        <c:when test="${voucher != null}">
                                            <th class="col-md-1">${voucher.voucherCode}</th>  
                                                <c:choose> 
                                                    <c:when test="${voucher.type == true}">
                                                    <th class="col-md-1">${voucher.value}% off</th>   
                                                    </c:when>
                                                    <c:when test="${voucher.type == false}">
                                                        <c:choose>
                                                            <c:when test="${totalPrice < voucher.value}">
                                                            <th class="col-md-1">- ${totalPrice} VND</th>   
                                                            </c:when>
                                                            <c:otherwise>
                                                            <th class="col-md-1">- ${voucher.value} VND</th>  
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>                                              
                                                </c:choose> 
                                            </c:when>
                                            <c:otherwise>
                                            <td class="col-md-1"></td>   
                                            <td class="col-md-1">- 0 VND</td>   
                                        </c:otherwise>
                                    </c:choose>

                                </tr>
                                <tr>
                                    <th class="col-md-1"></th> 
                                    <th class="col-md-1"></th>  
                                    <th class="col-md-1">Actual price</th>   
                                        <c:choose> 
                                            <c:when test="${voucher.type == true}">
                                            <th class="col-md-1">${totalPrice - (voucher.value * totalPrice / 100)} VND</th>   
                                            </c:when>
                                            <c:when test="${voucher.type == false}">
                                                <c:choose>
                                                    <c:when test="${totalPrice < voucher.value}">
                                                    <th class="col-md-1">0 VND</th>   
                                                    </c:when>
                                                    <c:otherwise>
                                                    <th class="col-md-1">${totalPrice - voucher.value} VND</th>   
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when> 
                                            <c:otherwise>
                                            <td class="col-md-1">${totalPrice} VND</td>  
                                        </c:otherwise>
                                    </c:choose>                              
                                </tr>
                            </table>                      
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
</html>
