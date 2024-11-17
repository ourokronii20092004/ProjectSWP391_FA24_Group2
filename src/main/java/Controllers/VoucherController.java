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
import java.awt.BorderLayout;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jasper.tagplugins.jstl.core.Out;

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
            action = "activated";
        }
        System.out.println("--- Voucher Controller doGet --- ");
        try {
            ArrayList<Voucher> voucherList = null;
            System.out.println("doGet: " + action);
            switch (action) {
                case "list":
                    String isActiveParam = request.getParameter("isActive");

                    if (isActiveParam == null) {
                        System.out.println("isActive is null");
                        voucherList = voucherDAO.getAllVouchers();
                        break;
                    } else {
                        System.out.println("isActive is not null");
                        try {
                            int isActiveValue = Integer.parseInt(isActiveParam);
                            System.out.println("isActive is: " + isActiveValue);
                            System.out.println("isActive param is: " + isActiveParam);
                            switch (isActiveValue) {
                                case 1:
                                    voucherList = voucherDAO.getVoucherList(true);
                                    System.out.println("isActive is 1");
                                    break;
                                case 0:
                                    voucherList = voucherDAO.getVoucherList(false);
                                    System.out.println("isActive is 0");
                                    break;
                                case -1:
                                    voucherList = voucherDAO.getAllVouchers();
                                    System.out.println("isActive is -1");
                                    break;
                                default:
                                    voucherList = voucherDAO.getAllVouchers();
                                    System.out.println("isActive is something else");
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            voucherList = voucherDAO.getAllVouchers();
                            System.out.println("isActive is not number");
                        }
                    }
                    break;
                case "expired":
                    voucherList = voucherDAO.getExpiredVouchers();
                    break;
                case "activate":
                    int voucherIdToActivate = Integer.parseInt(request.getParameter("voucherId"));
                    voucherDAO.activateVoucher(voucherIdToActivate);
                    response.sendRedirect("VoucherController?action=expired");
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
                    System.out.println("DEFAULT");
                    voucherList = voucherDAO.getAllVouchers();
                    break;
            }

            request.setAttribute("voucherList", voucherList);

            String destination = "voucherManagement.jsp";
            if ("expired".equals(action) || "searchExpired".equals(action)) {
                destination = "restoreVoucher.jsp";
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
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
            action = "activated";
        }
        System.out.println("--- Voucher Controller doPost --- ");
        System.out.println("doPost: " + action);
        try {
            switch (action) {
                case "add":
                    String voucherCode = request.getParameter("voucherCode");

                    if (voucherDAO.isVoucherCodeExists(voucherCode)) {
                        request.setAttribute("errorMessage", "Voucher code already exists.");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("voucherManagement.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }

                    boolean discountType = Boolean.parseBoolean(request.getParameter("discountType"));
                    float discountValue = Float.parseFloat(request.getParameter("discountValue"));
                    Date startDate = Date.valueOf(request.getParameter("startDate"));
                    Date endDate = Date.valueOf(request.getParameter("endDate"));
                    String voucherName = request.getParameter("voucherName");
                    boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
                    Date createdAt = new Date(System.currentTimeMillis());
                    Date updatedAt = createdAt;

                    Voucher newVoucher = new Voucher(0, voucherCode, discountType, discountValue, startDate, endDate, voucherName, isActive, createdAt, updatedAt);
                    voucherDAO.addVoucher(newVoucher);
                    break;
                case "edit":
                    break;
                case "bulkAction": {
                    String[] selectedVouchers = request.getParameterValues("selectedVouchers");
                    String bulkAction = request.getParameter("bulkActivate") != null ? "activate"
                            : (request.getParameter("bulkDeactivate") != null ? "deactivate"
                            : (request.getParameter("bulkDelete") != null ? "delete" : null));

                    if (selectedVouchers != null && bulkAction != null) {
                        for (String voucherIdStr : selectedVouchers) {
                            try {
                                int voucherId = Integer.parseInt(voucherIdStr);
                                switch (bulkAction) {
                                    case "activate":
                                        voucherDAO.activateVoucher(voucherId);
                                        break;
                                    case "deactivate":
                                        voucherDAO.deactivateVoucher(voucherId);
                                        break;
                                    case "delete":
                                        voucherDAO.deleteVoucher(voucherId);
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                Logger.getLogger(VoucherController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        }
                    }
                    response.sendRedirect("VoucherController?action=expired");
                    return;
                }
            }
            response.sendRedirect("VoucherController");

        } catch (NumberFormatException ex) {
            Logger.getLogger(VoucherController.class.getName()).log(Level.SEVERE, null, ex);
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

}
