/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class OrderItem {

    private final int orderItemID;
    private final int orderID;
    private Product product;
    private int quantity;
    private float priceAtPurchase;

    public OrderItem(int orderItemID, int orderID, Product product, int quantity, float priceAtPurchase) {
        this.orderItemID = orderItemID;
        this.orderID = orderID;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(float priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

}
