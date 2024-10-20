<%-- 
    Document   : EmployeeManagement
    Created on : Oct 20, 2024, 3:57:53 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Employee Management</title>
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

            /* Hide forms initially */
            #addEmployeeForm,
            #editEmployeeForm
            {
                display: none;
            }
        </style>
    </head>

    <body>
        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <!-- User Management -->
        <div class="row card-container">
            <div class="col-md-12">
                <h6 class="card-title">Employee Management</h6>
                <div class="card">
                    <div class="row">
                        <div class="col-md-6">
                            <button id="showAddEmployeeFormBtn" class="btn btn-primary mb-3">Add User</button>
                            <div id="addEmployeeForm">
                                <!-- Form to add new user -->
                            </div>
                            <div id="editEmployeeForm">
                                <!-- Form to edit existing user -->
                            </div>

                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Username</th>
                                        <th>Email</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>                            
                                    <!-- User data will be loaded here -->
                                    <c:forEach items="${empList}" var="e">
                                        <thead>
                                            <tr>
                                                <td>${e.id}</td>
                                                <td>${e.userName}</td>
                                                <td>${e.email}</td>
                                                <td>Actions</td>
                                            </tr>
                                        </thead>
                                    </c:forEach>
                            
                            </table>
                        </div>
                        <div class="col-md-6">
                            <!-- You can add a preview or details section here -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <iframe src="adminFooter.jsp" height="50px"></iframe>
        </footer>
    </body>
</html>
