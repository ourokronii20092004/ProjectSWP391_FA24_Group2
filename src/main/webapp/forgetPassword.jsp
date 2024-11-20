<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.Cookie" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Forgot Password</title>
        <link rel="stylesheet" href="../css/auth.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    </head>
    <body>
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="row border rounded-5 p-3 bg-white shadow box-area" style="border-radius: 12px;">
                <div class="col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column left-box"
                     style="background:#103cbe; border-radius: 12px;">
                    <div class="feature-image mt-2 mb-3">
                        <img src="https://i.pinimg.com/736x/aa/cb/ca/aacbca564f3c3af647dd277025ee895f.jpg" class="img-fluid" style="border: solid 2px; border-radius: 5px; width:400px;" />
                    </div>
                    <p class="text-white fs-2" style=" font-weight: bold; " >PAMB</p>
                    <small class="text-white text-wrap text-center mb-2">WELCOME TO THE FLOWER GALLERY</small>
                </div>
                <div class="col-md-6 right-box">
                    <form action="PasswordController" method="post"> 
                        <div class="row align-items-center">
                            <div class="header-text mb-2">
                                <h2>PAMB</h2>
                                <p>A Flower Shop Website</p>
                            </div>
                            <p>Enter your Email to recovery password</p>  
                            <c:if test="${action != null}">
                                <div class="alert-warning mb-3 text-center p-2 ms-3 small" style="width: 50%; font-size: 14px;">No email found!</div>
                            </c:if>
                            <div class="input-group mb-3">
                                <input minlength="8" type="text" name="email" class="form-control form-control-lg bg-light fs-6" placeholder="Enter your Email" value="" required />
                            </div>
                            <div class="input-group mb-3 mt-3 d-flex justify-content-between">
                                <div class="row">
                                    <small>Already have an account? <a href="/LoginController">Sign In</a></small>
                                </div>
                                <div class="row">
                                    <small>Don't have an account? <a href="/RegisterController">Sign Up</a></small>
                                </div> 
                            </div>
                            <div class="input-group mt-3" style="border-radius: 12px;">
                                <button type="submit" class="btn btn-lg btn-custom w-100 fs-6">Send Password</button>
                            </div>    
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>