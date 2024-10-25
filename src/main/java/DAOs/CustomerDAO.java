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
public class CustomerDAO {
    ArrayList<User> cusList = new ArrayList<>();
    private int upCount;

    public CustomerDAO() throws SQLException {
        viewCustomerList();
    }

    private void viewCustomerList() throws SQLException {
        cusList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * from [dbo].[User] WHERE RoleID = 2");
            while (rs.next()) {
                cusList.add(new User(rs.getInt("UserID"),
                            rs.getString("Username"),
                            rs.getString("Salt"),
                            rs.getString("PasswordHash"),
                            rs.getString("Email"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("ShippingAddress"),
                            rs.getString("ImageURL"),
                            rs.getInt("RoleID"),
                            rs.getByte("IsActive") == 1,
                            rs.getDate("CreatedAt"),
                            rs.getDate("UpdatedAt")));                  
                }
        }
    }

    public User readCustomer(int id) throws SQLException {
        if (cusList.isEmpty()) {
            return null;
        }
        for (User u : cusList) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public void addCustomer(User cus) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("INSERT INTO [dbo].[User] VALUES(?,?,?,?,?,?,?,?,?,NULL,NULL)");
            pre.setString(1, cus.getUserName());
            pre.setString(2, cus.getSalt());
            pre.setString(3, cus.getPassword());
            pre.setString(4, cus.getEmail());
            pre.setString(5, cus.getFirstName());
            pre.setString(6, cus.getLastName());
            pre.setString(7, cus.getAddress());
            pre.setInt(8, cus.getRoleID());
            pre.setInt(9, cus.isIsActive() ? 1 : 0);
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                viewCustomerList();
                upCount = 0;
            }

        }
    }

    public void updateCustomer(User cus) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE [dbo].[User] SET "
                    + "[FirstName] = ?"
                    + ",[LastName] = ?"
                    + ",[ShippingAddress] = ?"
                    + ",[IsActive] = ?"
                    + " WHERE UserID = ?");
            pre.setString(1, cus.getFirstName());
            pre.setString(2, cus.getLastName());
            pre.setString(3, cus.getAddress());
            pre.setInt(4, (cus.isIsActive() ? 1 : 0));
            pre.setInt(5, cus.getId());
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                cusList.set(cusList.indexOf(readCustomer(cus.getId())), cus);
                upCount = 0;
            }
        }
    }

    public boolean removeCustomer(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            upCount = DBConnection.ExecuteUpdate("UPDATE [dbo].[User] "
                    + "SET isActive = 0"
                    + "WHERE UserID = " + id
            );
            DBConnection.Disconnect();
            readCustomer(id).setIsActive(false);
            if (upCount > 0) {
                upCount = 0;
                return true;
            }

        }
        return false;
    }
}
