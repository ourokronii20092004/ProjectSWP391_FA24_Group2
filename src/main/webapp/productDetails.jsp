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
    </head>
    <body>
        <section class="text-secondary bg-white">
            <div class="container py-5">
                <div class="row gx-5">
                    <!-- Image Section -->
                    <div class="col-lg-6 mb-4 mb-lg-0">
                        <img src="https://www.whitmorerarebooks.com/pictures/medium/2465.jpg" alt="ecommerce" class="img-fluid rounded border border-secondary">
                    </div>

                    <!-- Content Section -->
                    <div class="col-lg-6">
                        <h2 class="text-muted small">BRAND NAME</h2>
                        <h1 class="text-dark display-5 fw-medium">The Catcher in the Rye</h1>

                        <!-- Rating and Social Icons -->
                        <div class="d-flex align-items-center mb-3">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-star-fill text-danger"></i>
                                <i class="bi bi-star-fill text-danger"></i>
                                <i class="bi bi-star-fill text-danger"></i>
                                <i class="bi bi-star-fill text-danger"></i>
                                <i class="bi bi-star text-danger"></i>
                                <span class="text-secondary ms-2">4 Reviews</span>
                            </div>
                            <div class="ms-3 ps-3 border-start">
                                <a href="#" class="text-secondary me-2"><i class="bi bi-facebook"></i></a>
                                <a href="#" class="text-secondary me-2"><i class="bi bi-twitter"></i></a>
                                <a href="#" class="text-secondary"><i class="bi bi-instagram"></i></a>
                            </div>
                        </div>

                        <p class="lead">Fam locavore kickstarter distillery. Mixtape chillwave tumeric sriracha taximy chia microdosing tilde DIY. XOXO fam indxgo juiceramps cornhole raw denim forage brooklyn. Everyday carry +1 seitan poutine tumeric. Gastropub blue bottle austin listicle pour-over, neutra jean shorts keytar banjo tattooed umami cardigan.</p>

                        <!-- Color and Size Selection -->
                        <div class="d-flex align-items-center mb-4 pb-3 border-bottom">
                            <!-- Color Selection -->
                            <div class="d-flex align-items-center me-4">
                                <span class="me-2">Color</span>
                                <button class="btn border-secondary rounded-circle p-2 me-1" style="width: 24px; height: 24px;"></button>
                                <button class="btn border-secondary bg-secondary rounded-circle p-2 me-1" style="width: 24px; height: 24px;"></button>
                                <button class="btn border-secondary bg-danger rounded-circle p-2" style="width: 24px; height: 24px;"></button>
                            </div>

                            <!-- Size Selection -->
                            <div class="d-flex align-items-center">
                                <span class="me-2">Size</span>
                                <select class="form-select form-select-sm">
                                    <option>SM</option>
                                    <option>M</option>
                                    <option>L</option>
                                    <option>XL</option>
                                </select>
                            </div>
                        </div>

                        <!-- Price and Buttons -->
                        <div class="d-flex align-items-center">
                            <span class="fs-4 fw-bold text-dark me-4">$58.00</span>
                            <button class="btn btn-danger text-white px-4">Button</button>
                            <button class="btn btn-secondary ms-3 rounded-circle d-flex align-items-center justify-content-center" style="width: 40px; height: 40px;">
                                <i class="bi bi-heart-fill text-danger"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Add Bootstrap Icons CDN in the head section if not already included -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.5.0/font/bootstrap-icons.min.css">

    </body>
</html>
