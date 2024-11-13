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

    public void updateRating(Rating rating) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                // First, check if the rating belongs to the user
                String queryCheck = "SELECT * FROM Rating WHERE RatingID = ? AND UserID = ?";
                PreparedStatement pstmCheck = DBConnection.getPreparedStatement(queryCheck);
                pstmCheck.setInt(1, rating.getRatingID());
                pstmCheck.setInt(2, rating.getUserID()); // Only the owner can update
                ResultSet rs = pstmCheck.executeQuery();

                if (rs.next()) { // If rating found and belongs to the user
                    String queryUpdate = "UPDATE Rating SET RatingValue=?, Comment=? WHERE RatingID=?";
                    PreparedStatement pstmUpdate = DBConnection.getPreparedStatement(queryUpdate);
                    pstmUpdate.setInt(1, rating.getRatingValue());
                    pstmUpdate.setString(2, rating.getComment());
                    pstmUpdate.setInt(3, rating.getRatingID());
                    pstmUpdate.executeUpdate();
                    pstmUpdate.close();
                } else {
                    System.out.println("You are not authorized to update this rating.");
                }

                pstmCheck.close();
                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
                throw e; // Re-throw the exception for servlet-level handling
            }
        }
    }

    public void deleteRating(int ratingID, int userID) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                // Check if the rating belongs to the user
                String queryCheck = "SELECT * FROM Rating WHERE RatingID = ? AND UserID = ?";
                PreparedStatement pstmCheck = DBConnection.getPreparedStatement(queryCheck);
                pstmCheck.setInt(1, ratingID);
                pstmCheck.setInt(2, userID); // Only the owner can delete
                ResultSet rs = pstmCheck.executeQuery();

                if (rs.next()) { // If rating found and belongs to the user
                    String queryDelete = "DELETE FROM Rating WHERE RatingID = ?";
                    PreparedStatement pstmDelete = DBConnection.getPreparedStatement(queryDelete);
                    pstmDelete.setInt(1, ratingID);
                    pstmDelete.executeUpdate();
                    pstmDelete.close();
                } else {
                    System.out.println("You are not authorized to delete this rating.");
                }

                pstmCheck.close();
                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
                throw e; // Re-throw the exception for servlet-level handling
            }
        }
    }

    public void deleteAllRating(int ratingID) {
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
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            String query = "SELECT * FROM Rating WHERE ProductID = ?";
            try (PreparedStatement pstm = DBConnection.getPreparedStatement(query)) {
                pstm.setInt(1, productID);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    ratingList.add(new Rating(
                        rs.getInt("RatingID"),
                        rs.getInt("User ID"),
                        rs.getInt("ProductID"),
                        rs.getInt("RatingValue"),
                        rs.getString("Comment"),
                        rs.getDate("CreatedAt")
                    ));
                }
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                DBConnection.Disconnect();
            }
        }
        return ratingList;
    }
    
}
