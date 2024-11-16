/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
public class UserDAO {
    
    public User getUserData(int userID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT *  FROM [dbo].[User] Where UserID like '" + userID + "'");
                rs.next();
                User user = new User(rs.getInt("UserID"),
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
                        rs.getDate("UpdatedAt"));
                DBConnection.Disconnect();
                return user;

            } catch (SQLException e) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }
}
