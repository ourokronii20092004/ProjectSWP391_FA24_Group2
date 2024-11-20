<%-- 
    Document   : about
    Created on : Jun 19, 2024, 9:21:53 AM
    Author     : CE180010 - Nguyen Nhat Dang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="DAOs.UserDAO"%>
<%@page import="Models.User"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PAMB HomePage</title>
        <link href ="/css/about.css" rel="stylesheet">

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
            /* Team Section */
            .team-grid {
                display: grid;
                align-items: center;
                justify-content: center;
                grid-template-columns: repeat(auto-fit, minmax(150px, 250px));
                gap: 40px;
                margin-top: 30px;
            }

            .team-member {
                text-align: center;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
            }

            .team-member img {
                width: 150px;
                height: 150px;
                border-radius: 5%;
                margin: 0 auto 15px auto;
                object-fit: cover;
            }

            .team-member h4 {
                margin-top: 10px;
            }

            .team-member p {
                margin-bottom: 15px;
            }

            .team-intro {
                display: flex;
                align-items: center;
                justify-content: center;
                margin-bottom: 30px;
            }

            .team-description {
                flex: 1;
                padding: 20px;
                display: block;
                text-align: center;
                font-size: medium;
                min-width: 70%;
                max-width: 70%;
                font-size: 1.2em;
            }

            /* Container for Content */
            .content-box {
                max-width: 80%;
                margin: 0 auto;
                padding: 0 20px;
            }

            /* Footer Styles */
            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 1em 0;
                margin-top: 80px;
            }

            /* Return to Top Button */
            #return-to-top-btn {
                display: none;
                position: fixed;
                bottom: 20px;
                right: 20px;
                z-index: 99;
                border: none;
                outline: none;
                background-color: rgba(23, 37, 64, 0.9);
                color: white;
                cursor: pointer;
                padding: 0;
                width: 50px;
                height: 50px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
                font-size: 10px;
                text-align: center;
            }

            #return-to-top-btn:hover {
                background-color: #A2323B;
            }

            #return-to-top-btn span {
                display: inline-block;
                transform: scaleX(2.0);
            }
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                color: #333;
                background-color: #FAFBFD;
                line-height: 1.6;
            }

            h1,
            h2,
            h3,
            h4,
            strong {
                font-weight: 700;
                margin-bottom: 0.8em;
            }

            h2 {
                font-size: 2em;
            }

            h3 {
                font-size: 1.4em;
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
                    <a href="/" class="nav-link active">About</a>
                    <a href="#" class="nav-link active">Contact</a>


                </nav>

                <c:choose>
                    <c:when test = "${ sessionScope.userID == null}">

                        <div class="d-flex align-items-center gap-3">
                            <a href="/LoginController" class="btn btn-outline-primary">Login</a>
                            <a href="/RegisterController" class="btn btn-primary">Sign Up</a>
                        </div>
                    </c:when>
                    <c:otherwise>
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

                                <img src="<%= user.getImgURL() %>" alt="Avatar" class="avatar me-2 ms-2" id="avatarButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <!-- me-3 tạo margin-right cho avatar -->

                                <!-- Menu thả xuống -->
                                <div class="dropdown-menu dropdown-menu-left custom-dropdown" aria-labelledby="avatarButton">
                                    <a class="user-greeting">Hello, <%= name %></a>
                                    <a class="dropdown-item" href="CustomerProfileController">Profile</a>
                                    <a class="dropdown-item" href="LogoutController">Logout</a>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>

            </header>
            <section id="intro">
                <div>
                    <div class="team-intro">
                        <div class="team-description">
                            <h2>Meet the PAMB Team</h2>
                            <p>We’re a team of University Students from FPT University, passionate about flowers and committed to spreading joy through floral arrangements. Our online flower shop is your one-stop destination for exquisite blooms, personalized gifts, and heartfelt expressions. We curate a diverse collection of fresh flowers, from classic roses to exotic orchids, to cater to every occasion, big or small.</p>
                        </div>
                    </div>
                    <div class="team-grid">
                        <div class="team-member">
                            <img src="img/hau.jpg" alt="Le Trung Hau">
                            <h4>Le Trung Hau</h4>
                            <p>Team member</p>
                        </div>
                        <div class="team-member">
                            <img src="img/dang.jpg" alt="Nguyen Nhat Dang">
                            <h4>Nguyen Nhat Dang</h4>
                            <p>Team member</p>
                        </div>
                        <div class="team-member">
                            <img src="img/phat.jpg" alt="Phan Viet Phat">
                            <h4>Phan Viet Phat</h4>
                            <p>Team member</p>
                        </div>
                        <div class="team-member">
                            <img src="img/huy.jpg" alt="Tran Le Gia Huy">
                            <h4>Tran Le Gia Huy</h4>
                            <p>Team member</p>
                        </div>
                        <div class="team-member">
                            <img src="img/binh.jpg" alt="Tran Le Gia Huy">
                            <h4>Phan Phuc Binh</h4>
                            <p>Project Leader</p>
                        </div>
                    </div>
                </div>
            </section>
            <footer>
                <iframe src="adminFooter.jsp" height="50px"></iframe>
            </footer>

            <!-- Return to Top Button -->
            <button id="return-to-top-btn" title="Return to Top">
                <span>Λ</span>
            </button>


        </div>
        <!-- Return to Top Script-->
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
        </script>
    </body>

</html>