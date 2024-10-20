<%-- 
    Document   : login
    Created on : Oct 20, 2024, 12:25:36 PM
    Author     : phanp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
                    <img src="../img/auth-background.jpg" class="img-fluid" style="width:250px;" />
                </div>
                <p class="text-white fs-2">Some content here</p>
                <small class="text-white text-wrap text-center">Description or something here</small>
            </div>

            <div class="col-md-6 right-box">
                <div class="row align-items-center">
                    <div class="header-text mb-4">
                        <h2>PAMB</h2>
                        <p>A Flower Shop Website</p>
                    </div>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control form-control-lg bg-light fs-6" placeholder="Username"
                            required />
                    </div>
                    <div class="input-group mb-1">
                        <input type="password" class="form-control form-control-lg bg-light fs-6" placeholder="Password"
                            required />
                    </div>
                    
                    <div class="input-group mb-3 mt-3 d-flex justify-content-between">
                        <div class="row">
                            <small>Don't have an account? <a href="#">Sign Up</a></small>
                        </div> 
                        <div class="row">
                            <small><a href="#">Forget Password?</a></small>
                        </div>
                    </div>

                    <div class="form-check ms-3 mb-3">
                        <input type="checkbox" class="form-check-input" id="formCheck" />
                        <label for="formCheck"><small>Remember Me</small></label>
                    </div>

                    <div class="input-group mb-3" style="border-radius: 12px;">
                        <button class="btn btn-lg btn-custom w-100 fs-6">Login</button>
                    </div>    
                    <div class="input-group mb-3" style="border-radius: 12px;">
                        <button class="btn btn-lg btn-custom w-100 fs-6"><img src="../img/google.png" style="width: 20px;" class="me-2">Sign In With Google</button>
                    </div>
                                  
                </div>
            </div>
        </div>
    </div>
</body>

</html>
