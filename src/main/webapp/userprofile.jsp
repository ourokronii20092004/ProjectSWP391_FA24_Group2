<%-- 
    Document   : userprofile
    Created on : Oct 20, 2024, 11:10:21 AM
    Author     : phanp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
</head>

<body>

    <!-- Navigation Bar -->
    <div class="d-flex flex-column min-vh-100">
            <header class="d-flex justify-content-between align-items-center p-3 border-bottom">
                <a href="#" class="d-flex align-items-center text-decoration-none">

                    <span class="h5 ms-2">PAMB</span>
                </a>
                <nav class="d-none d-lg-flex gap-4">
                    <a href="#" class="text-decoration-none text-muted">Home</a>
                    <a href="#" class="text-decoration-none text-muted">Shop</a>
                    <a href="#" class="text-decoration-none text-muted">About</a>
                    <a href="#" class="text-decoration-none text-muted">Contact</a>
                </nav>
                <div class="d-flex align-items-center gap-3">
                    <a class="nav-link" href="#">Hello, Nguyen Nhat Dang</a>
                    <a href="cart.html">
                        <img src="../img/shopping-cart.svg" alt="Cart" class="cart-icon">
                    </a>
                    <!--                     <svg xmlns="" width="24" height="24" fill="none" stroke="currentColor"
                                            stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-6 w-6">
                                            <path
                                                d="M12 12c2.5 0 4.5-2 4.5-4.5S14.5 3 12 3 7.5 5 7.5 7.5 9.5 12 12 12zm0 2c-5.5 0-9 2-9 6v2h18v-2c0-4-3.5-6-9-6z" />
                                        </svg> -->
                </div>
            </header>

    <!-- Main Content -->
 <div class="container my-5">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-4 mb-4">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        Account
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><a href="#" class="text-dark">Account Information</a></li>
                        <li class="list-group-item"><a href="#" class="text-dark">Change Password</a></li>
                        <li class="list-group-item"><a href="#" class="text-dark">History Bought</a></li>
                        <li class="list-group-item"><a href="#" class="text-dark">Logout</a></li>
                    </ul>
                </div>
            </div>

            <!-- Profile Form -->
            <div class="col-md-8">
                <h3 class="mb-4 text-center">Account Information</h3>

                <!-- Avatar Section -->
                <div class="text-center mb-4">
                    <img id="avatarPreview" src="../img/avt1.jpg" alt="Avatar" class="rounded-circle border border-primary" width="150" height="150">
                    <div class="mt-3">
                        <button type="button" class="btn btn-outline-secondary" id="changeAvatarButton">Change Avatar</button>
                        <input type="file" id="avatarInput" accept="image/*" style="display: none;">
                    </div>
                </div>

                <form id="profileForm" class="p-4 bg-white rounded shadow-sm">
                    <div class="mb-3">
                        <label for="email" class="form-label">* Your Email:</label>
                        <input type="email" class="form-control" id="email" value="dangnnce180010@fpt.edu.vn" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="name" class="form-label">* Last Name:</label>
                        <input type="text" class="form-control" id="name" value="(K18 CT)" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="surname" class="form-label">* First Name: </label>
                        <input type="text" class="form-control" id="surname" value="Nguyen Nhat Dang" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">* Phone Number:</label>
                        <input type="tel" class="form-control" id="phone" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">* Address:</label>
                        <input type="text" class="form-control" id="address" readonly>
                    </div>

                    <button type="button" class="btn btn-primary w-100" id="editButton">Edit</button>
                    <button type="submit" class="btn btn-success w-100 mt-3" id="saveButton" style="display:none;">Save</button>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const editButton = document.getElementById("editButton");
            const saveButton = document.getElementById("saveButton");
            const formFields = document.querySelectorAll("input[readonly]");
            const changeAvatarButton = document.getElementById("changeAvatarButton");
            const avatarInput = document.getElementById("avatarInput");
            const avatarPreview = document.getElementById("avatarPreview");

            // Enable editing on clicking "Edit"
            editButton.addEventListener("click", function () {
                formFields.forEach(field => {
                    if (field.id !== "email") { // Keep email readonly
                        field.removeAttribute("readonly");
                    }
                });
                editButton.style.display = "none";
                saveButton.style.display = "block";
            });

            // Change Avatar Button
            changeAvatarButton.addEventListener("click", function () {
                avatarInput.click(); // Trigger file input when button is clicked
            });

            // Preview the new avatar when user selects an image
            avatarInput.addEventListener("change", function () {
                const file = avatarInput.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        avatarPreview.src = e.target.result; // Set the preview image source
                    }
                    reader.readAsDataURL(file); // Read the image file as a data URL
                }
            });

            // Show confirmation message on submitting the form
            const form = document.getElementById("profileForm");
            form.addEventListener("submit", function (e) {
                e.preventDefault(); // Prevent the form from submitting immediately
                alert("Profile updated successfully!");
                // Optionally submit the form here using AJAX
            });
        });
    </script>
</body>

</html>