/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class OrdersDAO {

    public ArrayList<Order> orderList = new ArrayList<>();

    public OrdersDAO(int userID) throws SQLException {
        viewOrderListByUserID(userID);
    }

    private void viewOrderListByUserID(int userID) throws SQLException {
        orderList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[Orders] WHERE UserID = " + userID);
            while (rs.next()) {
                orderList.add(new Order(rs.getInt("OrderID"),
                        userID, 
                        rs.getDate("OrderDate"),
                        rs.getFloat("TotalAmount"),
                        rs.getString("OrderStatus")));
            }
            rs.close();
            DBConnection.Disconnect();
        }
    }

    public Order readOrder(int orderID) {
        if (orderList.isEmpty()) {
            return null;
        }
        for (Order or : orderList) {
            if (or.getOrderId() == orderID) {
                return or;
            }
        }
        return null;
    }
    
        public boolean updateOrder(Order order) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            String sql = "UPDATE [dbo].[Orders] SET [OrderStatus] = ? WHERE OrderID = ?";
            PreparedStatement pre = DBConnection.getPreparedStatement(sql);
            pre.setString(1, order.getOrderStatus()); // assuming `getStatus` method exists in the `Order` class
            pre.setInt(2, order.getOrderId()); // use `getOrderId` for OrderID
            int upCount = pre.executeUpdate();
            DBConnection.Disconnect();
            return upCount > 0;
        }
        return false;
    }
    
    
}
