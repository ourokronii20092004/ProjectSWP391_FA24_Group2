<%-- 
    Document   : productDetails
    Created on : Nov 13, 2024, 9:14:49 AM
    Author     : phanp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
                integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="d-flex flex-column min-vh-100">
            <header class="d-flex justify-content-between align-items-center p-3 border-bottom">
                <a href="homepage.jsp" class="d-flex align-items-center text-decoration-none">

                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">
                    <a href="#" class="text-decoration-none text-muted">Home</a>
                    <a href="#" class="text-decoration-none text-muted">Shop</a>
                    <a href="#" class="text-decoration-none text-muted">About</a>
                    <a href="#" class="text-decoration-none text-muted">Contact</a>
                </nav>
                <div class="d-flex align-items-center gap-3">
                    <a class="nav-link" href="#">Hello, Nguyen Nhat Dang</a>
                    <form action="/CartController" method="POST" style="display: inline;">
                        <button type="submit" style="border: none; background: none; padding: 0;">
                            <img src="../img/icon/shopping-cart.svg" alt="Cart" class="cart-icon">
                        </button>
                    </form>
                    <!--                     <svg xmlns="" width="24" height="24" fill="none" stroke="currentColor"
                                            stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-6 w-6">
                                            <path
                                                d="M12 12c2.5 0 4.5-2 4.5-4.5S14.5 3 12 3 7.5 5 7.5 7.5 9.5 12 12 12zm0 2c-5.5 0-9 2-9 6v2h18v-2c0-4-3.5-6-9-6z" />
                                        </svg> -->
                </div>
            </header>
            <section class="bg-white py-5">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-10 d-flex flex-wrap">
                            <div class="col-lg-6">
                                <img src="https://i.pinimg.com/originals/aa/ed/6e/aaed6e46143374dfa4b1a894c2287957.gif" alt="ecommerce" class="img-fluid rounded border border-secondary">
                            </div>
                            <div class="col-lg-6 pt-4 pt-lg-0">
                                <h2 class="text-secondary text-uppercase">Brand Name</h2>
                                <h1 class="text-dark display-6 mb-3">The Catcher in the Rye</h1>
                                <div class="d-flex align-items-center mb-3">
                                    <div class="d-flex align-items-center">
                                        <i class="bi bi-star-fill text-danger"></i>
                                        <i class="bi bi-star-fill text-danger"></i>
                                        <i class="bi bi-star-fill text-danger"></i>
                                        <i class="bi bi-star-fill text-danger"></i>
                                        <i class="bi bi-star text-danger"></i>
                                        <span class="text-muted ms-2">4 Reviews</span>
                                    </div>
                                    <div class="d-flex ms-3 border-start ps-3">
                                        <a href="https://www.facebook.com/binh.phanphuc.1/" class="text-muted me-2"><i class="bi bi-facebook"></i></a>
                                        <a href="#" class="text-muted me-2"><i class="bi bi-twitter"></i></a>
                                        <a href="#" class="text-muted"><i class="bi bi-chat"></i></a>
                                    </div>
                                </div>
                                <p class="text-muted">
                                    Fam locavore kickstarter distillery. Mixtape chillwave tumeric sriracha taximy chia microdosing tilde DIY. XOXO fam indxgo juiceramps cornhole raw denim forage brooklyn. Everyday carry +1 seitan poutine tumeric. Gastropub blue bottle austin listicle pour-over, neutra jean shorts keytar banjo tattooed umami cardigan.
                                </p>
                                <div class="d-flex align-items-center mt-4 pb-3 border-bottom mb-4">
                                    <div class="me-4">
                                        <span class="me-2">Color</span>
                                        <button class="btn btn-secondary rounded-circle p-2"></button>
                                        <button class="btn bg-dark rounded-circle p-2 ms-1"></button>
                                        <button class="btn bg-danger rounded-circle p-2 ms-1"></button>
                                    </div>
                                    <div class="ms-lg-5">
                                        <span class="me-2">Size</span>
                                        <select class="form-select d-inline w-auto">
                                            <option>SM</option>
                                            <option>M</option>
                                            <option>L</option>
                                            <option>XL</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center">
                                    <span class="h3 text-dark">$58.00</span>
                                    <button class="btn btn-danger ms-auto text-white px-4">Add to Cart</button>
                                    <button class="btn btn-outline-secondary ms-3 rounded-circle p-0">
                                        <i class="bi bi-heart fs-4"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Bootstrap Icons -->
            <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.7.2/font/bootstrap-icons.min.css" rel="stylesheet">


            </body>
            </html>
