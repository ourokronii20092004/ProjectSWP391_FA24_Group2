/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Rating;
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
public class RatingDAO {

    public Rating getRating(String userID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Rating Where UserID like '" + userID + "'");
                rs.next();
                Rating rating = new Rating(rs.getString("RatingID"), rs.getString("UserID"), rs.getString("ProductID"), rs.getString("RatingValue"), rs.getString("Comment"), rs.getString("CreatedAt"));
                return rating;
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public void addRating(Rating rating) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {

            try {
                String stm = "INSERT INTO Rating (RatingID,UserID,ProductID,RatingValue,Comment,CreatedAt)"
                        + "VALUES(?,?,?,?,?,?)";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setString(1, rating.getRatingID());
                pstm.setString(2, rating.getUserID());
                pstm.setString(3, rating.getProductID());
                pstm.setString(4, rating.getRatingValue());
                pstm.setString(5, rating.getComment());
                pstm.setString(6, rating.getCreatedAt());
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void updateRating(Rating rating) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                String query = "UPDATE Rating SET RatingValue=?, Comment=? WHERE UserID=?";
                PreparedStatement pstm = DBConnection.getPreparedStatement(query);
                pstm.setString(1, rating.getRatingValue());
                pstm.setString(2, rating.getComment());
                pstm.setString(3, rating.getUserID());
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
   
}
