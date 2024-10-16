/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
public class Rating {
     private int ratingID;
     private int userID;
     private int productID,ratingValue;
     String comment;
     private Date createdAt;

    public Rating() {
        this.userID = 0;
        this.createdAt = null;
    }

    public Rating(int ratingID, int userID, int productID, int ratingValue, String comment, Date createdAt) {
        this.ratingID = ratingID;
        this.userID = userID;
        this.productID = productID;
        this.ratingValue = ratingValue;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }    

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
}
