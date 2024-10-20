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
import java.sql.PreparedStatement;
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
                PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM [dbo].[User] where Username like ?");
                pre.setString(1, username);
                Account acc;
                try (ResultSet rs = pre.executeQuery()) {
                    rs.next();
                    acc = null;
                    if (HashFunction.comparePasswords(password, rs.getString("Salt"), rs.getString("PasswordHash"))) {
                        acc = new Account(rs.getString("Username"), rs.getString("PasswordHash"), rs.getString("Salt"), rs.getInt("RoleID"));
                    }
                }
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
