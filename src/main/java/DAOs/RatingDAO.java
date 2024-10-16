/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Rating;
import DB.DBConnection;
import Models.CartItem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                Rating rating = new Rating(rs.getInt("RatingID"), rs.getInt("UserID"), rs.getInt("ProductID"), rs.getInt("RatingValue"), rs.getString("Comment"), rs.getDate("CreatedAt"));
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
                pstm.setInt(1, rating.getRatingID());
                pstm.setInt(2, rating.getUserID());
                pstm.setInt(3, rating.getProductID());
                pstm.setInt(4, rating.getRatingValue());
                pstm.setString(5, rating.getComment());
                pstm.setDate(6, rating.getCreatedAt());
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
                pstm.setInt(1, rating.getRatingValue());
                pstm.setString(2, rating.getComment());
                pstm.setInt(3, rating.getUserID());
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void removeComment(int ratingID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                //Delete cartitem from cart
                String stm = "UPDATE Rating SET Comment = NULL"
                        + "WHERE RatingID LIKE ?";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setInt(1, ratingID);
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (Exception e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
            DBConnection.Disconnect();
        }
    }

    public void removeRatingValue(int ratingID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                //Delete cartitem from cart
                String stm = "UPDATE Rating SET RatingValue = NULL"
                        + "WHERE RatingID LIKE ?";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setInt(1, ratingID);
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (Exception e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
            DBConnection.Disconnect();
        }
    }
    
    public void DeleteAllRating(int ratingID){
                if (DBConnection.isConnected()) {
            try {
                //Delete cartitem from cart
                String stm = "DELETE FROM Rating "
                        + "WHERE RatingID LIKE ?";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setInt(1, ratingID);
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (Exception e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
            DBConnection.Disconnect();
        }       
    }
    public ArrayList<Rating> viewAllRating(int productID) throws SQLException {
        ArrayList<Rating> ratingList = new ArrayList<>();
        ratingList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Rating Where ProductID like '" + productID + "'");
            while (rs.next()) {
                ratingList.add(new Rating(rs.getInt("RatingID"), rs.getInt("UserID"), rs.getInt("ProductID"), rs.getInt("RatingValue"), rs.getString("Comment"), rs.getDate("CreatedAt")));
            }
        }
        return ratingList;
    }
}
