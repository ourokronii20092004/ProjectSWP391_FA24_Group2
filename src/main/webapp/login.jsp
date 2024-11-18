<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.Cookie" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
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
                        <img src="../img/auth-background.jpg" class="img-fluid" style="width:250px;" />
                    </div>
                    <p class="text-white fs-2">Some content here</p>
                    <small class="text-white text-wrap text-center">Description or something here</small>
                </div>
                <%
                    String username = "";
                    String password = "";
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if ("username".equals(cookie.getName())) {
                                username = cookie.getValue();
                            } else if ("password".equals(cookie.getName())) {
                                password = cookie.getValue();
                            }
                        }
                    }
                %>
                <div class="col-md-6 right-box">
                    <form action="LoginController" method="post"> 
                        <div class="row align-items-center">
                            <div class="header-text mb-4">
                                <h2>PAMB</h2>
                                <p>A Flower Shop Website</p>
                            </div>
                            <div class="input-group mb-3">
                                <!-- Display error message if login fails -->
                                <%
                                    String check = request.getParameter("check");
                                    if ("false".equals(check)) {
                                %>
                                <div class="alert alert-danger" role="alert">
                                    Incorrect username or password.
                                </div>
                                <%
                                    }
                                    if ("register".equals(check)) {
                                %>
                                <div class="alert alert-success" role="alert">
                                    Confirm success! Please login!
                                </div>
                                <%
                                    }
                                %>
                            </div>
                            <div class="input-group mb-3">

                                <input minlength="8" type="text" name="username" class="form-control form-control-lg bg-light fs-6" placeholder="Username" value="<%= username%>" required />
                            </div>
                            <div class="input-group mb-1">
                                <input minlength="8" type="password" name="password" class="form-control form-control-lg bg-light fs-6" placeholder="Password" value="<%= password%>" required />
                            </div>

                            <div class="input-group mb-3 mt-3 d-flex justify-content-between">
                                <div class="row">
                                    <small>Don't have an account? <a href="/RegisterController">Sign Up</a></small>
                                </div> 
                                <div class="row">
                                    <small><a href="/PasswordController?action=forgetPassword">Forget Password?</a></small>
                                </div>
                            </div>

                            <div class="form-check ms-3 mb-3">
                                <input type="checkbox" class="form-check-input" id="formCheck" name="remember" <%= !username.isEmpty() ? "checked" : ""%> />
                                <label for="formCheck"><small>Remember Me</small></label>
                            </div>

                            <div class="input-group mb-3" style="border-radius: 12px;">
                                <button type="submit" class="btn btn-lg btn-custom w-100 fs-6">Login</button>
                            </div>    
                            <div class="input-group mb-3" style="border-radius: 12px;">
                                <a href="/LoginWithGoogleController" class="btn btn-lg btn-custom w-100 fs-6"><img src="../img/google.png" style="width: 20px;" class="me-2">Sign In With Google</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>

</html>

