/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.CartItem;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
public class CartDAO {

    public CartItem getCartItem(int userID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT "
                        + "  CartItem.CartItemID, "
                        + "  CartItem.UserID, "
                        + "  CartItem.Quantity, "
                        + "  CartItem.ProductID, "
                        + "  Product.ProductName, "
                        + "  Product.Description, "
                        + "  Product.Price, "
                        + "  Product.ImageURL, "
                        + "  Product.StockQuantity "
                        + "FROM "
                        + "  CartItem "
                        + "  INNER JOIN Product ON CartItem.ProductID = Product.ProductID \n"
                        + "WHERE "
                        + "  UserID like '" + userID + "'");
                rs.next();
                CartItem cartItem = new CartItem(rs.getInt("CartItemID"), rs.getInt("UserID"), rs.getInt("Quantity"), rs.getInt("ProductID"), rs.getNString("ProductName"), rs.getNString("Description"), rs.getFloat("Price"), rs.getString("ImageURL"), rs.getInt("StockQuantity"));
                DBConnection.Disconnect();
                return cartItem;
            } catch (SQLException e) {
                Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public void addCartItem(int userID, int productID, int quantity) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                // First, check if the cart item already exists
                String checkQuery = "SELECT Quantity FROM [dbo].[CartItem] WHERE UserID = ? AND ProductID = ?";
                PreparedStatement checkStmt = DBConnection.getPreparedStatement(checkQuery);
                checkStmt.setInt(1, userID);
                checkStmt.setInt(2, productID);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // If the item exists, update its quantity
                    int currentQuantity = rs.getInt("Quantity");
                    String updateQuery = "UPDATE [dbo].[CartItem] SET Quantity = ? WHERE UserID = ? AND ProductID = ?";
                    PreparedStatement updateStmt = DBConnection.getPreparedStatement(updateQuery);
                    updateStmt.setInt(1, currentQuantity + quantity);
                    updateStmt.setInt(2, userID);
                    updateStmt.setInt(3, productID);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                } else {
                    // If the item does not exist, insert a new row
                    String insertQuery = "INSERT INTO [dbo].[CartItem] (UserID, ProductID, Quantity) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = DBConnection.getPreparedStatement(insertQuery);
                    insertStmt.setInt(1, userID);
                    insertStmt.setInt(2, productID);
                    insertStmt.setInt(3, quantity);
                    insertStmt.executeUpdate();
                    insertStmt.close();
                }

                rs.close();
                checkStmt.close();
                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void removeCartItem(int cartItemID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                //Delete cartitem from cart
                String stm = "DELETE FROM [dbo].[CartItem] "
                        + "WHERE CartItemID LIKE ?";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setInt(1, cartItemID);
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (Exception e) {
                Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, e);
            }
            DBConnection.Disconnect();
        }
    }

    public ArrayList<CartItem> viewCartItemList(int userID) throws SQLException {
        ArrayList<CartItem> cartList = new ArrayList<>();
        cartList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT "
                    + "  CartItem.CartItemID, "
                    + "  CartItem.UserID, "
                    + "  CartItem.Quantity, "
                    + "  CartItem.ProductID, "
                    + "  Product.ProductName, "
                    + "  Product.Description, "
                    + "  Product.Price, "
                    + "  Product.ImageURL, "
                    + "  Product.StockQuantity "
                    + "FROM "
                    + "  CartItem "
                    + "  INNER JOIN Product ON CartItem.ProductID = Product.ProductID \n"
                    + "WHERE "
                    + "  UserID like '" + userID + "'");
            while (rs.next()) {
                cartList.add(new CartItem(rs.getInt("CartItemID"), rs.getInt("UserID"), rs.getInt("Quantity"), rs.getInt("ProductID"), rs.getNString("ProductName"), rs.getNString("Description"), rs.getFloat("Price"), rs.getString("ImageURL"), rs.getInt("StockQuantity")));
            }
        }
        return cartList;
    }

    public void updateCartItemQuantity(int cartItemID, int quantity) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            String stm = "UPDATE [dbo].[CartItem] SET Quantity = ? WHERE CartItemID = ?";
            PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
            pstm.setInt(1, quantity);
            pstm.setInt(2, cartItemID);
            pstm.executeUpdate();
            pstm.close();
            DBConnection.Disconnect();
        }
    }
}
