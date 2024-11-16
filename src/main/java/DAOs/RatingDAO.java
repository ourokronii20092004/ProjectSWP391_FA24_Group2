/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Rating;
import DB.DBConnection;
import Models.CartItem;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
                String stm = "INSERT INTO Rating (UserID,ProductID,RatingValue,Comment,CreatedAt)"
                        + "VALUES(?,?,?,?,?)";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setInt(1, rating.getUserID());
                pstm.setInt(2, rating.getProductID());
                pstm.setInt(3, rating.getRatingValue());
                pstm.setString(4, rating.getComment());
                pstm.setDate(5, new Date(System.currentTimeMillis()));
                System.out.println(rating.getCreatedAt());
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
                // No user check, just update the rating
                String queryUpdate = "UPDATE Rating SET RatingValue=?, Comment=?, CreatedAt=? WHERE UserID=?";
                PreparedStatement pstmUpdate = DBConnection.getPreparedStatement(queryUpdate);
                pstmUpdate.setInt(1, rating.getRatingValue());
                pstmUpdate.setString(2, rating.getComment());
                pstmUpdate.setDate(3, new Date(System.currentTimeMillis()));  // Current timestamp for 'CreatedAt'
                pstmUpdate.setInt(4, rating.getUserID());
                System.out.println(rating.getRatingValue());
                System.out.println(rating.getComment());
                System.out.println(rating.getCreatedAt());
                System.out.println(rating.getUserID());
                pstmUpdate.executeUpdate();
                pstmUpdate.close();

                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
                throw e;  // Re-throw the exception for servlet-level handling
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

public void deleteRatingAsEmp(int ratingID) {
    DBConnection.Connect();
    if (DBConnection.isConnected()) {
        try {
            String stm = "DELETE FROM Rating WHERE RatingID = ?";
            PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
            pstm.setInt(1, ratingID);
            pstm.executeUpdate();
            pstm.close();
            System.out.println("Rating with ID " + ratingID + " has been deleted by an employee.");
        } catch (Exception e) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBConnection.Disconnect();
        }
    }
}

    public ArrayList<Rating> viewAllRating(int productID) throws SQLException {
        ArrayList<Rating> ratingList = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            String query = "SELECT Rating.RatingID, Rating.UserID, Rating.ProductID, Rating.RatingValue, Rating.Comment, Rating.CreatedAt, [User].Username\n"
                    + "FROM  Rating INNER JOIN\n"
                    + "[User] ON Rating.UserID = [User].UserID WHERE ProductID = ?";
            try ( PreparedStatement pstm = DBConnection.getPreparedStatement(query)) {
                pstm.setInt(1, productID);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    ratingList.add(new Rating(
                            rs.getInt("RatingID"),
                            rs.getInt("UserID"),
                            rs.getInt("ProductID"),
                            rs.getInt("RatingValue"),
                            rs.getString("Comment"),
                            rs.getDate("CreatedAt"),
                            rs.getString("Username")
                    ));
                }
                System.out.println(ratingList.size());
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                DBConnection.Disconnect();
            }
        }
        return ratingList;
    }

    public Rating getRatingForUserAndProduct(int userID, int productID) throws SQLException {
        DBConnection.Connect();
        Rating rating = null;
        if (DBConnection.isConnected()) {
            String query = "SELECT * FROM Rating WHERE UserID = ? AND ProductID = ?";
            try ( PreparedStatement pstm = DBConnection.getPreparedStatement(query)) {
                pstm.setInt(1, userID);
                pstm.setInt(2, productID);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                    rating = new Rating(
                            rs.getInt("RatingID"),
                            rs.getInt("UserID"),
                            rs.getInt("ProductID"),
                            rs.getInt("RatingValue"),
                            rs.getString("Comment"),
                            rs.getDate("CreatedAt")
                    );
                }
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
                throw e;
            } finally {
                DBConnection.Disconnect();
            }
        }
        return rating;
    }

    public int getProductIDByRatingID(int ratingID) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            String query = "SELECT ProductID FROM Rating WHERE RatingID = ?";
            try ( PreparedStatement pstm = DBConnection.getPreparedStatement(query)) {
                pstm.setInt(1, ratingID);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("ProductID");
                }
            } catch (SQLException e) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                DBConnection.Disconnect();
            }
        }
        return -1;  // Return -1 if no productID is found (invalid ratingID)
    }
}
