<%-- 
    Document   : adminReport
    Created on : Oct 20, 2024, 2:51:29 PM
    Author     : CE181515 - Phan Viet Phat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reports | PAMP</title>
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

            .card-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
        </style>
    </head>

    <body>

        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <div class="container-fluid fullpagecontent">
            <div class="row">

                <div class="col-md-6 card-container">
                    <h6 class="card-title">Sales Data</h6>
                    <div class="card">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Day</th>
                                    <th>Product</th>
                                    <th>Quantity</th>
                                    <th>Revenue</th>
                                </tr>
                            </thead>
                            <tbody id="salesDataTable">
                                <!-- Data will be loaded here -->
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="col-md-6 card-container">
                    <div class="card-header">
                        <h6 class="card-title">Sales Trend</h6>
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="timeframeDropdown"
                                    data-bs-toggle="dropdown" aria-expanded="false">
                                Days
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="timeframeDropdown">
                                <li><a class="dropdown-item" href="#"
                                       onclick="changeTimeframe('Days'); return false;">Days</a></li>
                                <li><a class="dropdown-item" href="#"
                                       onclick="changeTimeframe('Months'); return false;">Months</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="card">
                        <div id="lineChart" style="width: 100%; height: 300px;"></div>
                    </div>
                </div>

            </div>
        </div>

        <footer>
            <iframe src="adminFooter.jsp" height="50px"></iframe>
        </footer>

        <script>
            // Load the Google Charts Visualization API and the corechart package
            google.charts.load('current', {packages: ['corechart']});

            // Sample data for days and months
            var daysData = [
                [1, 2100, 1800], [2, 1600, 1500], [3, 1500, 1650], [4, 1850, 1700], [5, 1700, 1600],
                [6, 1500, 1400], [7, 2200, 1900], [8, 2800, 2300], [9, 2950, 2500], [10, 3800, 2100],
                [11, 3300, 2800], [12, 2900, 2600], [13, 2400, 2200], [14, 2600, 2400], [15, 2800, 2500],
                [16, 3100, 2800], [17, 2900, 2600], [18, 2700, 2400], [19, 2900, 2600], [20, 3200, 2900],
                [21, 3000, 2700], [22, 2800, 2500], [23, 3100, 2800], [24, 3300, 3000], [25, 3200, 2900],
                [26, 2900, 2600], [27, 3100, 2800], [28, 3300, 3000], [29, 3400, 3100], [30, 3300, 3000]
            ];

            var monthsData = [
                ["Jan", 2200, 1900], ["Feb", 1800, 1600], ["Mar", 1700, 1500], ["Apr", 1900, 1700], ["May", 2300, 2000],
                ["Jun", 2900, 2500], ["Jul", 3100, 2700], ["Aug", 2800, 2500], ["Sep", 2600, 2300], ["Oct", 2400, 2100],
                ["Nov", 2700, 2400], ["Dec", 3000, 2700]
            ];

            var chart; // Store chart instance globally
            var options = {
                title: 'Line Chart',
                curveType: 'function',
                legend: {position: 'bottom'},
                hAxis: {
                    title: 'Day',
                    gridlines: {count: 15}
                },
                vAxis: {
                    title: 'Sales'
                },
                colors: ['#4285F4', '#9E9E9E']
            };

            // Function to draw the line chart
            function drawChart(chartData) {
                var data = new google.visualization.DataTable();
                // Add data columns based on timeframe
                if (chartData[0][0].constructor === Number) {
                    data.addColumn('number', 'Day');
                } else {
                    data.addColumn('string', 'Month');
                }
                data.addColumn('number', 'This Week');
                data.addColumn('number', 'Last Week');

                // Add data rows
                data.addRows(chartData);

                // Instantiate and draw the chart
                chart = new google.visualization.LineChart(document.getElementById('lineChart'));
                chart.draw(data, options);
            }

            // Function to change the timeframe (Days/Months)
            function changeTimeframe(timeframe) {
                document.getElementById('timeframeDropdown').textContent = timeframe;

                // Update chart data and redraw
                if (timeframe === 'Days') {
                    drawChart(daysData);
                    options.hAxis.title = 'Day';
                } else {
                    drawChart(monthsData);
                    options.hAxis.title = 'Month';
                }
                chart.draw(chart.getData(), options);
            }

            // Function to load sample data into the table
            function loadSalesData() {
                var tableBody = document.getElementById('salesDataTable');
                var salesData = [
                    [1, "Product A", 10, 500],
                    [2, "Product B", 5, 250],
                    [3, "Product A", 8, 400],
                            // ... add more data rows as needed
                ];

                for (var i = 0; i < salesData.length; i++) {
                    var row = tableBody.insertRow();
                    for (var j = 0; j < salesData[i].length; j++) {
                        var cell = row.insertCell();
                        cell.textContent = salesData[i][j];
                    }
                }
            }

            // Function to initialize the page with data
            function initializePage() {
                loadSalesData();
                drawChart(daysData); // Initial chart with days data
            }

            // Set a callback to run when the Google Visualization API is loaded
            google.charts.setOnLoadCallback(initializePage);

        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
    </body>
</html>
