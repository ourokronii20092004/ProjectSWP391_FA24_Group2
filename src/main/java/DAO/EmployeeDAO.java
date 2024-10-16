/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.User;
import DB.DBConnection;
import java.sql.*;
import java.util.*;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class EmployeeDAO {

    ArrayList<User> empList = new ArrayList<>();
    private int upCount;

    public EmployeeDAO() throws SQLException {
        viewEmployeeList();
    }

    private void viewEmployeeList() throws SQLException {
        empList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * from [dbo].[User] WHERE RoleID = 3");
            while (rs.next()) {
                empList.add(new User(rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Salt"),
                        rs.getString("PasswordHash"),
                        rs.getString("Email"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("ShippingAddress"),
                        rs.getInt("RoleID"),
                        rs.getByte("IsActive") == 1,
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")));
            }
        }
    }

    public User readEmployee(int id) throws SQLException {
        if (empList.isEmpty()) {
            return null;
        }
        for (User u : empList) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public void addEmployee(User emp) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("INSERT INTO [dbo].[User] VALUES(?,?,?,?,?,?,?,?,?,NULL,NULL)");
            pre.setString(1, emp.getUserName());
            pre.setString(2, emp.getSalt());
            pre.setString(3, emp.getPassword());
            pre.setString(4, emp.getEmail());
            pre.setString(5, emp.getFirstName());
            pre.setString(6, emp.getLastName());
            pre.setString(7, emp.getAddress());
            pre.setInt(8, emp.getRoleID());
            pre.setInt(9, emp.isIsActive() ? 1 : 0);
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                viewEmployeeList();
                upCount = 0;
            }

        }
    }

    public void updateEmployee(User emp) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE [dbo].[User] SET "
                    + "[FirstName] = ?"
                    + ",[LastName] = ?"
                    + ",[ShippingAddress] = ?"
                    + ",[IsActive] = ?"
                    + " WHERE UserID = ?");
            pre.setString(1, emp.getFirstName());
            pre.setString(2, emp.getLastName());
            pre.setString(3, emp.getAddress());
            pre.setInt(4, (emp.isIsActive() ? 1 : 0));
            pre.setInt(5, emp.getId());
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                empList.set(empList.indexOf(readEmployee(emp.getId())), emp);
                upCount = 0;
            }
        }
    }

    public boolean removeEmployee(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            upCount = DBConnection.ExecuteUpdate("UPDATE [dbo].[User] "
                    + "SET isActive = 0"
                    + "WHERE UserID = " + id
            );
            DBConnection.Disconnect();
            readEmployee(id).setIsActive(false);
            if (upCount > 0) {
                upCount = 0;
                return true;
            }

        }
        return false;
    }
}
