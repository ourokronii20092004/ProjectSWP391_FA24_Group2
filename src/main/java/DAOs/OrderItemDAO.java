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
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ArrayList list = new ArrayList<>();
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
                return null;
            }

        }
        return null;
    }
}
