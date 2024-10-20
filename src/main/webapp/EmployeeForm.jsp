<%-- 
    Document   : EmployeeForm
    Created on : Oct 20, 2024, 1:25:56 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Create Hostel</title>
        <link href="https://fonts.googleapis.com/css2?family=Karla:wght@400;700&display=swap" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Karla', sans-serif;
                background: no-repeat;
                background-size: cover;
                background-position: center;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 0;
                overflow: hidden;
            }
            .container {
                background: rgba(0, 0, 0, 0.6);
                border: 1px solid rgba(255, 255, 255, 0.1);
                border-radius: 15px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
                padding: 0;
                max-width: 600px;
                width: 100%;
                overflow: hidden;
                backdrop-filter: blur(10px);
                color: #fff;
            }
            .container h3 {
                text-align: center;
                margin: 0;
                background-color: rgba(0, 0, 0, 0.6);
                color: #007bff;
                padding: 20px;
                border-radius: 15px 15px 0 0;
                width: 100%;
                box-sizing: border-box;
            }
            .form-content {
                padding: 30px;
                max-height: calc(100vh - 75px); /* Adjust for header height */
                overflow-y: auto;
                background-color: rgba(255, 255, 255, 0.1);
                box-shadow: inset 0 4px 8px rgba(0, 0, 0, 0.05);
                border-radius: 0 0 15px 15px;
            }
            .form-group {
                margin-bottom: 1rem;
            }
            .form-control, .form-select {
                border-radius: 0.25rem;
                background-color: rgba(255, 255, 255, 0.2);
                color: #fff;
                border: 1px solid #ced4da;
            }
            .form-control:focus, .form-select:focus {
                box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
                border-color: #80bdff;
                background-color: rgba(255, 255, 255, 0.2);
                color: #fff;
            }
            .btn {
                display: block;
                width: 100%;
                background-color: #28a745;
                border-color: #28a745;
                color: #fff;
                padding: 0.5rem 1rem;
                border-radius: 0.25rem;
                transition: background-color 0.2s ease;
            }
            .btn:hover {
                background-color: #218838;
                border-color: #218838;
            }
            .btn-secondary {
                background-color: gray;
            }
        </style>
    </head>
    <body>
        <main class="container">
            <h3>Add Employee</h3>
            <div class="form-content">
                <form id="employeeForm" action="EmployeeController" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" name="username" id="username" class="form-control" placeholder="Username" required>
                    </div>
                    <div class="form-group">
                        <label for="password" class="form-label">password</label>
                        <input type="text" class="form-control" name="password" id="password" required>
                    </div>
                    <div class="form-group">
                        <label for="address" class="form-label">Address</label>
                        <input type="text" name="address" id="address" class="form-control" placeholder="Address" required>
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
                        <input type="file" class="form-control" id="pic" name="pic" accept="image/*" required>
                    </div>
                    <button type="submit" name="add" id="btnNext" class="btn">Add Employee</button>
                    <a href="#" class="btn btn-secondary mt-3">Back To Main</a>
                </form>
            </div>
        </main>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
        <script>
            document.getElementById('employeeForm').addEventListener('submit', function (e) {
                const totalRooms = document.getElementById('txtTotalRooms').value;
                if (totalRooms <= 0) {
                    alert('Total Rooms must be a positive number.');
                    e.preventDefault();
                }
            });
        </script>

    </body>
</html>
