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
                height: 100%;
            }
            .footerIframe {
                border: none;
                width: 100%;
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
        </style>
    </head>

    <body>
        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <!-- User Management -->
        <div class="container-fluid fullpagecontent">

            <div class="col-md-12">
                <h6 class="card-title">Employee Management</h6>
                <div class="card">
                    <div class="row">
                        <div>
                            <div class="row">

                                <a href="/EmployeeController/add" class="btn btn-primary mb-3 col-md-2">Add User</a>


                                <input type="text" id="input" onkeyup="searchFunction()" placeholder="Search for names..">
                            </div>
                            <table class="table table-striped" id="empList">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Username</th>
                                        <th>Full name</th>                              
                                        <th>Email</th>
                                        <th>Created date</th>
                                        <th>Updated date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>                            
                                <!-- User data will be loaded here -->                               
                                <c:forEach items="${empList}" var="e">
                                    <tr>
                                        <td class="col-md-1">${e.id}</td>
                                        <td class="col-md-1">${e.userName}</td>
                                        <td class="col-md-3">${e.firstName} ${e.lastName}</td>
                                        <td class="col-md-3">${e.email}</td>
                                        <td class="col-md-1">${e.createdAt}</td>
                                        <td class="col-md-1">${e.updatedAt}</td>                                   
                                        <td class="col-md-2">
                                            <form action="/EmployeeController" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" name="action"/>
                                                <input onclick="getAction(update)" class="btn btn-secondary" type="submit" value="Update" />
                                                <input onclick="getAction(delete)" class="btn btn-danger" type="submit" value="Delete" />
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>
                        </div>                       
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <iframe class="footerIframe" src="adminFooter.jsp" height="70px"></iframe>
        </footer>
    </body>
    <script>
        function searchFunction() {
            // Declare variables
            var input, filter, table, td, tr, i, txtValue;
            input = document.getElementById('input');
            filter = input.value.toUpperCase();
            table = document.getElementById("empList");
            tr = table.rows;
            // Loop through all list items, and hide those who don't match the search query
            for (i = 0; i < tr.length; i++) {
                td = tr[i].getElementsByTagName("td")[2];
                // Check if td exists
                if (td) {
                    txtValue = td.textContent.toUpperCase();
                    if (txtValue.indexOf(filter) > -1) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }
            }
        }
        function getAction(action) {
            document.querySelector('input[name="action"]').value = action;
        }
    </script>
</html>
