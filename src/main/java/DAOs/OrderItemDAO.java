/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.OrderItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class OrderItemDAO {

    public ArrayList<OrderItem> getOrderItemByOrderID(int orderID) {
        ArrayList list = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM OrderItem WHERE OrderID = " + orderID);
            try {
                while (rs.next()) {
                    list.add(new OrderItem(rs.getInt("OrderItemID"),
                            orderID,
                            new ProductDAO().getProductByID(rs.getInt("ProductID")),
                            rs.getInt("Quantity"),
                            rs.getFloat("PriceAtPurchase")));
                }
                rs.close();
                DBConnection.Disconnect();
                return list;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return list;
    }

    // Add a new OrderItem
    public boolean addOrderItem(OrderItem orderItem) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            String query = "INSERT INTO OrderItem (OrderID, ProductID, Quantity, PriceAtPurchase) VALUES (" 
                    + orderItem.getOrderID() + ", " 
                    + orderItem.getProduct().getProductID() + ", "
                    + orderItem.getQuantity() + ", " 
                    + orderItem.getPriceAtPurchase() + ")";
            int result = DBConnection.ExecuteUpdate(query);
            DBConnection.Disconnect();
            return result > 0;
        }
        return false;
    }

    // View all OrderItems (Optional method for viewing purposes)
    public ArrayList<OrderItem> viewOrderItem() {
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM OrderItem");
            try {
                while (rs.next()) {
                    orderItemList.add(new OrderItem(rs.getInt("OrderItemID"), 
                                                     rs.getInt("OrderID"), 
                                                     new ProductDAO().getProductByID(rs.getInt("ProductID")), 
                                                     rs.getInt("Quantity"), 
                                                     rs.getFloat("PriceAtPurchase")));
                }
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBConnection.Disconnect();
            }
        }
        return orderItemList;
    }
}
