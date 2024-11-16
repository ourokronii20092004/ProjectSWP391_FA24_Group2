<%-- 
    Document   : index
    Created on : Oct 15, 2024, 2:09:05 PM
    Author     : phanp
--%>        

<%@page import="java.util.ArrayList"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PAMB Guest</title>
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

            .custom-dropdown.show {
                display: block; /* Hiển thị menu khi có class 'show' */
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
            <header class="d-flex justify-content-between align-items-center p-3 border-bottom" style="background-color: #FFCCCC;">
                <a href="/MainPageController" class="d-flex align-items-center text-decoration-none">

                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">
                    <a href="/MainPageController"class="nav-link active">Home</a>
                    <a href="#" class="nav-link active">About</a>
                    <a href="#" class="nav-link active">Contact</a>
                    <a href="#" class="nav-link active">Vouchers</a>
                </nav>

                <div class="d-flex align-items-center gap-3">
                    <a href="/LoginController" class="btn btn-outline-primary">Login</a>
                    <a href="/RegisterController" class="btn btn-primary">Sign Up</a>
                </div>


            </header>

            <div class="p-3">
                <form class="d-flex align-items-center justify-content-center">
                    <input type="search" class="form-control me-2 w-50 border-3" placeholder="Search products..." aria-label="Search">
                    <button class="btn btn-outline-secondary" type="submit">
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
                            <input class="form-check-input" type="checkbox" id="clothing">
                            <label class="form-check-label" for="clothing">Flowers For The Occasion</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="electronics">
                            <label class="form-check-label" for="electronics">Flowers 11/20</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="home">
                            <label class="form-check-label" for="home">Flowers By Bouquet</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="beauty">
                            <label class="form-check-label" for="beauty">Flower Of Visitation</label>
                        </div>
                    </div>
                    <div class="mb-4">
                        <h3 class="h6">Price Range</h3>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="under-25">
                            <label class="form-check-label" for="under-25">$25 and under</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="25-50">
                            <label class="form-check-label" for="25-50">$25 - $50</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="50-100">
                            <label class="form-check-label" for="50-100">$50 - $100</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="100-200">
                            <label class="form-check-label" for="100-200">$100 - $200</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="200-plus">
                            <label class="form-check-label" for="200-plus">$200 and above</label>
                        </div>
                    </div>
                    <div class="mb-4">
                        <h3 class="h6">Sort By</h3>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="sort" id="featured" value="featured" checked>
                            <label class="form-check-label" for="featured">Featured</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="sort" id="low-to-high" value="low-to-high">
                            <label class="form-check-label" for="low-to-high">Price: Low to High</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="sort" id="high-to-low" value="high-to-low">
                            <label class="form-check-label" for="high-to-low">Price: High to Low</label>
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
                        <div class="col-md-4 col-sm-6 mb-4">
                            <div class="card h-100">
                                <img src="https://i.pinimg.com/originals/aa/ed/6e/aaed6e46143374dfa4b1a894c2287957.gif" alt="Product Image" class="card-img-top">
                                <div class="card-body text-center">
                                    <form action="/RatingController" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="list">
                                        <input type="hidden" name="productID" value="<%= pro.getProductID() %>">
                                        <button type="submit" class="btn btn-link p-0" style="text-decoration: none;">
                                            <h3 class="h5 card-title"><%= pro.getProductName() %></h3>
                                        </button>
                                    </form>
                                    <p class="text-muted">$<%= pro.getPrice()%></p>
                                    <!-- Form để thêm sản phẩm vào giỏ hàng -->
                                    <form action="" method="post">
                                        <a href="#" class="text-decoration-none text-muted">Home</a>
                                        <input type="hidden" name="action" value="add">
                                        <input type="hidden" name="productID" value="<%= pro.getProductID()%>">
                                        <input type="number" name="quantity" value="1" min="1" class="form-control mb-2" style="width: 60px; margin: 0 auto;">                                        
                                        <a href="/RegisterController" class="btn btn-primary">Add to Cart</a>
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
</html>

