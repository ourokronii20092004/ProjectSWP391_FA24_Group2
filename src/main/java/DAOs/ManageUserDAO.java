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
import java.util.List;

/**
 *
 * @author phanp
 */
public class ManageUserDAO {

    public List<User> getAllUser() {
        List<User> listUser = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("Select * from User");
                while (rs.next()) {
                    listUser.add(new User(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getInt(8),
                            rs.getBoolean(9),
                            rs.getDate(10),
                            rs.getDate(11)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listUser;
    }

        public int deleteUser(int id) {
            DBConnection.Connect();
            int count =0;
            if (DBConnection.isConnected()) {
                PreparedStatement pst;
                try {
                    String sql = "Delete from User where id=?";
                    pst = DBConnection.getPreparedStatement(sql);
                    pst.setInt(1, id);
                    count = pst.executeUpdate();
                } catch (SQLException e) {
                   count = 0 ;
                }
            }
            return count;
        }
}
