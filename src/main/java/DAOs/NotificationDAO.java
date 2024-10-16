/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Notification;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class NotificationDAO {
    private ArrayList<Notification> notificationList;

    public ArrayList<Notification> viewNotificationList() throws SQLException {
        notificationList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Notification");
            while (rs.next()) {
                notificationList.add(new Notification(
                        rs.getInt("NotificationID"),
                        rs.getInt("UserID"),
                        rs.getString("Message"),
                        rs.getByte("IsRead"),
                        rs.getDate("CreatedAt")
                ));
            }
        }
        return new ArrayList<>(notificationList);
    }

    public ArrayList<Notification> getNotificationsByUserId(int userId) throws SQLException {
        ArrayList<Notification> userNotifications = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Notification WHERE UserID = ?");
            pre.setInt(1, userId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                userNotifications.add(new Notification(
                        rs.getInt("NotificationID"),
                        rs.getInt("UserID"),
                        rs.getString("Message"),
                        rs.getByte("IsRead"),
                        rs.getDate("CreatedAt")
                ));
            }
            pre.close();
            DBConnection.Disconnect();
        }
        return userNotifications;
    }

    public Notification getNotificationById(int notificationId) throws SQLException {
        for (Notification notification : notificationList) {
            if (notification.getNotificationID() == notificationId) {
                return notification;
            }
        }
        return null;
    }

    public void addNotification(Notification notification) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("INSERT INTO Notification (UserID, Message, IsRead) VALUES (?, ?, ?)");
            pre.setInt(1, notification.getUserID());
            pre.setString(2, notification.getMessage());
            pre.setByte(3, notification.getIsRead());
            int rowsAffected = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();

            if (rowsAffected > 0) {
                viewNotificationList(); //REFRESH
            }
        }
    }

    public void updateNotification(Notification notification) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE Notification SET UserID = ?, Message = ?, IsRead = ? WHERE NotificationID = ?");
            pre.setInt(1, notification.getUserID());
            pre.setString(2, notification.getMessage());
            pre.setByte(3, notification.getIsRead());
            pre.setInt(4, notification.getNotificationID());
            int rowsAffected = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();

            if (rowsAffected > 0) {
                viewNotificationList();  //REFRESH
            }
        }
    }

    public void deleteNotification(int notificationId) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("DELETE FROM Notification WHERE NotificationID = ?");
            pre.setInt(1, notificationId);
            int rowsAffected = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();

            if (rowsAffected > 0) {
                viewNotificationList();  //REFRESH
            }
        }
    }

    public void markAsRead(int notificationId) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE Notification SET IsRead = 1 WHERE NotificationID = ?");
            pre.setInt(1, notificationId);
            int rowsAffected = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();

            if (rowsAffected > 0) {
                Notification notification = getNotificationById(notificationId);
                if (notification != null) {
                    notification.setIsRead((byte) 1);
                }
            }
        }
    }

    public void markAllAsRead(int userId) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE Notification SET IsRead = 1 WHERE UserID = ?");
            pre.setInt(1, userId);
            int rowsAffected = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (rowsAffected > 0) {
                for (Notification notification : notificationList) {
                    if (notification.getUserID() == userId) {
                        notification.setIsRead((byte) 1);
                    }
                }
            }
        }
        
        
        
        
    }
    
    
}
