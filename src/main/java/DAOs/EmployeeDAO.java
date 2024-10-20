/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author phanp
 */
public class EmployeeDAO {

    private int upCount;

    public ArrayList<User> viewEmployeeList() throws SQLException {
        ArrayList<User> empList = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * from [dbo].[User] WHERE RoleID = 3");
            while (rs.next()) {
                empList.add(new User(rs.getInt("UserID"),
                        rs.getString("Salt"),
                        rs.getString("PasswordHash"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ImageURL"),
                        rs.getString("ShippingAddress"),
                        rs.getInt("RoleID"),
                        rs.getByte("IsActive") == 1,
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")));
            }
            DBConnection.Disconnect();
        }
        return empList;
    }

    public User readEmployee(int id) throws SQLException {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                User emp;
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * from [dbo].[User] WHERE UserID = " + id);
                rs.next();
                emp = new User(rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ImageURL"),
                        rs.getString("ShippingAddress"),
                        rs.getInt("RoleID"),
                        rs.getByte("IsActive") == 1,
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"));
                DBConnection.Disconnect();
                return emp;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public void addEmployee(User emp) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("INSERT INTO [dbo].[User] VALUES(?,?,?,?,?,?,?,?,?)");
            pre.setString(1, emp.getUserName());
            pre.setString(2, emp.getSalt());
            pre.setString(3, emp.getPassword());
            pre.setString(4, emp.getEmail());
            pre.setString(5, emp.getFirstName());
            pre.setString(6, emp.getLastName());
            pre.setString(7, emp.getPhoneNumber());
            pre.setString(7, emp.getImgURL());
            pre.setString(7, emp.getAddress());
            pre.setInt(8, emp.getRoleID());
            pre.setInt(9, emp.isIsActive() ? 1 : 0);
            pre.close();
            DBConnection.Disconnect();
        }
    }

    public void updateEmployee(User emp) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE [dbo].[User] SET "
                    + "[FirstName] = ?"
                    + ",[LastName] = ?"
                    + ",[PhoneNumber] = ?"
                    + ", [ImageURL] = ?"
                    + ",[ShippingAddress] = ?"
                    + ",[IsActive] = ?"
                    + " WHERE UserID = ?");
            pre.setString(1, emp.getFirstName());
            pre.setString(2, emp.getLastName());
            pre.setString(3, emp.getPhoneNumber());
            pre.setString(4, emp.getImgURL());
            pre.setString(5, emp.getAddress());
            pre.setInt(6, (emp.isIsActive() ? 1 : 0));
            pre.setInt(7, emp.getId());
            pre.close();
            DBConnection.Disconnect();
        }
    }

    public boolean removeEmployee(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            upCount = DBConnection.ExecuteUpdate("UPDATE [dbo].[User] "
                    + "SET isActive = 0"
                    + " WHERE UserID = " + id
            );
            DBConnection.Disconnect();
            if (upCount > 0) {
                upCount = 0;
                return true;
            }

        }
        return false;
    }
}
