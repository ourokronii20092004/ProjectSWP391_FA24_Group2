/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import Hash.HashFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
public class LoginDAO {

    public static Account Validate(String username, String password) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM User where Username like'" + username + "'");
                rs.next();
                Account acc = null;
                if (HashFunction.comparePasswords(password, rs.getString("Salt"), rs.getString("PasswordHash"))) {
                    acc = new Account(rs.getString("Username"), rs.getString("PasswordHash"), rs.getString("Salt"), rs.getInt("RoleID"));
                }
                rs.close();
                DBConnection.Disconnect();
                return acc;
            } catch (SQLException e) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
        }
        return null;
    }
}
