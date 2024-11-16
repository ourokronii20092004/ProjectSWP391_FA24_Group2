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
        <style>
            /* Định dạng ảnh avatar */
            .avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                cursor: pointer;
                border: 2px solid #ddd;
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
            .avatar-profile-container {
                position: relative;
                width: 150px;
                height: 150px;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .avatar-profile-container img {
                width: 100%;
                height: 100%;
                border-radius: 50%;
                object-fit: cover;
            }

            .overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.6); /* Độ mờ cho lớp phủ */
                display: flex;
                justify-content: center;
                align-items: center;
                border-radius: 50%;
                opacity: 0;
                transition: opacity 0.3s;
            }

            .avatar-profile-container:hover .overlay {
                opacity: 1; /* Hiện overlay khi di chuột */
            }

            .overlay button {
                color: white;
                border-color: white;
            }
        </style>
    </head>

    <body>
        <!-- Navigation Bar -->
        <div class="d-flex flex-column min-vh-100">
            <header class="d-flex justify-content-between align-items-center p-3 border-bottom">
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

                        <img src="https://i.pinimg.com/originals/01/bd/c8/01bdc83a37e5f1b9abab0dbe535fdeae.gif" alt="Avatar" class="avatar me-2 ms-2" id="avatarButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="margin-left: 15px;"> <!-- me-3 tạo margin-right cho avatar -->

                        <!-- Menu thả xuống -->
                        <div class="dropdown-menu dropdown-menu-left custom-dropdown" aria-labelledby="avatarButton">
                            <a class="user-greeting">Hello, <%= name %></a>
                            <a class="dropdown-item" href="/CustomerProfileController">Profile</a>
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
                        <div class="text-center mb-4 avatar-profile-container" style="margin-left:140px;">
                            <img id="avatarPreview" src="https://i.pinimg.com/originals/01/bd/c8/01bdc83a37e5f1b9abab0dbe535fdeae.gif" alt="Avatar" class="rounded-circle border border-primary" width="150" height="150">
                            <div class="overlay">
                                <button type="button" class="btn btn-outline-secondary" id="changeAvatarButton">Change Avatar</button>
                                <input type="file" id="avatarInput" accept="image/*" style="display: none;">
                            </div>
                        </div>

                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white">
                                Account
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><a href="#" style="text-decoration: none;" class="text-dark">Change Password</a></li>
                                <li onclick="showBoughtHistory()" class="list-group-item"><a href="#" style="text-decoration: none;" class="text-dark">History Bought</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- Profile Form -->
                    <div class="col-md-8">
                        <h3 class="mb-4 text-center">Account Information</h3>
                        <form id="profileForm" class="p-4 bg-white rounded shadow-lg">
                            <div class ="row">
                                <div class="col-md-6 mb-3">
                                    <label for="name" class="form-label">First Name:</label>
                                    <input type="text" class="form-control" id="firstname" value="<%=user.getFirstName()%>" readonly>
                                </div>
                                <div class="mb-3 col-md-6">
                                    <label for="surname" class="form-label">Last Name:</label>
                                    <input type="text" class="form-control" id="lastname" value="<%=user.getLastName()%>" readonly>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email:</label>
                                <input type="email" class="form-control" id="email" value="<%=user.getEmail()%>" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="phone" class="form-label">Phone Number:</label>
                                <input type="tel" class="form-control" id="phone" value="<%=user.getPhoneNumber()%>" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address:</label>
                                <input type="text" class="form-control" id="address" value="<%=user.getAddress()%>" readonly>
                            </div>

                            <button type="button" class="btn btn-primary w-100" id="editButton">Edit</button>
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
                    const changeAvatarButton = document.getElementById("changeAvatarButton");
                    const avatarInput = document.getElementById("avatarInput");
                    const avatarPreview = document.getElementById("avatarPreview");
                    // Enable editing on clicking "Edit"
                    editButton.addEventListener("click", function () {
                        formFields.forEach(field => {
                            if (field.id !== "email") { // Keep email readonly
                                field.removeAttribute("readonly");
                            }
                        });
                        editButton.style.display = "none";
                        saveButton.style.display = "block";
                    });
                    // Change Avatar Button
                    changeAvatarButton.addEventListener("click", function () {
                        avatarInput.click(); // Trigger file input when button is clicked
                    });
                    // Preview the new avatar when user selects an image
                    avatarInput.addEventListener("change", function () {
                        const file = avatarInput.files[0];
                        if (file) {
                            const reader = new FileReader();
                            reader.onload = function (e) {
                                avatarPreview.src = e.target.result; // Set the preview image source
                            }
                            reader.readAsDataURL(file); // Read the image file as a data URL
                        }
                    });
                    // Show confirmation message on submitting the form
                    const form = document.getElementById("profileForm");
                    form.addEventListener("submit", function (e) {
                        e.preventDefault(); // Prevent the form from submitting immediately
                        alert("Profile updated successfully!");
                        // Optionally submit the form here using AJAX
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
                        document.getElementById("BoughtHistory").setAttribute("hidden","");
                    }
                }
            </script>
    </body>

</html>