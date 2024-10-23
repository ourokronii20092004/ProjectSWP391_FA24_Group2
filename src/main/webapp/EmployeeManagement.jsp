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
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary mb-3 col-md-2" data-bs-toggle="modal" data-bs-target="#addEmployee">
                                    Add User
                                </button>

                                <input type="text" id="input" onkeyup="searchFunction()" placeholder="Search for names..">
                            </div>
                            <table class="table table-striped" id="empList">                             
                                <tr>
                                    <th class="col-md-1">Picture</th>
                                    <th class="col-md-1">ID</th>
                                    <th class="col-md-1">Username</th>
                                    <th class="col-md-2">Full name</th>                              
                                    <th class="col-md-3">Email</th>
                                    <th class="col-md-1">Created date</th>
                                    <th class="col-md-1">Updated date</th>
                                    <th class="col-md-2">Actions</th>
                                </tr>                       
                                <!-- User data will be loaded here -->      

                                <c:forEach items="${empList}" var="e">                                 
                                    <c:if test="${e.isActive == true}">
                                        <tr>
                                            <td class="col-md-1">
                                                <c:if test="${p.imageURL != null}">
                                                    <img src="${p.imageURL}" alt="${p.productName}" height="50">
                                                </c:if>
                                            </td>
                                            <td class="col-md-1">${e.id}</td>
                                            <td class="col-md-1">${e.userName}</td>
                                            <td class="col-md-2">${e.firstName} ${e.lastName}</td>
                                            <td class="col-md-3">${e.email}</td>                                       
                                            <td class="col-md-1">${e.createdAt}</td>
                                            <td class="col-md-1">${e.updatedAt}</td>                                   
                                            <td class="col-md-2">
                                                <button data-bs-toggle="modal" 
                                                        data-bs-target="#editEmployee"
                                                        data-user-id ="${e.id}"
                                                        data-user-username="${e.userName}"
                                                        data-user-firstName="${e.firstName}"
                                                        data-user-lastName="${e.lastName}"
                                                        data-user-email="${e.email}"
                                                        data-user-address="${e.address}"
                                                        data-user-phoneNumber="${e.phoneNumber}"
                                                        data-user-imgURL="${e.imgURL}"
                                                        class="btn btn-secondary">Details</button>
                                                <a onclick="return confirm('Are you sure you want to delete User: ${e.userName} | ID: ${e.id}?')" href="/EmployeeController/deactivate?userID=${e.id}" class="btn btn-danger">Remove</a>   
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>                       
                    </div>
                </div>
            </div>
        </div>


        <!-- Modal Add -->
        <div class="modal fade" id="addEmployee" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Add Employee</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="employeeForm" action="EmployeeController/add" method="post">
                            <div class="form-group">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" name="username" class="form-control" id="username" placeholder="Username">
                            </div>
                            <div class="form-group">
                                <label for="password" class="form-label">Password</label>
                                <input type="text" class="form-control" name="password"  placeholder="Password" required>
                            </div>
                            <div class="form-group">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" name="address" id="address" class="form-control" placeholder="Address">
                            </div>
                            <div class="form-group">
                                <label for="email" class="form-label">Email</label>
                                <input type="text" name="email" id="email" class="form-control" placeholder="Email" required>
                            </div>
                            <div class="form-group">
                                <label for="firstName" class="form-label">First name</label>
                                <input type="text" name="firstName" id="firstName" class="form-control" placeholder="First name" required>
                            </div>
                            <div class="form-group">
                                <label for="lastName" class="form-label">Last name</label>
                                <input type="text" name="lastName" id="lastName" class="form-control" placeholder="Last name" required>
                            </div>
                            <div class="form-group">
                                <label for="phoneNumber" class="form-label">Phone Number</label>
                                <input type="text" name="phoneNumber" id="phoneNumber" class="form-control" placeholder="Phone Number" required>
                            </div>
                            <div class="form-group">
                                <label for="pic" class="form-label">Profile Picture</label>
                                <input type="file" class="form-control" id="pic" name="pic" accept="image/*" disabled="">
                            </div>
                            <div class=" form-group d-flex justify-content-center">
                                <input type="submit" class="btn btn-primary me-3 p-2" value="Add employee"/>
                                <button type="button" class="btn btn-secondary me-3 p-2" data-bs-dismiss="modal">Close</button>   
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">                                         
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal Edit -->
        <div class="modal fade" id="editEmployee" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Employee's detail information</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="employeeForm" action="EmployeeController/edit" method="post">
                            <div class="row">
                                <div class="col-md-3 form-group">
                                    <label for="editId" class="form-label">ID:</label>
                                    <input type="text" name="userID" id="editID" class="form-control"  readonly="" >
                                </div>
                                <div class="col-md-8 form-group">
                                    <label for="editUsername" class="form-label">Username</label>
                                    <input type="text" name="username" id="editUsername" class="form-control"  readonly="" disabled="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="editAddress" class="form-label">Address</label>
                                <input type="text" name="address" id="editAddress" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="editEmail" class="form-label">Email</label>
                                <input type="text" name="email" id="editEmail" class="form-control" placeholder="Email"  readonly="" disabled="">
                            </div>
                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <label for="editFirstName" class="form-label">First name</label>
                                    <input type="text" name="firstName" id="editFirstName" class="form-control" placeholder="First name" required>
                                </div>
                                <div class="col-md-6 form-group">
                                    <label for="editLastName" class="form-label">Last name</label>
                                    <input type="text" name="lastName" id="editLastName" class="form-control" placeholder="Last name" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="editPhoneNumber" class="form-label">Phone Number</label>
                                <input type="text" name="phoneNumber" id="editPhoneNumber" class="form-control" placeholder="Phone Number" required>
                            </div>
                            <div class=" form-group">
                                <label for="editImgURL" class="form-label">Profile Picture</label>
                                <input type="file" class="form-control" id="editImgURL" name="pic" accept="image/*" disabled="">
                            </div>
                            <div class=" form-group d-flex justify-content-center">
                                <input type="submit" class="btn btn-primary me-3 p-2" value="Update"/>
                                <button type="button" class="btn btn-secondary me-3 p-2" data-bs-dismiss="modal">Close</button>   
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">                                         
                    </div>
                </div>
            </div>
        </div>
        <footer>
            <iframe class="footerIframe" src="adminFooter.jsp" height="70px"></iframe>
        </footer>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous">
    </script>
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
                td = tr[i].getElementsByTagName("td")[3];
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

        // edit employee modal 
        const editEmployee = document.getElementById('editEmployee');
        editEmployee.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-user-id');
            const username = button.getAttribute('data-user-username');
            const firstName = button.getAttribute('data-user-firstName');
            const lastName = button.getAttribute('data-user-lastName');
            const email = button.getAttribute('data-user-email');
            const address = button.getAttribute('data-user-address');
            const phoneNumber = button.getAttribute('data-user-phoneNumber');
            const imgURL = button.getAttribute('data-user-imgURL');
            document.getElementById('editID').value = id;
            document.getElementById('editUsername').value = username;
            document.getElementById('editFirstName').value = firstName;
            document.getElementById('editLastName').value = lastName;
            document.getElementById('editEmail').value = email;
            document.getElementById('editAddress').value = address;
            document.getElementById('editPhoneNumber').value = phoneNumber;
            document.getElementById('editImgURL').value = imgURL;
            // them data cho may cai modal
        });

    </script>
</html>
