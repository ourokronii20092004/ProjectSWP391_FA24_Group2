<%-- 
    Document   : index
    Created on : Oct 15, 2024, 2:09:05 PM
    Author     : phanp
--%>        

<%@page import="java.util.ArrayList"%>
<%@page import="DAOs.ProductDAO"%>
<%@page import="java.sql.ResultSet"%>
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
    </head>

    <body >

        <!-- Logout Modal -->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="d-flex flex-column min-vh-100">
            <header class="d-flex justify-content-between align-items-center p-3 border-bottom">
                <a href="dashboard.jsp" class="d-flex align-items-center text-decoration-none">

                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">
                    <a href="/LogoutController" class="text-decoration-none text-muted">Home</a>
                    <a href="#" class="text-decoration-none text-muted">Shop</a>
                    <a href="#" class="text-decoration-none text-muted">About</a>
                    <a href="#" class="text-decoration-none text-muted">Contact</a>
                </nav>
                <div class="d-flex align-items-center gap-3">
                    <a href="/LoginController" class="btn btn-outline-primary">Login</a>
                    <a href="/RegisterController" class="btn btn-primary">Sign Up</a>
                    <!--                     <svg xmlns="" width="24" height="24" fill="none" stroke="currentColor"
                                            stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-6 w-6">
                                            <path
                                                d="M12 12c2.5 0 4.5-2 4.5-4.5S14.5 3 12 3 7.5 5 7.5 7.5 9.5 12 12 12zm0 2c-5.5 0-9 2-9 6v2h18v-2c0-4-3.5-6-9-6z" />
                                        </svg> -->
                </div>
            </header>

            <div class="p-3">
                <form class="d-flex align-items-center">
                    <input type="search" class="form-control me-2" placeholder="Search products..." aria-label="Search">
                    <button class="btn btn-outline-secondary" type="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor"
                             stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4">
                        <circle cx="11" cy="11" r="8" />
                        <path d="m21 21-4.3-4.3" />
                        </svg>
                        <span class="visually-hidden">Search</span>
                    </button>
                </form>
            </div>

            <main class="d-flex flex-column flex-lg-row gap-4 p-4">
                <aside class="w-100 w-lg-25 filter-section">
                    <h2 class="h6 mb-3">Filters</h2>
                    <div class="mb-4">
                        <h3 class="h6">Categories</h3>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="clothing">
                            <label class="form-check-label" for="clothing">Clothing</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="electronics">
                            <label class="form-check-label" for="electronics">Electronics</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="home">
                            <label class="form-check-label" for="home">Home</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="beauty">
                            <label class="form-check-label" for="beauty">Beauty</label>
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
                <section class="product-section container" id="product-section">
                    <div class="row">
                        <%
                            ProductDAO productDao = new ProductDAO();
                            ArrayList<Models.Product> list = productDao.viewProductList();
                            for (Models.Product pro : list) {
                        %>
                        <div class="col-md-4 col-sm-6 mb-4">
                            <div class="card h-100">
                                <img src="<%= pro.getImageURL()%>" alt="Product Image" class="card-img-top">
                                <div class="card-body text-center">
                                    <a href="#"><h3 class="h5 card-title"><%= pro.getProductName()%></h3></a>
                                    <p class="text-muted">$<%= pro.getPrice()%></p>
                                    <!-- Form để thêm sản phẩm vào giỏ hàng -->
                                    <form action="" method="post">
                                        <a href="#" class="text-decoration-none text-muted">Home</a>
                                        <input type="hidden" name="action" value="add">
                                        <input type="hidden" name="productID" value="<%= pro.getProductID()%>">
                                        <input type="number" name="quantity" value="1" min="1" class="form-control mb-2" style="width: 60px; margin: 0 auto;">                                        
                                        <a href="/LoginController" class="btn btn-primary">Add to Cart</a>
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
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
    crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
    crossorigin="anonymous"></script>
    <script>
        $(document).ready(function ()   
        {
            var urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get('logout') === 'true') {
                $('#logoutModal').modal('show');
            }
        });
    </script>
</html>

