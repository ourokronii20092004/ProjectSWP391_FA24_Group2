/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DAOs.LoginDAO;
import DB.DBConnection;
import Models.Account;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
public class AccountDAO {

    public int findUserID(String username) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[User] where Username like '" + username + "'");
                rs.next();
                int id = rs.getInt("UserID");
                DBConnection.Disconnect();
                return id;
            } catch (SQLException e) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                return -1;
            }
        }
        return -1;
    }

    //Phat
    public int findUserIDByEmail(String email) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[User] where Email like '" + email + "'");
                rs.next();
                int id = rs.getInt("UserID");
                DBConnection.Disconnect();
                return id;
            } catch (SQLException e) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                return -1;
            }
        }
        return -1;
    }

    //Phat
    public Models.Account findUserbyUsername(String username) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[User] where Username like '" + username + "'");
                rs.next();
                Models.Account account = new Account(rs.getString("Username"),
                        rs.getString("PasswordHash"),
                        rs.getString("Salt"),
                        rs.getInt("RoleID"),
                        rs.getInt("IsActive") == 1);
                DBConnection.Disconnect();
                return account;
            } catch (SQLException e) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
        }
        return null;
    }

    //Binh
    public void updateProfile(int id, User newinfo) {
        DBConnection.Connect();

        if (DBConnection.isConnected()) {

            try ( PreparedStatement pre = DBConnection.getPreparedStatement(
                    "UPDATE [dbo].[User] "
                    + "SET Email = ?, "
                    + "    firstName = ?, "
                    + "    lastName = ?, "
                    + "    address = ? "
                    + "WHERE id = ?")) {

                pre.setString(1, newinfo.getEmail());
                pre.setString(2, newinfo.getFirstName());
                pre.setString(3, newinfo.getLastName());
                pre.setString(4, newinfo.getAddress());

                pre.executeUpdate();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    public void viewProfile(int id, User user) {
        DBConnection.Connect();

        // Check if the connection is established
        if (DBConnection.isConnected()) {
            // Use try-with-resources to automatically close the PreparedStatement and ResultSet
            try ( PreparedStatement pre = DBConnection.getPreparedStatement(
                    "SELECT Email, firstName, lastName, address FROM User WHERE id = ?")) {

                // Set the ID parameter for the query
                pre.setInt(1, id);

                // Execute the query and store the result in a ResultSet
                try ( ResultSet rs = pre.executeQuery()) {
                    // If the user with the given ID exists
                    if (rs.next()) {
                        // Populate the User object with data from the ResultSet
                        user.setEmail(rs.getString("Email"));
                        user.setFirstName(rs.getString("firstName"));
                        user.setLastName(rs.getString("lastName"));
                        user.setAddress(rs.getString("address"));
                    }
                }
            } catch (SQLException e) {
                // Handle any SQL exceptions (log or print)
                e.printStackTrace();
            }
        }
    }

    public boolean recoverAcconut(int id) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            int upCount = DBConnection.ExecuteUpdate("UPDATE [dbo].[User] "
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

    public boolean updatePassword(int userID, String password, String salt) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {

            try (
                     PreparedStatement pre = DBConnection.getPreparedStatement(
                            "UPDATE [User] SET PasswordHash = ?, Salt=? WHERE userID = ?")) {
                pre.setString(1, password);
                pre.setString(2, salt);
                pre.setInt(3, userID);
                pre.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;   
    }
}
