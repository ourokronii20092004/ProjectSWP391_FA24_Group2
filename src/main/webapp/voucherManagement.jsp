<%-- 
    Document   : voucherManagement
    Created on : Nov 14, 2024, 11:38:27 AM
    Author     : Le Trung Hau - CE180481
--%>

<%-- 
    Document   : voucherManagement.jsp
    Created on : Nov 9, 2024, 1:39:51 PM
    Author     : Le Trung Hau - CE180481
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Voucher Management | PAMP</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

        <style>
            body {
                background-color: #f8f9fa;
                color: #343a40;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
            }

            .container-fluid {
                padding: 2rem 1rem;
                flex: 1;
                overflow-y: auto;
                height: calc(100vh - 130px);

            }

            .card {
                border: none;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
                padding: 2rem;
                background-color: white;
            }

            .card-title {
                color: #261d6a;
                font-weight: 600;
                margin-bottom: 1.5rem;
            }

            #productListTable {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 1rem;
                table-layout: fixed;


            }

            #productListTable th, #productListTable td {
                padding: 1.5rem 1rem;
                border-bottom: 1px solid #dee2e6;
                text-align: left;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;

            }
            #productListTable img {
                width: 70px;
                height: 70px;
                object-fit: cover;
                border-radius: 5px;
                margin-right: 1rem;
            }

            #productListTable th:first-child, #productListTable td:first-child {
                width: 5%;
            } /* ID */
            #productListTable th:nth-child(2), #productListTable td:nth-child(2) {
                width: 10%;
            } /* Image */
            #productListTable th:nth-child(3), #productListTable td:nth-child(3) {
                width: 18%;
            } /* Name */
            #productListTable th:nth-child(4), #productListTable td:nth-child(4) {
                width: 22%;
            } /* Description */
            #productListTable th:nth-child(5), #productListTable td:nth-child(5) {
                width: 10%;
            } /* Price */
            #productListTable th:nth-child(6), #productListTable td:nth-child(6) {
                width: 10%;
            } /* Category ID */
            #productListTable th:nth-child(7), #productListTable td:nth-child(7) {
                width: 10%;
            } /* Stock */
            #productListTable th:last-child, #productListTable td:last-child {
                width: 15%;
            } /* Actions */

            #productListTable input[type="checkbox"] {
                transform: scale(1.5);
                accent-color: #261d6a;
            }

            .table-responsive {
                overflow-x: auto;
            }

            .btn-primary {
                background-color: #261d6a;
                border-color: #261d6a;
            }

            footer {
                background-color: #3E3E3E;
                color: white;
                text-align: center;
                padding: 1rem 0;
                margin-top: auto;
            }

            iframe {
                border: none;
                width: 100%;
            }

            html, body {
                overflow-y: auto;
            }

            .title-and-buttons {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 1.5rem;
            }

            .search-and-filter {
                display: flex;
                align-items: center;
                gap: 1rem;
                margin-bottom: 1rem;
            }

            .search-input-group {
                width: 50%; 
            }

            .filter-group {
                width: 50%;
                display: flex;
                gap: 1rem; 
            }

            .flex-grow-1 {
                flex-grow: 1;
            }

            .search-button {
                background: none;
                border: none;
                padding: 0;
                cursor: pointer;
            }

            .search-button img {
                width: 20px;
                height: 20px;
            }

            .modal-body {
                display: flex;
                flex-direction: column;

            }

            .col-md-6 textarea {
                height: border-box;
                resize: none;
                max-height: inherit;
            }

            .modal-body label{
                align-self: flex-start;
                font-weight: bold;
            }

            .modal-body .row {
                align-items: flex-start;
                max-height: fit-content;
                height: fit-content;
            }
            .image-preview {
                max-width: 100%;
                height: auto;
                object-fit: cover;
                border: 1px solid #ced4da;
                margin-bottom: 0.5rem;
            }

            .image-preview-container{
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
                width: 100%;
            }

            .search-and-filter {
                display: flex;
                align-items: center;
                gap: 1rem;
                margin-bottom: 1rem;
            }

            .search-and-filter select {
                max-width: 150px;
            }

            /* for date and time inputs in modals */
            .datetime-local-input {
                /* IDK MAN HELPPPPP*/
            }

            #voucherListTable {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 1rem;
                table-layout: fixed;
            }

            #voucherListTable th, #voucherListTable td {
                padding: 1.5rem 1rem;
                border-bottom: 1px solid #dee2e6;
                text-align: left;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

            /* Responsive column widths */
            #voucherListTable th:nth-child(1), #voucherListTable td:nth-child(1) {
                width: 5%;
            }  /* ID */
            #voucherListTable th:nth-child(2), #voucherListTable td:nth-child(2) {
                width: 15%;
            } /* Name */
            #voucherListTable th:nth-child(3), #voucherListTable td:nth-child(3) {
                width: 15%;
            } /* Discount Type */
            #voucherListTable th:nth-child(4), #voucherListTable td:nth-child(4) {
                width: 10%;
            } /* Value */
            #voucherListTable th:nth-child(5), #voucherListTable td:nth-child(5) {
                width: 15%;
            } /* Start Date */
            #voucherListTable th:nth-child(6), #voucherListTable td:nth-child(6) {
                width: 15%;
            } /* End Date */
            #voucherListTable th:nth-child(7), #voucherListTable td:nth-child(7) {
                width: 25%;
            } /* Actions */

        </style>
    </head>
    <body>

        <iframe src="adminNavbar.jsp" height="60px"></iframe>

        <div class="container-fluid">
            <div class="card">
                <div class="title-and-buttons">
                    <h5 class="card-title">Voucher Management</h5>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addVoucherModal">
                        Add Voucher
                    </button>
                </div>

                <c:if test="${not empty noResultsMessage}">
                    <div class="alert alert-warning" role="alert">
                        ${noResultsMessage}
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>




                <form action="VoucherController" method="GET" class="search-and-filter">
                    <div class="search-input-group">
                        <input type="text" name="searchName" placeholder="Search for vouchers..." class="form-control">
                    </div>

                    <div class="filter-group">
                        <select name="discountType" class="form-select">
                            <option value="-1">All Types</option>
                            <option value="0">Flat Reduction</option>
                            <option value="1">Percentage Reduction</option>
                        </select>
                        <select name="isActive" class="form-select">
                            <option value="-1">All Status</option>
                            <option value="1">Active</option>
                            <option value="0">Inactive</option>
                        </select>
                        <button type="submit" class="search-button">
                            <img src="img/icon/search.svg" alt="Search">
                        </button>
                    </div>
                </form>




                <form action="VoucherController" method="POST" id="voucherActionsForm">
                    <input type="hidden" name="action" value="bulkAction">

                    <div class="table-responsive">
                        <table class="table" id="voucherListTable">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Discount Type</th>
                                    <th>Value</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${voucherList}" var="voucher">
                                    <tr>
                                        <td>${voucher.voucherID}</td>
                                        <td>${voucher.voucherName}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${voucher.type}">Percentage</c:when>
                                                <c:otherwise>Flat</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${voucher.value}</td>
                                        <td>${voucher.startDate}</td>
                                        <td>${voucher.endDate}</td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-warning editVoucherBtn" data-bs-toggle="modal" data-bs-target="#editVoucherModal"
                                                    data-voucher-id="${voucher.voucherID}" data-voucher-code="${voucher.voucherCode}" data-discount-type="${voucher.type}"
                                                    data-discount-value="${voucher.value}" data-start-date="${voucher.startDate}" data-end-date="${voucher.endDate}" data-voucher-name="${voucher.voucherName}"
                                                    data-is-active="${voucher.isActive}">
                                                Edit
                                            </button>
                                            <c:choose>
                                                <c:when test="${voucher.isActive}">
                                                    <a href="VoucherController?action=deactivate&voucherId=${voucher.voucherID}" class="btn btn-sm btn-secondary">Deactivate</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="VoucherController?action=activate&voucherId=${voucher.voucherID}" class="btn btn-sm btn-success">Activate</a>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="VoucherController?action=delete&voucherId=${voucher.voucherID}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this voucher?')">Delete</a>


                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </form>
            </div>
        </div>


        <!-- Add Voucher Modal -->
        <div class="modal fade" id="addVoucherModal" tabindex="-1" aria-labelledby="addVoucherModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addVoucherModalLabel">Add Voucher</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="VoucherController" method="POST" id="addVoucherForm">
                            <input type="hidden" name="action" value="add">
                            <div class="mb-3">
                                <label for="voucherCode" class="col-form-label">Voucher Code:</label>
                                <input type="text" class="form-control" id="voucherCode" name="voucherCode" value="${generatedVoucherCode}" required>
                            </div>

                            <div class="mb-3">
                                <label for="discountType" class="col-form-label">Discount Type:</label>
                                <select class="form-select" id="discountType" name="discountType" required onchange="updateDiscountValueFormat()">
                                    <option value="1">Percentage</option>
                                    <option value="0">Flat</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="discountValue" class="col-form-label">Discount Value:</label>
                                <input type="number" class="form-control" id="discountValue" name="discountValue" step="0.01" required>
                            </div>


                            <div class="mb-3 row">
                                <div class="col-md-6">
                                    <label for="startDate" class="col-form-label">Start Date:</label>
                                    <input type="datetime-local" class="form-control datetime-local-input" id="startDate" name="startDate" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="endDate" class="col-form-label">End Date:</label>
                                    <input type="datetime-local" class="form-control datetime-local-input" id="endDate" name="endDate" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="voucherName" class="col-form-label">Voucher Name:</label>
                                <input type="text" class="form-control" id="voucherName" name="voucherName" required>
                            </div>

                            <div class="mb-3">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="1" id="isActive" name="isActive" checked>
                                    <label class="form-check-label" for="isActive">
                                        Is Active
                                    </label>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add Voucher</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Voucher Modal -->
        <div class="modal fade" id="editVoucherModal" tabindex="-1" aria-labelledby="editVoucherModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editVoucherModalLabel">Edit Voucher</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="VoucherController" method="POST" id="editVoucherForm">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="voucherId" id="editVoucherId">

                            <div class="mb-3">
                                <label for="editVoucherCode" class="col-form-label">Voucher Code:</label>
                                <input type="text" class="form-control" id="editVoucherCode" name="voucherCode" required>
                            </div>

                            <div class="mb-3">
                                <label for="editDiscountType" class="col-form-label">Discount Type:</label>
                                <select class="form-select" id="editDiscountType" name="discountType" required onchange="updateDiscountValueFormat()">
                                    <option value="1">Percentage</option>
                                    <option value="0">Flat</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="editDiscountValue" class="col-form-label">Discount Value:</label>
                                <input type="number" class="form-control" id="editDiscountValue" name="discountValue" step="any" required>
                            </div>

                            <div class="mb-3 row">
                                <div class="col-md-6">
                                    <label for="editStartDate" class="col-form-label">Start Date:</label>
                                    <input type="datetime-local" class="form-control datetime-local-input" id="editStartDate" name="startDate" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="editEndDate" class="col-form-label">End Date:</label>
                                    <input type="datetime-local" class="form-control datetime-local-input" id="editEndDate" name="endDate" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="editVoucherName" class="col-form-label">Voucher Name:</label> <input type="text" class="form-control" id="editVoucherName" name="voucherName" required>
                            </div>

                            <div class="mb-3">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="1" id="editIsActive" name="isActive">
                                    <label class="form-check-label" for="editIsActive">
                                        Is Active
                                    </label>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <footer>
            <iframe src="adminFooter.jsp" height="70px"></iframe>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/voucherManagement.js"></script>
        <script>
                                    // Add JavaScript for form validation, dynamic discount formatting, and any other client-side logic

                                    // Function to update discount value format based on discount type
                                    function updateDiscountValueFormat() {

                                        var discountType = document.getElementById("discountType").value;
                                        var discountValueInput = document.getElementById("discountValue");

                                        if (discountType === "1") {

                                            discountValueInput.step = "0.01"; //for percentage
                                            discountValueInput.max = "100";
                                            discountValueInput.min = "0";
                                            discountValueInput.type = "number"

                                        } else {

                                            discountValueInput.step = "any"; // Flat discount; no restrictions
                                            discountValueInput.removeAttribute("max");
                                            discountValueInput.removeAttribute("min");
                                            discountValueInput.type = "number"
                                        }

                                    }
                                    const editButtons = document.querySelectorAll('.editVoucherBtn');
                                    editButtons.forEach(button => {
                                        button.addEventListener('click', function () {
                                            const modal = document.getElementById('editVoucherModal');
                                            const form = modal.querySelector('form');

                                            const voucherId = this.getAttribute('data-voucher-id');
                                            const voucherCode = this.getAttribute('data-voucher-code');
                                            const discountType = this.getAttribute('data-discount-type');
                                            const discountValue = this.getAttribute('data-discount-value');
                                            const startDate = this.getAttribute('data-start-date');
                                            const endDate = this.getAttribute('data-end-date');
                                            const voucherName = this.getAttribute('data-voucher-name');
                                            const isActive = this.getAttribute('data-is-active');


                                            form.querySelector('[name="voucherId"]').value = voucherId;
                                            form.querySelector('[name="voucherCode"]').value = voucherCode;
                                            form.querySelector('[name="discountType"]').value = discountType;
                                            form.querySelector('[name="discountValue"]').value = discountValue;
                                            form.querySelector('[name="startDate"]').value = startDate;
                                            form.querySelector('[name="endDate"]').value = endDate;
                                            form.querySelector('[name="voucherName"]').value = voucherName;
                                            form.querySelector('[name="isActive"]').checked = isActive === 'true';


                                        });
                                    });

                                    //SelectAll checkbox function
                                    const selectAllCheckbox = document.getElementById('selectAllCheckbox');

                                    const individualCheckboxes = document.querySelectorAll('input[name="selectedVouchers"]');

                                    selectAllCheckbox.addEventListener('change', function () {

                                        individualCheckboxes.forEach(checkbox => {

                                            checkbox.checked = this.checked;
                                        });
                                    });

        </script>
    </body>
</html>