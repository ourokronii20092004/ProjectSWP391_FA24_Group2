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
                String stm = "INSERT INTO [dbo].[CartItem] (UserID,ProductID,Quantity)"
                        + "VALUES(?,?,?)";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setInt(1, userID);
                pstm.setInt(2, productID);
                pstm.setInt(3, quantity);
                pstm.executeUpdate();
                pstm.close();
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
