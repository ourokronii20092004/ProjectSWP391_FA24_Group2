<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.Cookie" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Change Password</title>
        <link rel="stylesheet" href="../css/auth.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    </head>

    <body>
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="row border rounded-5 p-3 bg-white shadow box-area" style="border-radius: 12px;">
                <div class="col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column left-box"
                     style="background:#103cbe; border-radius: 12px;">
                    <div class="feature-image mb-3">
                        <img src="https://i.pinimg.com/originals/bd/56/5d/bd565dcc0a556add0b0a0ed6b26d686e.gif" class="img-fluid" style="width:250px;" />
                    </div>
                    <p class="text-white fs-2">Some content here</p>
                    <small class="text-white text-wrap text-center">Description or something here</small>
                </div>

                <div class="col-md-6 right-box">
                    <form action="PasswordController" method="get"> 
                        <div class="row align-items-center">
                            <div class="header-text mb-2">
                                <h2>PAMB</h2>
                                <p>A Flower Shop Website</p>
                            </div>
                            <p>Change A New Password</p>  
                            <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger text-center p-2 ms-3 small" style="width: 50%; font-size: 14px;" role="alert">
                                <%= request.getAttribute("error") %>
                            </div>
                            <% } %>
                            <div class="input-group mb-3">
                                <input minlength="8" type="password" class="form-control form-control-lg bg-light fs-6"
                                       name="oldPassword"  placeholder="Your Password" required />
                            </div>
                            <div class="input-group mb-3">
                                <input minlength="8" type="password" class="form-control form-control-lg bg-light fs-6"
                                       name="newPassword"  placeholder="New Password" required />
                            </div>
                            <div class="input-group mb-3">
                                <input minlength="8" type="password" class="form-control form-control-lg bg-light fs-6"
                                       name="confirmPassword"  placeholder="Confirm New Password" required />
                            </div>


                            <div class="input-group mb-3 mt-3 d-flex justify-content-between">
                                <div class="row">
                                    <small>Already have an account? <a href="/LoginController">Sign In</a></small>
                                </div>
                                <div class="row">
                                    <small>Don't have an account? <a href="/RegisterController">Sign Up</a></small>
                                </div> 

                            </div>

                            <div class="input-group mb-3" style="border-radius: 12px;">
                                <button type="submit" class="btn btn-lg btn-custom w-100 fs-6">Confirm</button>
                            </div>    

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>

</html>

