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
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM CartItem Where CartID like '" + userID + "'");
                rs.next();
                CartItem cartItem = new CartItem(rs.getString("CartItemID"), rs.getString("UserID"), rs.getString("ProductID"), rs.getString("Quantity"));
                DBConnection.Disconnect();
                return cartItem;
            } catch (SQLException e) {
                Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public void addCartItem(CartItem cartItem) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                String stm = "INSERT INTO CartItem (CartItemID,UserID,ProductID,Quantity)"
                        + "VALUES(?,?,?,?)";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setString(1, cartItem.getCartItemID());
                pstm.setString(2, cartItem.getUserID());
                pstm.setString(3, cartItem.getProductID());
                pstm.setString(4, cartItem.getQuantity());
                pstm.executeUpdate();
                pstm.close();
                DBConnection.Disconnect();
            } catch (SQLException e) {
                Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void removeCartItem(String cartItemID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                //Delete cartitem from cart
                String stm = "DELETE FROM CartItem "
                        + "WHERE CartItemID LIKE ?";
                PreparedStatement pstm = DBConnection.getPreparedStatement(stm);
                pstm.setString(1, cartItemID);
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
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM CartItem Where CartID like '" + userID + "'");
            while (rs.next()) {
                cartList.add(new CartItem(rs.getString("CartItemID"), 
                                          rs.getString("UserID"), 
                                          rs.getString("ProductID"), 
                                          rs.getString("Quantity")));
            }
        }
        return cartList;
    }
      
}
