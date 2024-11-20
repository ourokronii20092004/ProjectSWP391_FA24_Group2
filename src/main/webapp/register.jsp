<%-- 
    Document   : register
    Created on : Oct 20, 2024, 12:25:47 PM
    Author     : phanp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register</title>
        <link rel="stylesheet" href="../css/auth.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
                integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
    </head>

    <body>
        <div class="container d-flex justify-content-center align-items-center min-vh-100">

            <div class="row border rounded-5 p-3 bg-white shadow box-area" style="border-radius: 12px;">

                <div class="col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column left-box"
                     style="background:#103cbe; border-radius: 12px;">
                    <div class="feature-image mb-3">
                        <img src="https://i.pinimg.com/736x/aa/cb/ca/aacbca564f3c3af647dd277025ee895f.jpg" class="img-fluid" style="border: solid 2px; border-radius: 5px; width:500px;" />
                    </div>
                    <p class="text-white fs-2" style=" font-weight: bold; " >PAMB</p>
                    <small class="text-white text-wrap text-center">WELCOME TO THE FLOWER GALLERY</small>
                </div>

                <div class="col-md-6 right-box">
                    <div class="row align-items-center">
                        <div class="header-text mb-4">
                            <h2>Welcome to PAMB</h2>
                            <p>Sign up your account</p>
                        </div>
                        <form action="/RegisterController/Create" method="post">
                            <%
                                    String check = (String) request.getSession().getAttribute("action");
                                    if ("password".equals(check)) {
                            %>
                            <div class="alert alert-danger" role="alert">
                                Password is not match with Confirm password!
                            </div>
                            <%
                                }
                                if ("success".equals(check)) {
                            %>
                            <div class="alert alert-success" role="alert">
                                Create successfully! Please check your email to confirm the register!
                            </div>
                            <%
                                }                 
                                if ("fail".equals(check)) {
                            %>
                            <div class="alert alert-danger" role="alert">
                                Username or email has already been used! please try another one!
                            </div>
                            <%
                                }
                            request.getSession().removeAttribute("action");
                            %>
                            <div class="input-group mb-3">
                                <input minlength="8" type="text" class="form-control form-control-lg bg-light fs-6"
                                       name="username" placeholder="Username" required />
                            </div>
                            <div class="input-group mb-3">
                                <input minlength="8" type="password" class="form-control form-control-lg bg-light fs-6" 
                                       name="password"  placeholder="Password" required />
                            </div>

                            <div class="input-group mb-3">
                                <input minlength="8" type="password" class="form-control form-control-lg bg-light fs-6"
                                       name="confirmPassword"  placeholder="Confirm Password" required />
                            </div>
                            <div class="input-group mb-3">
                                <input type="email" class="form-control form-control-lg bg-light fs-6"
                                       name="email" placeholder="Email" required />
                            </div>

                            <div class="input-group mb-3 ms-1 d-flex justify-content-between align-items-center">

                                <div class="form-check flex-row-1">
                                    <input type="checkbox" class="form-check-input" id="formCheck" required="" />
                                    <label for="formCheck"><small>I Agree to <a href="">Privacy Policy</a></small></label>
                                </div>

                                <div class="m-2 row">
                                    <small>Already have an account? <a href="/LoginController">Sign In</a></small>
                                </div>

                            </div>

                            <div class="input-group mb-3" style="border-radius: 12px;">
                                <button class="btn btn-lg btn-primary w-100 fs-6">Sign Up</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>