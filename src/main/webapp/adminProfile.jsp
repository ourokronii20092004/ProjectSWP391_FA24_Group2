<%-- 
    Document   : adminProfile
    Created on : Oct 20, 2024, 2:51:19 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Profile | PAMP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        body {
            background-color: #F5F7FB;
            color: #1C1554;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
        }

        .card-container {
            margin-top: 20px;
            margin-bottom: 20px;
            width: 70%;
            margin-left: auto;
            margin-right: auto;
        }

        .card-title {
            color: #888;
            margin-bottom: 10px;
        }

        .card {
            background-color: #FFFFFF;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            border: none;
            padding: 20px;
        }

        .fullpagecontent {
            flex: 1;
        }

        footer {
            background-color: #3E3E3E;
            color: #FFFFFF;
            text-align: center;
            padding: 1rem 0;
        }

        iframe {
            border: none;
            width: 100%;
        }

        .profile-image {
            width: 150px;
            height: 150px;
            object-fit: cover;
            border-radius: 50%;
            margin: 0 auto 20px auto;
            display: block;
        }

        .table {
            border-collapse: collapse;
            width: 100%;
        }

        .table td {
            white-space: nowrap;
        }

        .table td,
        .table th {
            border: none;

        }

        .table td:nth-child(2) {
            width: 100%;
        }
    </style>
</head>

<body>

    <iframe src="adminNavbar.jsp" height="60px"></iframe>

    <div class="container-fluid fullpagecontent">
        <div class="row">
            <div class="col-md-12 card-container">
                <h6 class="card-title">Admin Profile</h6>
                <div class="card">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <img src="../img/avt/avt3.png" alt="Admin Profile Picture" class="profile-image">
                            <h2>Endmin</h2>
                            <p class="text-muted">Administrator</p>
                        </div>
                        <div class="col-md-6">
                            <table class="table">
                                <tbody>
                                    <tr>
                                        <th>Username:</th>
                                        <td>Endmin</td>
                                    </tr>
                                    <tr>
                                        <th>Email:</th>
                                        <td>endmin@endfieldindustry.com</td>
                                    </tr>
                                    <tr>
                                        <th>Number:</th>
                                        <td>no</td>
                                    </tr>
                                    <tr>
                                        <th>Bio:</th>
                                        <td>The Doctor killed Theresa, keep Oripathy going then go mimir</td>
                                    </tr>
                                    <tr>
                                        <th>Joined:</th>
                                        <td>2024-01-21</td>
                                    </tr>
                                    <!-- Add more rows as needed -->
                                </tbody>
                            </table>
                        </div>
                        <div class="col-md-6">
                            <!-- You can add additional information or sections here, like: -->
                            <!-- - Recent Activity -->
                            <!-- - Settings -->
                            <!-- - Other relevant details -->
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-12 text-center">
                            <!-- Add buttons to edit or delete the profile here -->
                            <!-- Maybe needed, maybe not -->
                            <!-- Probably not since making a pop-up is better, or edit right in the form-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer>
        <iframe src="adminFooter.jsp" height="50px"></iframe>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
    crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
    crossorigin="anonymous"></script>
</body>
</html>
