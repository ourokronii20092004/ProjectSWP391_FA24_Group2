<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <style>
            .star-rating {
                display: flex;
                flex-direction: row-reverse;
                font-size: 1.5rem; /* Slightly smaller stars */
                justify-content: flex-end;
            }
            .star-rating input {
                display: none;
            }
            .star {
                cursor: pointer;
                color: #ddd;
                transition: color 0.2s;
            }
            .star:hover, .star:hover ~ .star,
            .star-rating input:checked ~ .star {
                color: gold;
            }

            .product-image {
                max-width: 100%; /* Ensure image responsiveness */
                height: auto;    /* Maintain aspect ratio */
            }

            /* Optional: Style the review section */
            .review {
                border: 1px solid #eee;
                padding: 15px;
                margin-bottom: 10px;
                border-radius: 5px;
            }

            .float-end {
                float: right;

            }
        </style>

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
        <div class="d-flex flex-column min-vh-100"> <%-- Use a container for better layout --%>
            <header class="d-flex justify-content-between align-items-center py-3 border-bottom">
                <a href="homepage.jsp" class="d-flex align-items-center text-decoration-none">
                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-md-flex gap-4"> <%-- Show nav on medium screens and up --%>
                    <a href="#" class="text-decoration-none text-muted">Home</a>
                    <a href="#" class="text-decoration-none text-muted">Shop</a>
                    <a href="#" class="text-decoration-none text-muted">About</a>
                    <a href="#" class="text-decoration-none text-muted">Contact</a>
                </nav>
                <div class="d-flex align-items-center gap-3">
                    <a class="nav-link" href="#">Hello, Nguyen Nhat Dang</a>
                    <form action="/CartController" method="POST" style="display: inline;">
                        <button type="submit" style="border: none; background: none; padding: 0;">
                            <i class="bi bi-cart fs-4"></i> <%-- Use Bootstrap icon --%>
                        </button>
                    </form>
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

                                    <!-- Display aggregate rating -->
                                    <div class="mb-3">
                                        <span class="text-dark fs-5">Average Rating:</span>
                                        <span class="text-warning fs-5">${averageRating}</span> 
                                        <i class="bi bi-star-fill text-warning"></i> <!-- Hiển thị biểu tượng sao -->
                                    </div>
                                    <div class="mb-3">
                                        <span class="text-dark fs-5">Total Reviews:</span>
                                        <span class="text-secondary fs-5">${totalRatings}</span>
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
                                    <form action="/CartController" method="post">
                                        <input type="hidden" name="action" value="add">
                                        <input type="hidden" name="productID" value="${param.productID}">
                                        <input type="number" name="quantity" value="1" min="1" class="form-control mb-2" style="width: 60px;
                                               margin: 0 auto;">
                                        <button type="submit" class="btn btn-primary">Add to Cart</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- show list Rating -->
            <section class="my-5">
                <div class="d-flex justify-content-center"> <%-- Center the button --%>
                    <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseComments" aria-expanded="false" aria-controls="collapseComments">
                        View Comments
                    </button>
                </div>
                <div class="collapse mt-3" id="collapseComments">  <%-- Added collapse div --%>
                    <div class="card card-body"> <%-- Optional: Add card styling --%>

                        <section class="my-5">
                            <h3>Comments</h3>
                            <c:forEach var="rating" items="${ratingList}">
                                <div class="border p-3 mb-3 rounded shadow-sm" style="margin-top:15px;">
                                    <p class="mb-0">${rating.user}: </p>
                                    <p class="mb-0">${rating.comment}</p>
                                    <div class="star-rating mb-1" style="margin-top:20px;">
                                        <!-- Render the filled and empty stars -->
                                        <c:forEach var="j" begin="${rating.ratingValue + 1}" end="5">
                                            <i class="bi bi-star"></i>
                                        </c:forEach>
                                        <c:forEach var="i" begin="1" end="${rating.ratingValue}">
                                            <i class="bi bi-star-fill"></i>
                                        </c:forEach>
                                    </div>a
                                    <small class="text-muted">Posted on: ${rating.createdAt}</small>

                                    <!-- Check if the logged-in user is the owner of the rating -->
                                    <c:if test="${sessionScope.userID == rating.userID || role == 3}">
                                        <form action="/RatingController" method="POST" onsubmit="return confirmRemoveSingle()">
                                            <input type="hidden" name="action" value="deleteRating">
                                            <input type="hidden" name="ratingID" value="${rating.ratingID}">
                                            <input type="hidden" name="productID" value="${productID}">
                                            <div style="margin-top:10px;">
                                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                            </div>
                                        </form>

                                    </c:if>
                                </div>
                            </c:forEach>

                        </section>
                        <%-- Add new rating form INSIDE the collapsible section --%>
                        <form action="/RatingController" method="POST" class="mt-4 border p-4 rounded shadow-sm" onsubmit="return validateForm()">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productID" value="${param.productID}">

                            <label for="ratingValue" class="form-label">Rating:</label>
                            <div class="star-rating mb-3">
                                <input type="radio" id="star5" name="ratingValue" value="5">
                                <label for="star5" class="star">★</label>
                                <input type="radio" id="star4" name="ratingValue" value="4">
                                <label for="star4" class="star">★</label>
                                <input type="radio" id="star3" name="ratingValue" value="3">
                                <label for="star3" class="star">★</label>
                                <input type="radio" id="star2" name="ratingValue" value="2">
                                <label for="star2" class="star">★</label>
                                <input type="radio" id="star1" name="ratingValue" value="1">
                                <label for="star1" class="star">★</label>
                            </div>

                            <label for="comment" class="form-label">Comment:</label>
                            <textarea name="comment" id="comment" rows="3" class="form-control" required></textarea>

                            <button type="submit" class="btn btn-primary mt-3">Submit Review</button>
                        </form>

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${errorMessage}
                            </div>
                        </c:if>
                    </div>
                </div>
            </section>
        </div>
        <style>
            .star-rating {
                display: flex;
                flex-direction: row-reverse; /* Đảo ngược hướng */
                font-size: 2rem; /* Kích thước lớn hơn cho các ngôi sao */
                justify-content: flex-end; /* Căn chỉnh các ngôi sao về bên phải */
            }
            .star-rating input[type="radio"] {
                display: none;
            }
            .star {
                cursor: pointer;
                color: #ddd;
                transition: color 0.2s;
            }
            .star:hover,
            .star:hover ~ .star {
                color: #f5b301;
            }
            .star-rating i {
                color: #ddd; /* Màu sắc ngôi sao rỗng */
                transition: color 0.2s;
                font-size: 1rem;
            }

            .star-rating i.bi-star-fill {
                color: gold; /* Màu sắc ngôi sao đầy */
            }
        </style>
        <script>

            function confirmRemoveSingle() {
                return confirm("Are you sure you want to remove this comment?");
            }
            function validateForm() {
                // Check if a star rating is selected
                const ratingSelected = document.querySelector('input[name="ratingValue"]:checked');
                // Check if the comment textarea is filled
                const comment = document.getElementById("comment").value.trim();

                // If either rating or comment is missing, show an alert and prevent submission
                if (!ratingSelected || comment === "") {
                    alert("Please enter both a rating and a comment before submitting.");
                    return false; // Prevent form submission
                }
                return true; // Allow form submission if validation passes
            }
        </script>
        <!-- Bootstrap Icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.7.2/font/bootstrap-icons.min.css" rel="stylesheet">

    </body>
</html>

