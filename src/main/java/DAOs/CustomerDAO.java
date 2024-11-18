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

    private int upCount;

    public ArrayList<User> viewCustomerList() {
        ArrayList<User> cusList = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * from [dbo].[User] WHERE RoleID = 2");
            try {
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

                DBConnection.Disconnect();
            } catch (SQLException ex) {
                return cusList;
            }

        }
        return cusList;
    }

    public User readCustomer(int id) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                User cus;
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * from [dbo].[User] WHERE UserID = " + id);
                rs.next();
                cus = new User(rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ImageURL"),
                        rs.getString("ShippingAddress"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"));
                DBConnection.Disconnect();
                return cus;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public boolean addCustomer(User cus) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre;
            try {
                pre = DBConnection.getPreparedStatement("INSERT INTO [dbo].[User] "
                        + "(Username, Salt, PasswordHash, Email, FirstName, LastName, PhoneNumber, ImageURL, ShippingAddress, RoleID, IsActive) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
                pre.setString(1, cus.getUserName());
                pre.setString(2, cus.getSalt());
                pre.setString(3, cus.getPassword());
                pre.setString(4, cus.getEmail());
                pre.setString(5, cus.getFirstName());
                pre.setString(6, cus.getLastName());
                pre.setString(7, cus.getPhoneNumber());
                pre.setString(8, cus.getImgURL());
                pre.setString(9, cus.getAddress());
                pre.setInt(10, 2);
                pre.setInt(11, cus.isIsActive() ? 1 : 0);
                pre.execute();
                pre.close();
                DBConnection.Disconnect();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    public boolean updateCustomer(User cus) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre;
            try {
                pre = DBConnection.getPreparedStatement("UPDATE [dbo].[User] SET "
                        + " [FirstName] = ?"
                        + ",[LastName] = ?"
                        + ",[PhoneNumber] = ?"
                        + ",[ShippingAddress] = ?"
                        + " WHERE UserID = ?");
                pre.setString(1, cus.getFirstName());
                pre.setString(2, cus.getLastName());
                pre.setString(3, cus.getPhoneNumber());
                pre.setString(4, cus.getAddress());
                pre.setInt(5, cus.getId());
                pre.execute();
                pre.close();
                DBConnection.Disconnect();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    public boolean updateIMG(User cus) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre;
            try {
                pre = DBConnection.getPreparedStatement("UPDATE [dbo].[User] SET "
                        + "[ImageURL] = ?"
                        + " WHERE UserID = ?");
                pre.setString(1, cus.getImgURL());
                pre.setInt(2, cus.getId());
                pre.execute();
                pre.close();
                DBConnection.Disconnect();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    public boolean removeCustomer(int id) {
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

    public boolean unbanCustomer(int id) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            upCount = DBConnection.ExecuteUpdate("UPDATE [dbo].[User] "
                    + "SET isActive = 1"
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
