<%-- 
    Document   : adminNavbar
    Created on : Oct 20, 2024, 2:19:23 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Navbar</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            body {
                background-color: #F5F7FB;
                color: #1C1554;
            }

            .navbar {
                background-color: #FFFFFF;
                box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            }

            .navbar-brand,
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

            .avatar {
                width: 40px;
                height: 40px;
                object-fit: cover;
                border-radius: 5px;
                margin-right: 0.5rem;
            }

            .username {
                font-weight: 500;
                font-size: 1rem;
                text-decoration: none;
                color: inherit;
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

    <body>

        <nav class="navbar navbar-expand-lg">
            <div class="container-fluid">

                <a class="navbar-brand active">Dashboard</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href="index.jsp"
                                                onclick="parent.location.href = this.href; return false;">Home</a></li>
                        <li class="nav-item"><a class="nav-link active" href="adminReport.jsp"
                                                onclick="parent.location.href = this.href; return false;">Reports</a></li>
                        <li class="nav-item"><a class="nav-link active" href="/OrderController"
                                                onclick="parent.location.href = this.href; return false;">Handler</a></li>
                        <li class="nav-item"><a class="nav-link active" href="/ProductController?action=listControl"
                                                onclick="parent.location.href = this.href; return false;">Control</a></li>
                        <li class="nav-item"><a class="nav-link active" href="/EmployeeController"
                                                onclick="parent.location.href = this.href; return false;">Employees</a></li>
                        <li class="nav-item"><a class="nav-link active" href="/CategoryController"
                                                onclick="parent.location.href = this.href; return false;">Category</a></li>
                    </ul>
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link bell-link position-relative" href="adminNotification.jsp"
                               onclick="parent.location.href = this.href; return false;">
                                <img src="img/icon/bell.svg" alt="Notification" class="bell-icon">
                            </a>
                        </li>
                        <li class="nav-item d-flex align-items-center">
                            <a href="adminProfile.jsp" onclick="parent.location.href = this.href; return false;"> <img
                                    src="../img/avt/avt3.png" alt="Avatar" class="avatar"> </a>
                            <a href="adminProfile.jsp" onclick="parent.location.href = this.href; return false;"
                               class="username">Endmin</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>

    </body>
