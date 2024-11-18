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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class OrderDAO {

    public ArrayList<Order> viewAllOrders() {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ArrayList<Order> orderList = new ArrayList<>();
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[Orders] ORDER BY OrderDate DESC");
            try {
                while (rs.next()) {
                    System.out.println(rs.getString("VoucherID"));
                    orderList.add(new Order(rs.getInt("OrderID"),
                            new UserDAO().getUserData(rs.getInt("UserID")),
                            new OrderItemDAO().getOrderItemByOrderID(rs.getInt("OrderID")),
                            rs.getString("VoucherID") == null ? null : new VoucherDAO().getVoucherByOrderID(rs.getInt("VoucherID")), //Voucher
                            rs.getDate("OrderDate"),
                            rs.getFloat("TotalAmount"),
                            rs.getString("OrderStatus")));
                    System.out.println(orderList.get(orderList.size() - 1).getOrderItemList().size());
                }

                rs.close();
                DBConnection.Disconnect();
                return orderList;
            } catch (SQLException ex) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ArrayList<Order> viewOrderListByUserID(int userID) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ArrayList<Order> orderList = new ArrayList<>();
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[Orders] WHERE UserID = " + userID + " ORDER BY OrderDate DESC");
            while (rs.next()) {
                orderList.add(new Order(rs.getInt("OrderID"),
                        new CustomerDAO().readCustomer(userID),
                        new OrderItemDAO().getOrderItemByOrderID(rs.getInt("OrderID")),
                        rs.getString("VoucherID") == null ? null : new VoucherDAO().getVoucherByOrderID(rs.getInt("VoucherID")), //Voucher
                        rs.getDate("OrderDate"),
                        rs.getFloat("TotalAmount"),
                        rs.getString("OrderStatus")));

            }
            rs.close();
            DBConnection.Disconnect();
            return orderList;
        }
        return null;
    }

    public Order readOrder(int orderID) {
        DBConnection.Connect();
      
        if (DBConnection.isConnected()) {
            Order or;
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[Orders] WHERE OrderID = " + orderID);
            try {
                rs.next();
                or = new Order(rs.getInt("OrderID"),
                        new CustomerDAO().readCustomer(rs.getInt("UserID")),
                        new OrderItemDAO().getOrderItemByOrderID(rs.getInt("OrderID")),
                        rs.getString("VoucherID") == null ? null : new VoucherDAO().getVoucherByOrderID(rs.getInt("VoucherID")), //Voucher
                        rs.getDate("OrderDate"),
                        rs.getFloat("TotalAmount"),
                        rs.getString("OrderStatus"));
                rs.close();
                DBConnection.Disconnect();
                return or;
            } catch (SQLException ex) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            pre.setInt(2, order.getOrderID()); // use `getOrderId` for OrderID
            int upCount = pre.executeUpdate();
            DBConnection.Disconnect();
            return upCount > 0;
        }
        return false;
    }

}
