<%-- 
    Document   : productManagement.jsp
    Created on : Oct 24, 2024, 9:42:29 AM
    Author     : Nguyen Nhat Dang - CE180010
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="Models.Product"%> 
<%@page import="DAOs.UserDAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Management | PAMP</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            body {
                background-color: #f8f9fa;
                color: #343a40;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
            }
            .container-fluid {
                padding: 2rem 1rem;
                flex: 1;
                overflow-y: auto;
                height: calc(100vh - 130px);

            }
            .card {
                border: none;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
                padding: 2rem;
                background-color: white;
            }
            .card-title {
                color: #261d6a;
                font-weight: 600;
                margin-bottom: 1.5rem;
            }
            #productListTable {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 1rem;
                table-layout: fixed;


            }
            #productListTable th, #productListTable td {
                padding: 1.5rem 1rem;
                border-bottom: 1px solid #dee2e6;
                text-align: left;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;

            }
            #productListTable img {
                width: 70px;
                height: 70px;
                object-fit: cover;
                border-radius: 5px;
                margin-right: 1rem;
            }

            #productListTable th:first-child, #productListTable td:first-child {
                width: 5%;
            } /* ID */
            #productListTable th:nth-child(2), #productListTable td:nth-child(2) {
                width: 10%;
            } /* Image */
            #productListTable th:nth-child(3), #productListTable td:nth-child(3) {
                width: 18%;
            } /* Name */
            #productListTable th:nth-child(4), #productListTable td:nth-child(4) {
                width: 22%;
            } /* Description */
            #productListTable th:nth-child(5), #productListTable td:nth-child(5) {
                width: 10%;
            } /* Price */
            #productListTable th:nth-child(6), #productListTable td:nth-child(6) {
                width: 10%;
            } /* Category ID */
            #productListTable th:nth-child(7), #productListTable td:nth-child(7) {
                width: 10%;
            } /* Stock */
            #productListTable th:last-child, #productListTable td:last-child {
                width: 15%;
            } /* Actions */




            #productListTable input[type="checkbox"] {
                transform: scale(1.5);
                accent-color: #261d6a;
            }
            .table-responsive {
                overflow-x: auto;
            }
            .btn-primary {
                background-color: #261d6a;
                border-color: #261d6a;
            }
            footer {
                background-color: #3E3E3E;
                color: white;
                text-align: center;
                padding: 1rem 0;
                margin-top: auto;
            }

            iframe {
                border: none;
                width: 100%;
            }

            html, body {
                overflow-y: auto;
            }

            .title-and-buttons {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 1.5rem;
            }
            .search-and-filter {
                display: flex;
                align-items: center;
                gap: 1rem;
            }
            .search-button {
                background: none;
                border: none;
                padding: 0;
                cursor: pointer;
            }
            .search-button img {
                width: 20px;
                height: 20px;
            }

            .modal-body {
                display: flex;
                flex-direction: column;

            }

            .col-md-6 textarea {
                height: border-box;
                resize: none;
                max-height: inherit;
            }

            .modal-body label{
                align-self: flex-start;
                font-weight: bold;
            }
            .modal-body .row {
                align-items: flex-start;
                max-height: fit-content;
                height: fit-content;
            }
            .image-preview {
                max-width: 100%;
                height: auto;
                object-fit: cover;
                border: 1px solid #ced4da;
                margin-bottom: 0.5rem;
            }
            .image-preview-container{
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
                width: 100%;
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
        </style>
    </head>
    <body>
        <% Product pro = (Product) request.getAttribute("product"); %>
        <iframe src="adminNavbar.jsp" height="60px"></iframe>
        <div class="container-fluid">
            <div class="card">

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
                                            <!-- Hiển thị sao rỗng (empty stars) -->
                                            <c:forEach var="j" begin="${rating.ratingValue + 1}" end="5">
                                                <i class="bi bi-star"></i>
                                            </c:forEach>
                                            <!-- Hiển thị sao đầy (filled stars) -->
                                            <c:forEach var="i" begin="1" end="${rating.ratingValue}">
                                                <i class="bi bi-star-fill"></i>
                                            </c:forEach>
                                        </div>
                                        <small class="text-muted">Posted on: ${rating.createdAt}</small>

                                        <!-- Check if the logged-in user is the owner of the rating -->

                                        <form action="/ManageRatingController" method="POST"" onsubmit="return confirmRemoveSingle()">
                                            <input type="hidden" name="action" value="deleteRating">
                                            <input type="hidden" name="ratingID" value="${rating.ratingID}">
                                            <input type="hidden" name="productID" value="${productID}">
                                            <div style="margin-top:10px;">
                                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                            </div>
                                        </form>


                                    </div>
                                </c:forEach>

                            </section>

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
            <footer>
                <iframe src="adminFooter.jsp" height="70px"></iframe>
            </footer>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
            <script src="js/ProductManagement.js"></script>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
            </body>
            </html>