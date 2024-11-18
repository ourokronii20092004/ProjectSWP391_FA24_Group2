/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class Product {
    private int productID;
    private String productName;
    private String description;
    private float price;
    private String imageURL;
    private int categoryID;
    private int stockQuantity;
    private Date createdAt;
    private Date updatedAt;
    private ArrayList<Rating> ratingList;

    public Product(int productID, String productName, String description, float price, String imageURL, int categoryID, int stockQuantity, Date createdAt, Date updatedAt) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.categoryID = categoryID;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product(int productID, String productName, String description, float price, String imageURL, int categoryID, int stockQuantity, Date createdAt, Date updatedAt, ArrayList<Rating> ratingList) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.categoryID = categoryID;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ratingList = ratingList;
    }

    public Product(int productID, String productName, String description, float price, String imageURL, int categoryID) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.categoryID = categoryID;
    }

    public Product(int productID, String productName, String description, float price, String imageURL, int categoryID, int stockQuantity) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.categoryID = categoryID;
        this.stockQuantity = stockQuantity;
    }

    public ArrayList<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(ArrayList<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
