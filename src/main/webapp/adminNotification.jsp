<%-- 
    Document   : adminNotification
    Created on : Oct 20, 2024, 2:50:33 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Notifications | PAMP</title>
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

            .notification-item {
                border-bottom: 1px solid #eee;
                padding: 15px 0;
            }

            .notification-item:last-child {
                border-bottom: none;
            }

            .unread {
                font-weight: bold;
            }
        </style>
    </head>

    <body>

        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <div class="container-fluid fullpagecontent">
            <div class="row">
                <div class="col-md-12 card-container">
                    <h6 class="card-title">Notifications</h6>
                    <div class="card">
                        <div class="notification-list">
                            <!-- Sample Notifications (replace with dynamic data) -->
                            <div class="notification-item unread">
                                <p><strong>New Order Received:</strong> Order #12345 placed by user123.</p>
                                <small class="text-muted">10 minutes ago</small>
                            </div>
                            <div class="notification-item">
                                <p><strong>Low Stock Alert:</strong> Roses are running low in inventory.</p>
                                <small class="text-muted">1 hour ago</small>
                            </div>
                            <div class="notification-item">
                                <p><strong>User Registered:</strong> A new user, flowerlover, has registered.</p>
                                <small class="text-muted">3 hours ago</small>
                            </div>
                            <!-- Add more notifications as needed -->
                        </div>
                        <div class="text-center mt-3">
                            <button class="btn btn-primary" id="markAllReadBtn">Mark All as Read</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <iframe src="adminFooter.jsp" height="50px"></iframe>
        </footer>

        <script>
            document.getElementById('markAllReadBtn').addEventListener('click', function () {
                var notifications = document.querySelectorAll('.notification-item');
                notifications.forEach(function (notification) {
                    notification.classList.remove('unread');
                });
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
    </body>
</html>
