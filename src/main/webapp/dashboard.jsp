<%-- 
    Document   : dashboard
    Created on : Oct 20, 2024, 2:22:51 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard | PAMP</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
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
                display: flex;
                justify-content: space-between;
                /* Distribute columns evenly */
            }

            .card-column {
                width: 23%;
                /* Adjust width as needed for 4 columns */
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
                margin-bottom: 20px;
                /* Add margin between cards in a column */
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

            .footerIframe {
                border: none;
                width: 100%;
            }

            #salesDataTable tbody {
                max-height: 150px;
                overflow-y: auto;
            }

            .long-content {
                height: auto;
            }
        </style>
    </head>

    <body>

        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <div class="container-fluid fullpagecontent">
            <div class="card-container">

                <!-- Column 1 -->
                <div class="card-column">
                    <h6 class="card-title">Recent Sales</h6>
                    <div class="card">
                        <table class="table table-striped" id="salesDataTable">
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>12345</td>
                                    <td>John Doe</td>
                                    <td>$50.00</td>
                                </tr>
                                <tr>
                                    <td>12346</td>
                                    <td>Jane Smith</td>
                                    <td>$75.50</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <h6 class="card-title">Customer Feedback</h6>
                    <div class="card long-content">
                        <p><strong>Users Reviews Made Today:</strong> 6</p>
                        <p><strong>Accepted Comments Today:</strong> 3</p>
                        <p><strong>Rejected Comments Today:</strong> 1</p>
                        <p><strong>Best Product Rating Today:</strong> Burger</p>
                    </div>
                </div>

                <!-- Column 2 -->
                <div class="card-column">
                    <h6 class="card-title">Sales Trend</h6>
                    <div class="card">
                        <div id="salesTrendChart" style="width: 100%; height: 200px;"></div>
                    </div>
                    <h6 class="card-title">Website Traffic</h6>
                    <div class="card">
                        <p><strong>Unique Visitors:</strong> 2</p>
                        <p><strong>Bots:</strong> 34</p>
                        <p><strong>Scrapers:</strong> 8</p>
                    </div>
                </div>

                <!-- Column 3 -->
                <div class="card-column">
                    <h6 class="card-title">Order Management</h6>
                    <div class="card">
                        <p><strong>Pending Orders:</strong> <span id="pendingOrdersCount"> 3</span></p>
                        <p><strong>Completed Orders Today:</strong> <span id="completedOrdersCount"> 15</span></p>
                    </div>
                    <h6 class="card-title">Vouchers</h6>
                    <div class="card">
                        <p><strong>Total Vouchers:</strong> 56</p>
                        <p><strong>Active Vouchers:</strong> 2</p>
                    </div>
                </div>

                <!-- Column 4 -->
                <div class="card-column">
                    <h6 class="card-title">Stock Management</h6>
                    <div class="card">
                        <p><strong>Total Items:</strong> 120</p>
                        <p><strong>Available Stock:</strong> <span id="availableStockCount">100</span></p>
                        <p><strong>Recently Stock:</strong> <span id="availableStockCount">10</span></p>
                        <p><strong>Low Stock Items:</strong> <span id="lowStockCount">2</span></p>
                        <p><strong>Out of Stock Items:</strong> <span id="outOfStockCount">8</span></p>
                    </div>
                    <h6 class="card-title">Inventory Levels</h6>
                    <div class="card long-content">
                        <p><strong>Overall:</strong> 90%</p>
                        <p><strong>Grading:</strong> Still good!</p>
                        <p>Binhdeeptry has been here.</p>
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <iframe src="adminFooter.jsp" height="50px" class="footerIframe"></iframe>
        </footer>
        <script>
            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawChart);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.
            function drawChart() {

                // Create the data table for the sales trend chart.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Month');
                data.addColumn('number', 'Sales');
                data.addRows([
                    ['Jan', 1000],
                    ['Feb', 1200],
                    ['Mar', 1500],
                    ['Apr', 1800],
                    ['May', 2000]
                            // Add more data points as needed
                ]);

                // Set chart options
                var options = {
                    'title': 'Sales Trend',
                    'width': '100%',
                    'height': 200,
                    'legend': 'none', // Hide legend for simplicity
                    'colors': ['#4285F4'] // Blue color
                };

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.LineChart(document.getElementById('salesTrendChart'));
                chart.draw(data, options);
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>

    </body>
</html>
