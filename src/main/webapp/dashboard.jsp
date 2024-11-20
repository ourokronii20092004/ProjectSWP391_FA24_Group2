<%-- 
    Document   : dashboard
    Created on : Oct 20, 2024, 2:51:52 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>DashBoard | PAMP</title>
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

            .error-text {
                color: red;
            }

            .product-image {
                width: 50px;
                height: 50px;
                object-fit: cover;
            }
        </style>
    </head>

    <body>
        <iframe src="adminNavbar.jsp" height="60px"></iframe>
        <div class="container-fluid fullpagecontent">
            <div class="row">
                <!-- Order Management Section -->
                <div class="col-md-12 card-container">
                    <h6 class="card-title">Order Management</h6>
                    <div class="card">
                        <div class="row">
                            <div class="col-md-12">
                                <table class="table table-striped">                            
                                    <tr>
                                        <th class="col-md-1">ID</th>
                                        <th class="col-md-1">Username</th>
                                        <th class="col-md-1">Full name</th>
                                        <th class="col-md-1">Price</th>
                                        <th class="col-md-1">Status</th>
                                        <th class="col-md-1">Order date</th>
                                        <th class="col-md-1">Action</th>
                                    </tr>
                                    <c:forEach items="${orderList}" var="o">                               
                                        <tr>
                                            <td class="col-md-1">${o.orderID}</td>
                                            <td class="col-md-1">${o.user.userName}</th>
                                            <td class="col-md-1">${o.user.firstName} ${o.user.lastName}</td>
                                            <td class="col-md-1">${o.totalAmount}</td>
                                            <td class="col-md-1">${o.orderStatus}</td>
                                            <td class="col-md-1">${o.orderDate}</td>
                                            <td class="col-md-1"><a href="/OrderDetailController?orderID=${o.orderID}" class="btn btn-secondary">Detail</a></td>
                                        </tr>  
                                    </c:forEach>                               
                                </table>
                            </div>                        
                        </div>
                    </div>
                </div>          
            </div>
        </div>
        <footer>
            <iframe src="adminFooter.jsp" height="50px"></iframe>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>

    </body>
</body>
</html>