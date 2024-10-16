/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DAOs.LoginDAO;
import DB.DBConnection;
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

    public String findUserID(String username) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Users where Username like '" + username + "'");
                rs.next();
                String id = rs.getString("UserID");
                DBConnection.Disconnect();
                return id;
            } catch (SQLException e) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
        }
        return null;
    }
   
}
