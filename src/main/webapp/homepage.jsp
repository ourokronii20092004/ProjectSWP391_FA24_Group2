<%-- 
    Document   : index
    Created on : Oct 15, 2024, 2:09:05 PM
    Author     : phanp
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PAMB HomePage</title>

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
            .card-img-top {
                width: 100%; /* Chiều rộng chiếm toàn bộ khung card */
                height: 400px; /* Chiều cao cố định */
                object-fit: cover; /* Giữ tỷ lệ, cắt phần dư */
                object-position: top; /* Ưu tiên phần trên của ảnh */
                border-radius: 8px; /* Tùy chọn: Bo góc ảnh */
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

    <body >
        <div class="d-flex flex-column min-vh-100">
            <header class="d-flex justify-content-between align-items-center p-3" style="background-color: #D3FFA3; border-bottom: 2px solid black;">
                <a href="/MainPageController" class="d-flex align-items-center justify-content-center text-decoration-none">

                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">

                    <a href="/MainPageController" class="nav-link active">Home</a>
                    <a href="#" class="nav-link active">About</a>
                    <a href="#" class="nav-link active">Contact</a>
                    <a href="#" class="nav-link active">Vouchers</a>

                </nav>
                <%
    int userID = (int) request.getSession().getAttribute("userID");
    UserDAO userDAO = new UserDAO();
                            
    User user = userDAO.getUserData(userID);
    String name = user.getUserName();
                %>
                <div class="d-flex align-items-center gap-3">
                    <div class="avatar-container ms-auto d-flex align-items-center" >

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

            <!-- Hiển thị thông báo thành công -->
            <%-- Success Message Alert --%>
            <c:if test="${not empty successMessage}">
                <div id="successAlert" class="alert alert-success text-center" role="alert">
                    ${successMessage}

                </div>
                <script>
                    // Hide the success message after 5 seconds
                    setTimeout(function () {
                        document.getElementById('successAlert').style.display = 'none';
                    <%
                                        // Clear the successMessage in the session or request scope
                                        request.setAttribute("successMessage", null);
                    %>
                    }, 5000);
                </script>
            </c:if>
            <div class="p-3">
                <form class="d-flex align-items-center justify-content-center">
                    <input type="text" id="search-input" onkeyup="searchFunction()" class="form-control me-2 w-50 border-3" placeholder="Search products..." aria-label="Search">
                    <button class="btn btn-outline-secondary" type="button" onclick="searchFunction()">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor"
                             stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-5 w-4">
                        <circle cx="11" cy="11" r="8" />
                        <path d="m21 21-4.3-4.3" />
                        </svg>
                        <span class="visually-hidden">Search</span>
                    </button>
                </form>
            </div>



            <main class="d-flex p-4">

                <aside class="col-md-2">
                    <h2 class="h6 mb-3">Filters</h2>
                    <div class="mb-4">
                        <h3 class="h6">Categories</h3>
                        <div class="form-check">
                            <input class="form-check-input category-checkbox" type="checkbox" name="category" value="1" id="category1"> <%-- Changed to checkbox --%>
                            <label class="form-check-label" for="Stem">Stem</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input category-checkbox" type="checkbox" name="category" value="2" id="category2"> <%-- Changed to checkbox --%>
                            <label class="form-check-label" for="Bouquet">Bouquet</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input category-checkbox" type="checkbox" name="category" value="3" id="category3"> <%-- Changed to checkbox --%>
                            <label class="form-check-label" for="Basket">Basket</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input category-checkbox" type="checkbox" name="category" value="4" id="category4"> <%-- Changed to checkbox --%>
                            <label class="form-check-label" for="Pot">Pot</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input category-checkbox" type="checkbox" name="category" value="5" id="category5"> <%-- Changed to checkbox --%>
                            <label class="form-check-label" for="Combo">Combo</label>
                        </div>
                    </div>
                </aside>
                <section class="col-md-10" id="product-section">
                    <div class="row">
                        <%
                            ProductDAO productDao = new ProductDAO();
ArrayList<Models.Product> list = productDao.viewProductList();
                            for (Models.Product pro : list) {
                        %>
                        <div class="col-md-4 col-sm-6 mb-4 product-item" data-name="<%= pro.getProductName().toLowerCase() %>" data-category="<%= pro.getCategoryID() %>">
                            <div class="card h-100">
                                <img src="<%= pro.getImageURL()%>" alt="Product Image" class="card-img-top">
                                <div class="card-body text-center">

                                    <form action="/RatingController" method="GET" class="d-inline">
                                        <input type="hidden" name="action" value="list">
                                        <input type="hidden" name="productID" value="<%= pro.getProductID()%>">
                                        <button type="submit" class="btn btn-link p-0" style="text-decoration: none;">
                                            <h3 class="h5 card-title"><%= pro.getProductName() %></h3>
                                        </button>
                                    </form>

                                    <p class="text-muted">$<%= pro.getPrice()%></p>
                                    <!-- Form để thêm sản phẩm vào giỏ hàng -->
                                    <form action="/CartController" method="post">
                                        <input type="hidden" name="action" value="add">
                                        <input type="hidden" name="productID" value="<%= pro.getProductID()%>">
                                        <input type="number" name="quantity" value="1" min="1" class="form-control mb-2" style="width: 60px;
                                               margin: 0 auto;">
                                        <button type="submit" class="btn btn-primary">Add to Cart</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </section>
            </main>
        </div>
    </body>
    <script>
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

        function searchFunction() {
            const input = document.getElementById('search-input').value.toLowerCase();
            const productItems = document.querySelectorAll('.product-item');

            productItems.forEach(item => {
                const productName = item.getAttribute('data-name');
                if (productName.includes(input)) {
                    item.style.display = '';
                } else {
                    item.style.display = 'none';
                }
            });
        }
        function filterProductsByCategory() {
            const checkedCategories = Array.from(document.querySelectorAll('input[name="category"]:checked')).map(checkbox => checkbox.value);
            const productItems = document.querySelectorAll('.product-item');

            productItems.forEach(item => {
                const productCategory = item.getAttribute('data-category');
                if (checkedCategories.length === 0 || checkedCategories.includes(productCategory)) { // Hiển thị tất cả nếu không có checkbox nào được chọn
                    item.style.display = '';
                } else {
                    item.style.display = 'none';
                }
            });
        }

        const categoryCheckboxes = document.querySelectorAll('input[name="category"]');
        categoryCheckboxes.forEach(checkbox => {
            checkbox.addEventListener('change', filterProductsByCategory);
        });
    </script>
</html>