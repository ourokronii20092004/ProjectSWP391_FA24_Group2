/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author phanp
 */
public class CartItem {

    private int cartItemID, userID, productID, quantity;
    private String productName;
    private String description;
    private float price;
    private String imageURL;

    public CartItem() {
    }

    public CartItem(int cartItemID, int userID, int productID, int quantity) {
        this.cartItemID = cartItemID;
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public CartItem(int cartItemID, int userID, int productID, int quantity, String productName, String description, float price, String imageURL) {
        this.cartItemID = cartItemID;
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

}
