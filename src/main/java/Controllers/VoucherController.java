/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.VoucherDAO;
import Models.Voucher;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Le Trung Hau - CE180481
 */
@WebServlet(name = "VoucherController", urlPatterns = {"/VoucherController"})
public class VoucherController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VoucherController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VoucherController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        VoucherDAO voucherDAO = new VoucherDAO();
        if (action == null) {
            action = "list";
        }
        System.out.println("--- Voucher Controller doGet --- ");
        try {
            ArrayList<Voucher> voucherList = null;
            System.out.println("doGet: " + action);
            switch (action) {
                case "list":
                    voucherList = voucherDAO.getAllVouchers();
                    break;
                case "activate":
                    int voucherIdToActivate = Integer.parseInt(request.getParameter("voucherId"));
                    voucherDAO.activateVoucher(voucherIdToActivate);
                    response.sendRedirect("VoucherController?action=list");
                    return;
                case "deactivate":
                    int voucherIdToDeactivate = Integer.parseInt(request.getParameter("voucherId"));
                    voucherDAO.deactivateVoucher(voucherIdToDeactivate);
                    response.sendRedirect("VoucherController?action=list");
                    return;
                case "search": {
                    String searchName = request.getParameter("searchName");
                    String discountTypeStr = request.getParameter("discountType");
                    String isActiveStr = request.getParameter("isActive");
                    try {
                        Integer discountType = (discountTypeStr == null || discountTypeStr.equals("-1")) ? null : Integer.parseInt(discountTypeStr);
                        Boolean isActive = null;// null is all, no value changed then it's all
                        if (isActiveStr != null) {
                            if (isActiveStr.equals("1")) {
                                isActive = true;
                            } else if (isActiveStr.equals("0")) {
                                isActive = false;
                            }
                        }
                        if (discountType == null) {
                            if (isActive == null) {
                                voucherList = voucherDAO.getAllVouchers();
                            } else {
                                voucherList = voucherDAO.searchVouchersAllDiscount(searchName, isActive);
                            }
                        } else {
                            if (isActive == null) {
                                voucherList = voucherDAO.getAllVouchers();
                            } else {
                                voucherList = voucherDAO.searchVouchers(searchName, discountType, isActive);
                            }
                        }
                        if (voucherList.isEmpty()) {
                            request.setAttribute("noResultsMessage", "No vouchers found matching your criteria.");
                            voucherList = voucherDAO.getAllVouchers();
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("NumberFormatException caught: Invalid discount type.");
                        request.setAttribute("errorMessage", "Invalid discount type. Please enter a valid number.");
                        voucherList = voucherDAO.getAllVouchers();
                    } catch (Exception e) {
                        System.out.println("Exception caught: Unexpected error during search.");
                        request.setAttribute("errorMessage", "An unexpected error occurred during search.");
                        voucherList = voucherDAO.getAllVouchers();
                    }
                    break;
                }
                default:
                    System.out.println("DEFAULT ACTION");
                    voucherList = voucherDAO.getAllVouchers();
                    break;
            }
            request.setAttribute("voucherList", voucherList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("voucherManagement.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException ex) {
            Logger.getLogger(VoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        VoucherDAO voucherDAO = new VoucherDAO();

        if (action == null) {
            action = "list";
        }

        System.out.println("--- Voucher Controller doPost ---");
        System.out.println("doPost: " + action);

        try {
            boolean success = false;

            switch (action) {
                case "add":
                    if (!validateVoucherAdd(request, response, voucherDAO)) {
                        response.sendRedirect("VoucherController?action=list");
                        return;
                    }
                    String voucherCode = request.getParameter("voucherCode");
                    boolean discountType = Boolean.parseBoolean(request.getParameter("discountType"));
                    float discountValue = Float.parseFloat(request.getParameter("discountValue"));
                    LocalDateTime startDate = LocalDateTime.parse(request.getParameter("startDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime endDate = LocalDateTime.parse(request.getParameter("endDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime createdAt = LocalDateTime.now();
                    LocalDateTime updatedAt = LocalDateTime.now();
                    String voucherName = request.getParameter("voucherName");

                    Voucher newVoucher = new Voucher(0, voucherCode, discountType, discountValue, startDate, endDate, voucherName, true, createdAt, updatedAt);
                    voucherDAO.addVoucher(newVoucher);
                    success = true;
                    break;

                case "edit":
                    if (!validateVoucherEdit(request, response, voucherDAO)) {
                        response.sendRedirect("VoucherController?action=list");
                        return;
                    }
                    int voucherId = Integer.parseInt(request.getParameter("voucherId"));
                    System.out.println("doPost got id of: " + voucherId);
                    String voucherCodeEdit = request.getParameter("voucherCode");
                    boolean discountTypeEdit = Boolean.parseBoolean(request.getParameter("discountType"));
                    float discountValueEdit = Float.parseFloat(request.getParameter("discountValue"));

                    LocalDateTime startDateEdit = LocalDateTime.parse(request.getParameter("startDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime endDateEdit = LocalDateTime.parse(request.getParameter("endDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    String voucherNameEdit = request.getParameter("voucherName");
                    LocalDateTime updatedAtEdit = LocalDateTime.now();

                    Voucher existingVoucher = voucherDAO.getVoucherById(voucherId);
                    if (existingVoucher != null) {
                        existingVoucher.setVoucherCode(voucherCodeEdit);
                        existingVoucher.setType(discountTypeEdit);
                        existingVoucher.setValue(discountValueEdit);
                        existingVoucher.setStartDate(startDateEdit);
                        existingVoucher.setEndDate(endDateEdit);
                        existingVoucher.setVoucherName(voucherNameEdit);
                        existingVoucher.setUpdateAt(updatedAtEdit);
                        voucherDAO.updateVoucher(existingVoucher);
                        success = true;
                    } else {
                        request.setAttribute("errorMessage", "Voucher not found.");
                        response.sendRedirect("VoucherController?action=list");
                        return;
                    }
                    break;

                case "delete":
                    int voucherIdToDelete = Integer.parseInt(request.getParameter("voucherId"));
                    if (voucherDAO.deleteVoucher(voucherIdToDelete)) {
                        request.setAttribute("successMessage", "Voucher deleted successfully.");
                        success = true;
                    } else {
                        request.setAttribute("errorMessage", "Failed to delete voucher.");
                        response.sendRedirect("VoucherController?action=list");
                        return;
                    }
                    break;

                default:
                    request.setAttribute("errorMessage", "Invalid action.");
                    response.sendRedirect("VoucherController?action=list");
                    return;
            }

            if (success) {
                response.sendRedirect("VoucherController?action=list");
            }

        } catch (NumberFormatException | DateTimeParseException | ServletException | IOException ex) {
            Logger.getLogger(VoucherController.class.getName()).log(Level.SEVERE, "Error in doPost", ex);
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            response.sendRedirect("VoucherController?action=list");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private boolean validateVoucherAdd(HttpServletRequest request, HttpServletResponse response, VoucherDAO voucherDAO) throws ServletException, IOException {
        String voucherCode = request.getParameter("voucherCode");
        String voucherName = request.getParameter("voucherName");
        String valueStr = request.getParameter("discountValue");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        if (voucherCode == null || voucherCode.trim().isEmpty() || voucherName == null || voucherName.trim().isEmpty() || valueStr == null || valueStr.trim().isEmpty() || startDateStr == null || startDateStr.trim().isEmpty() || endDateStr == null || endDateStr.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "All fields are required.");
            return false;
        }

        if (voucherDAO.isVoucherCodeExists(voucherCode)) {
            request.getSession().setAttribute("errorMessage", "Voucher code already exists.");
            return false;
        }

        if (voucherDAO.isVoucherNameExists(voucherName)) {
            request.getSession().setAttribute("errorMessage", "Voucher name already exists.");
            return false;
        }

        try {
            float value = Float.parseFloat(valueStr);
            boolean discountType = Boolean.parseBoolean(request.getParameter("discountType"));

            if (discountType && value > 1) {
                request.getSession().setAttribute("errorMessage", "Percentage value cannot exceed 1 (100%).");
                return false;
            }

            if (value <= 0) {
                request.getSession().setAttribute("errorMessage", "Discount value must be positive.");
                return false;
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid discount value format.");
            return false;
        }

        try {
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            if (startDate.isAfter(endDate)) {
                request.getSession().setAttribute("errorMessage", "Start date cannot be after end date.");
                return false;
            }
        } catch (DateTimeParseException e) {
            request.getSession().setAttribute("errorMessage", "Invalid date format (YYYY-MM-DDTHH:mm).");
            return false;
        }

        return true;
    }

    private boolean validateVoucherEdit(HttpServletRequest request, HttpServletResponse response, VoucherDAO voucherDAO) throws ServletException, IOException {
        String voucherCode = request.getParameter("voucherCode");
        String voucherName = request.getParameter("voucherName");
        String valueStr = request.getParameter("discountValue");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        if (voucherCode == null || voucherCode.trim().isEmpty() || voucherName == null || voucherName.trim().isEmpty() || valueStr == null || valueStr.trim().isEmpty() || startDateStr == null || startDateStr.trim().isEmpty() || endDateStr == null || endDateStr.trim().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "All fields are required.");
            return false;
        }

        try {
            float value = Float.parseFloat(valueStr);
            boolean discountType = Boolean.parseBoolean(request.getParameter("discountType"));

            if (discountType && value > 1) {
                request.getSession().setAttribute("errorMessage", "Percentage value cannot exceed 1 (100%).");
                return false;
            }

            if (value <= 0) {
                request.getSession().setAttribute("errorMessage", "Discount value must be positive.");
                return false;
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid discount value format.");
            return false;
        }

        try {
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            if (startDate.isAfter(endDate)) {
                request.getSession().setAttribute("errorMessage", "Start date cannot be after end date.");
                return false;
            }
        } catch (DateTimeParseException e) {
            request.getSession().setAttribute("errorMessage", "Invalid date format (YYYY-MM-DDTHH:mm).");
            return false;
        }

        return true;
    }
}
