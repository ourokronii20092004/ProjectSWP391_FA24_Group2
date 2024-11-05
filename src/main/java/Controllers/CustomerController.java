/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAOs.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author phanp
 */
@WebServlet(name="CustomerController", urlPatterns={"/CustomerController"})
public class CustomerController extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
//        id = Integer.parseInt(session.getAttribute("user_id") + "");
        //file upload
        if (request.getParameter("uploadImg") != null) {

            String fileName = "";

            String uploadPath = "";
            String s = getServletContext().getRealPath("") + File.separator;
            uploadPath = getServletContext().getRealPath("") + File.separator;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            for (Part part : request.getParts()) {
                if (part.getName().equals("txtPic")) {
//                    fileName = (String) getFileName(part);
                    if (fileName != null && !fileName.isEmpty()) {
                        part.write(uploadPath + File.separator +"img\\"+fileName);
                    }
                }
            }
            UserDAO dao = new UserDAO();
//            User user = dao.getUserByID(id); //Fix file DAO
//            dao.updateUserImg(id fileName);
            response.sendRedirect("/UserController");

        } else if (request.getParameter("btnSave") != null) {

            String firstName = request.getParameter("txtCallName");
            String lastName = request.getParameter("txtUserSurname");
            String Email = request.getParameter("txtPhoneNumber");
            String Address = request.getParameter("txtAddress");


//            User obj = new User(id, firstName, lastName, Email, Address);
            UserDAO dao = new UserDAO();
//            int updated = dao.updateUserInfo(id, obj);
//            if (updated == 0) {
//                response.sendRedirect("/err" + id);
//            } else {
//                response.sendRedirect("/UserController");
//            }

        }
    }
}
