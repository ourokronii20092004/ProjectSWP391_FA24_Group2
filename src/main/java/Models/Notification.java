/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class Notification {
    private int NotificationID;
    private int UserID;
    private String Message;
    private Byte IsRead;
    private Date CreatedAt;

    public Notification() {
    }

    public Notification(int NotificationID, int UserID, String Message, Byte IsRead, Date CreatedAt) {
        this.NotificationID = NotificationID;
        this.UserID = UserID;
        this.Message = Message;
        this.IsRead = IsRead;
        this.CreatedAt = CreatedAt;
    }

    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int NotificationID) {
        this.NotificationID = NotificationID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Byte getIsRead() {
        return IsRead;
    }

    public void setIsRead(Byte IsRead) {
        this.IsRead = IsRead;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }
    
    
    
    
}
