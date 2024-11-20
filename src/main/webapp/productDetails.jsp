<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Models.Product"%> 
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
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
            .card-img-top {
                width: 100%; /* Chiều rộng chiếm toàn bộ khung card */
                height: 600px; /* Chiều cao cố định */
                object-fit: cover; /* Giữ tỷ lệ, cắt phần dư */
                object-position: top; /* Ưu tiên phần trên của ảnh */
                border-radius: 8px; /* Tùy chọn: Bo góc ảnh */
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

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
    </head>
    <body>
        <% Product pro = (Product) request.getAttribute("product"); %>
        <header class="d-flex justify-content-between align-items-center p-3" style="background-color: #D3FFA3; border-bottom: 2px solid black;">
            <a href="/MainPageController" class="d-flex align-items-center justify-content-center text-decoration-none">

                <span class="h5 ms-2">PAMB</span>
            </a>
            <nav class="d-none d-lg-flex gap-4">

                <a href="/MainPageController" class="nav-link active">Home</a>
                <a href="/AboutController" class="nav-link active">About</a>
                <a href="/AboutController" class="nav-link active">Contact</a>
            </nav>
            <%

UserDAO userDAO = new UserDAO();
                            
User user = request.getSession().getAttribute("userID") == null ? null : userDAO.getUserData((int) request.getSession().getAttribute("userID"));

            %>
            <div class="d-flex align-items-center gap-3">
                <div class="avatar-container ms-auto d-flex align-items-center" >


                    <% if(user != null){%>
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
                        <a class="user-greeting">Hello, <%= user.getUserName() %></a>
                        <a class="dropdown-item" href="CustomerProfileController">Profile</a>
                        <a class="dropdown-item" href="LogoutController">Logout</a>
                    </div>
                    <%} else {%>
                    <div class="d-flex align-items-center gap-3">
                        <a href="/LoginController" class="btn btn-outline-primary">Login</a>
                        <a href="/RegisterController" class="btn btn-primary">Sign Up</a>
                    </div>
                    <%}%>

                </div>
            </div>

        </header>

        <section class="bg-white py-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-10 d-flex flex-wrap">
                        <div class="col-lg-6">
                            <img src="<%= pro.getImageURL() %>" alt="ecommerce" class="card-img-top rounded border border-secondary ">
                        </div>
                        <div class="col-lg-6 pt-4 pt-lg-0">
                            <h2 class="text-secondary text-uppercase"><%= pro.getProductName() %></h2>

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
                            <p class="text-muted" >
                                <%= pro.getDescription() %>
                            </p>

                            <div class="d-flex align-items-center">
                                <span class="h3 text-dark"><%= pro.getPrice() %></span>
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

