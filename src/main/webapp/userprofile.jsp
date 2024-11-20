<%-- 
Document   : userprofile
Created on : Oct 20, 2024, 11:10:21 AM
Author     : phanp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>

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

        <!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
                <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>-->

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
                <a href="/MainPageController" class="d-flex align-items-center justify-content-center text-decoration-none">

                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">
                    <a href="/MainPageController" class="nav-link active">Home</a>
                    <a href="#" class="nav-link active">About</a>
                    <a href="#" class="nav-link active">Contact</a>
                </nav>
                <%
    int userID = (int) request.getSession().getAttribute("userID");
    UserDAO userDAO = new UserDAO();
                            
    User user = userDAO.getUserData(userID);
    String name = user.getUserName();
                %>
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

            <!-- Main Content -->
            <div class="container my-5">
                <div class="row">
                    <!-- Sidebar -->
                    <div class="col-md-4 mb-4">
                        <!-- Avatar Section -->
                        <div class="text-center mb-4" >
                            <form id="avtForm" action="/CustomerProfileController" method="post" enctype="multipart/form-data">
                                <input hidden="" name="userID" value="<%= user.getId() %>">
                                <img id="avatarPreview" src="<%= (user.getImgURL() != null && !user.getImgURL().isEmpty()) ? user.getImgURL() : "/img/avt/user.png" %>" alt="Avatar" class="rounded-circle border border-primary" width="150" height="150">
                                <div class="text-center">
                                    <label for="avatarInput" class="btn btn-outline-secondary mt-3 mb-2" id="changeAvatarLabel">Change Avatar</label>
                                    <input type="file" id="avatarInput" name="changeAvatarButton" accept="image/*" style="display: none;">
                                    <button type="submit" class="btn btn-success mt-3 mb-2" id="saveAvatarButton" name="saveAvatarButton" style="display: none;">Save Avatar</button>
                                </div>
                            </form>
                        </div>

                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white">
                                Account
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><a href="/PasswordController?action=changePassword" style="text-decoration: none;" class="text-dark">Change Password</a></li>
                                <li onclick="showBoughtHistory()" class="list-group-item"><a href="#" style="text-decoration: none;" class="text-dark">History Bought</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- Profile Form -->
                    <div class="col-md-8" >
                        <h3 class="mb-4 text-center">Account Information</h3>

                        <form id="profileForm" class="p-4 bg-white rounded shadow-lg" action="/CustomerProfileController" method="post">

                            <div class ="row">
                                <input hidden="" name="userID" value="<%= user.getId() %>">
                                <div class="col-md-6 mb-3">
                                    <label for="name" class="form-label">First Name:</label>
                                    <input type="text" class="form-control" name="editFirstName" id="editFirstName" placeholder="Your First Name" value="<%= user.getFirstName() %>" required="" readonly>
                                </div>
                                <div class="mb-3 col-md-6">
                                    <label for="surname" class="form-label">Last Name:</label>
                                    <input type="text" class="form-control" name="editLastName" id="editLastName" placeholder="Your Last Name" value="<%= user.getLastName() %>" required="" readonly>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email:</label>
                                <input type="email" class="form-control" name="editEmail" id="editEmail" value="<%= user.getEmail() %>" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="phone" class="form-label">Phone Number:</label>
                                <input type="tel" maxlength="11" minlength="10" class="form-control" name="editPhoneNumber" id="editPhoneNumber" placeholder="Your Phone Number" value="<%= user.getPhoneNumber() %>"  required="" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address:</label>
                                <input type="text" class="form-control" name="editAddress" id="editAddress" name="" placeholder="Your Address" value="<%= user.getAddress() %>" required="" readonly>
                            </div>

                            <button type="button" class="btn btn-primary w-100" id="editButton" name="editButton">Edit</button>
                            <button type="submit" class="btn btn-success w-100 mt-3" id="saveButton"  name="saveButton" style="display:none;">Save</button>
                        </form>
                    </div>
                </div>
            </div> 

            <!-- Order History Management Section -->
            <div  id="BoughtHistory" hidden="" class="row container my-5">
                <div class="col-md-3"></div>
                <div class=" col-md-8">
                    <div class="row">

                        <table class="table table-striped">                            
                            <tr>                            
                                <th class="col-md-2">Order Items</th>
                                <th class="col-md-2">Price</th>
                                <th class="col-md-1">Status</th>
                                <th class="col-md-2">Order date</th>
                                <th class="col-md-2">Action</th>
                            </tr>
                            <c:forEach items="${boughtHistory}" var="o">  
                          
                                    <tr>
                                        <td class="col-md-2">
                                            <c:forEach items="${o.orderItemList}" var="ot">                                            
                                                ${ot.product.productName} x${ot.quantity}<br>
                                            </c:forEach>
                                        </td>
                                        <td class="col-md-2">${o.totalAmount}</td>
                                        <td class="col-md-1">${o.orderStatus}</td>
                                        <td class="col-md-2">${o.orderDate}</td>
                                        <td class="col-md-2"><a href="/OrderDetailController?orderID=${o.orderID}" class="btn btn-secondary">Detail</a></td>
                                    </tr>  
                                
                            </c:forEach>                               
                        </table>
                    </div>
                </div>
                <div class="col-md-3"></div>
            </div>


            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const editButton = document.getElementById("editButton");
                    const saveButton = document.getElementById("saveButton");
                    const formFields = document.querySelectorAll("input[readonly]");
                    const avatarInput = document.getElementById("avatarInput");
                    const saveAvatarButton = document.getElementById("saveAvatarButton");
                    const avatarPreview = document.getElementById("avatarPreview");
                    // Enable editing on clicking "Edit"
                    editButton.addEventListener("click", function () {
                        formFields.forEach(field => {
                            if (field.id !== "editEmail") { // Keep email readonly
                                field.removeAttribute("readonly");
                            }
                        });
                        editButton.style.display = "none";
                        saveButton.style.display = "block";
                    });


                    avatarInput.addEventListener("change", function (event) {
                        if (avatarInput.files && avatarInput.files[0]) {

                            const reader = new FileReader();
                            reader.onload = function (e) {
                                avatarPreview.src = e.target.result; // Cập nhật hình ảnh xem trước
                            };
                            reader.readAsDataURL(avatarInput.files[0]);
                            saveAvatarButton.style.display = "inline-block"; // Hiển thị nút Save Avatar
                        }
                    });

                });

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




                function showBoughtHistory() {
                    if (document.getElementById("BoughtHistory").hasAttribute("hidden")) {
                        document.getElementById("BoughtHistory").removeAttribute("hidden");
                    } else {
                        document.getElementById("BoughtHistory").setAttribute("hidden", "");
                    }
                }
            </script>
    </body>
</html>