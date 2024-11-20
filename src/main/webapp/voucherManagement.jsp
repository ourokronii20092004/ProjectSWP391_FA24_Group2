<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="vt" uri="/WEB-INF/tlds/voucherTags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Voucher Management | PAMP</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                color: #343a40;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
                font-family: sans-serif;
            }
            .container-fluid {
                padding: 2rem 1rem;
                flex: 1;
                overflow-y: auto;
            }
            .card {
                border: none;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
                padding: 2rem;
                background-color: white;
                margin-bottom: 20px;
            }
            .card-title {
                color: #261d6a;
                font-weight: 600;
                margin-bottom: 1.5rem;
            }
            .table-responsive {
                overflow-x: auto;
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
            #voucherListTable th:nth-child(1), #voucherListTable td:nth-child(1) {
                width: 4%;
            }
            #voucherListTable th:nth-child(2), #voucherListTable td:nth-child(2) {
                width: 10%;
            }
            #voucherListTable th:nth-child(3), #voucherListTable td:nth-child(3) {
                width: 12%;
            }
            #voucherListTable th:nth-child(4), #voucherListTable td:nth-child(4) {
                width: 12%;
            }
            #voucherListTable th:nth-child(5), #voucherListTable td:nth-child(5) {
                width: 10%;
            }
            #voucherListTable th:nth-child(6), #voucherListTable td:nth-child(6) {
                width: 13%;
            }
            #voucherListTable th:nth-child(7), #voucherListTable td:nth-child(7) {
                width: 13%;
            }
            #voucherListTable th:nth-child(8), #voucherListTable td:nth-child(8) {
                width: 6%;
            }
            #voucherListTable th:nth-child(9), #voucherListTable td:nth-child(9) {
                width: 20%;
            }
            .voucher-code {
                font-family: monospace;
                margin-top: 0.5rem;
                display: block;
            }
            .btn-primary {
                background-color: #261d6a;
                border-color: #261d6a;
            }
            .btn-sm {
                padding: 0.25rem 0.5rem;
                font-size: 0.875rem;
                line-height: 1.5;
                border-radius: 0.2rem;
            }
            .active-icon {
                color: green;
            }
            .inactive-icon {
                color: red;
            }
            .invalid-feedback {
                display: none;
                width: 100%;
                margin-top: 0.25rem;
                font-size: 80%;
                color: #dc3545;
            }
            .is-invalid .invalid-feedback {
                display: block;
            }
            footer {
                background-color: #3E3E3E;
                color: white;
                text-align: center;
                padding: 1rem 0;
                margin-top: auto;
                width: 100%;
            }
            iframe {
                border: none;
                width: 100%;
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
            .modal-body label {
                align-self: flex-start;
                font-weight: bold;
            }
            .modal-body .row {
                align-items: flex-start;
            }
            .modal-lg {
                max-width: 800px;
            }
            .server-side-error {
                color: #dc3545;
                margin-top: 0.25rem;
            }
        </style>
    </head>
    <body>
        <nav>
            <iframe src="adminNavbar.jsp" height="60px"></iframe>  
        </nav>
        <div class="container-fluid">
            <div class="card">
                <div class="title-and-buttons">
                    <h5 class="card-title">Voucher Management</h5>
                    <div>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addVoucherModal">Add Voucher</button>
                    </div>
                </div>
                <c:if test="${not empty noResultsMessage}"><div class="alert alert-warning" role="alert">${noResultsMessage}</div></c:if>
                <c:if test="${not empty discountType}">
                    <script>
                        alert("Percentage value cannot exceed 1 (100%). It has been set to flat discount.");
                        document.getElementById('discountType').value = '${discountType}';
                        document.getElementById('discountValue').value = '${discountValue}';
                    </script>
                </c:if>
                <c:if test="${not empty sessionScope.errorMessage}">
                    <div class="alert alert-danger" role="alert">${sessionScope.errorMessage}</div>
                    <c:remove var="errorMessage" scope="session"/>
                </c:if>
                <form action="VoucherController" method="GET" class="search-and-filter">
                    <input type="hidden" name="action" value="search"> 
                    <div class="search-input-group"><input type="text" name="searchName" placeholder="Search for vouchers..." class="form-control"></div>
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
                        <button type="submit" class="search-button"><img src="img/icon/search.svg" alt="Search"></button>
                    </div>
                </form>
                <div class="table-responsive">
                    <table class="table" id="voucherListTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Code</th>
                                <th>Discount Type</th>
                                <th>Value</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${voucherList}" var="voucher">
                                <tr>
                                    <td>${voucher.voucherID}</td>
                                    <td>${voucher.voucherName}</td>
                                    <td>${voucher.voucherCode}</td>
                                    <td><c:choose><c:when test="${voucher.type}">Percentage</c:when><c:otherwise>Flat</c:otherwise></c:choose></td>
                                    <td>${voucher.value}</td>
                                    <td><vt:formatLocalDateTime dateTime="${voucher.startDate}" pattern="HH:mm dd MMM yyyy"/></td>
                                    <td><vt:formatLocalDateTime dateTime="${voucher.endDate}" pattern="HH:mm dd MMM yyyy"/></td>
                                    <td><c:choose><c:when test="${voucher.isActive}"><span class="active-icon">✓</span></c:when><c:otherwise><span class="inactive-icon">✘</span></c:otherwise></c:choose></td>
                                            <td>
                                                    <button type="button" class="btn btn-sm btn-warning editVoucherBtn" data-bs-toggle="modal" data-bs-target="#editVoucherModal" data-voucher-id="${voucher.voucherID}" data-voucher-code="${voucher.voucherCode}" data-voucher-type="${voucher.type}" data-voucher-value="${voucher.value}" data-voucher-startdate="${voucher.startDate}" data-voucher-enddate="${voucher.endDate}" data-voucher-name="${voucher.voucherName}">Edit</button>
                                        <c:choose>
                                            <c:when test="${voucher.isActive}"><a href="VoucherController?action=deactivate&voucherId=${voucher.voucherID}" class="btn btn-sm btn-secondary">Deactivate</a></c:when>
                                            <c:otherwise><a href="VoucherController?action=activate&voucherId=${voucher.voucherID}" class="btn btn-sm btn-success">Activate</a></c:otherwise>
                                        </c:choose>
                                        <form action="VoucherController" method="POST" style="display: inline;">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="voucherId" value="${voucher.voucherID}">
                                            <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addVoucherModal" tabindex="-1" aria-labelledby="addVoucherModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addVoucherModalLabel">Add Voucher</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="VoucherController" method="POST" id="addVoucherForm" novalidate>
                            <input type="hidden" name="action" value="add">
                            <div class="mb-3"><label for="voucherCode" class="col-form-label">Voucher Code:</label><input type="text" class="form-control" id="voucherCode" name="voucherCode" required><div class="invalid-feedback voucherCodeError"></div></div>
                            <div class="mb-3"><label for="voucherName" class="col-form-label">Voucher Name:</label><input type="text" class="form-control" id="voucherName" name="voucherName" required><div class="invalid-feedback voucherNameError"></div></div>
                            <div class="mb-3"><label for="discountType" class="col-form-label">Discount Type:</label><select class="form-select" id="discountType" name="discountType" onchange="updateDiscountValueFormat()" required><option value="TRUE">Percentage</option><option value="FALSE">Flat</option></select><div class="invalid-feedback discountTypeError"></div></div>
                            <div class="mb-3"><label for="discountValue" class="col-form-label">Discount Value:</label><input type="number" class="form-control" id="discountValue" name="discountValue" step="0.01" required><div class="invalid-feedback discountValueError"></div></div>
                            <div class="mb-3 row">
                                <div class="col-md-6"><label for="startDate" class="col-form-label">Start Date:</label><input type="datetime-local" class="form-control datetime-local-input" id="startDate" name="startDate" required><div class="invalid-feedback startDateError"></div></div>
                                <div class="col-md-6"><label for="endDate" class="col-form-label">End Date:</label><input type="datetime-local" class="form-control datetime-local-input" id="endDate" name="endDate" required><div class="invalid-feedback endDateError"></div></div>
                            </div>
                            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button><button type="submit" class="btn btn-primary">Add Voucher</button></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="editVoucherModal" tabindex="-1" aria-labelledby="editVoucherModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header"><h5 class="modal-title" id="editVoucherModalLabel">Edit Voucher</h5><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>
                    <div class="modal-body">
                        <form action="VoucherController" method="POST" id="editVoucherForm" novalidate>
                            <input type="hidden" name="action" value="edit"><input type="hidden" name="voucherId" id="editVoucherId" value="">
                            <div class="mb-3"><label for="voucherCode" class="col-form-label">Voucher Code:</label><input type="text" class="form-control" id="voucherCode" name="voucherCode" required><div class="invalid-feedback voucherCodeError"></div></div>
                            <div class="mb-3"><label for="voucherName" class="col-form-label">Voucher Name:</label><input type="text" class="form-control" id="voucherName" name="voucherName" required><div class="invalid-feedback voucherNameError"></div></div>
                            <div class="mb-3"><label for="discountType" class="col-form-label">Discount Type:</label><select class="form-select" id="discountType" name="discountType" onchange="updateDiscountValueFormat()" required><option value="TRUE">Percentage</option><option value="FALSE">Flat</option></select><div class="invalid-feedback discountTypeError"></div></div>
                            <div class="mb-3"><label for="discountValue" class="col-form-label">Discount Value:</label><input type="number" class="form-control" id="discountValue" name="discountValue" step="0.01" required><div class="invalid-feedback discountValueError"></div></div>
                            <div class="mb-3 row">
                                <div class="col-md-6"><label for="startDate" class="col-form-label">Start Date:</label><input type="datetime-local" class="form-control datetime-local-input" id="startDate" name="startDate" required><div class="invalid-feedback startDateError"></div></div>
                                <div class="col-md-6"><label for="endDate" class="col-form-label">End Date:</label><input type="datetime-local" class="form-control datetime-local-input" id="endDate" name="endDate" required><div class="invalid-feedback endDateError"></div></div>
                            </div>
                            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button><button type="submit" class="btn btn-primary">Save Changes</button></div>
                        </form>
                        <div class="errorMessage alert alert-danger" style="display: none;"></div>
                    </div>
                </div>
            </div>
        </div>

        <footer><iframe src="adminFooter.jsp" height="70px"></iframe></footer>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/voucherManagement.js"></script>
    </body>
</html>