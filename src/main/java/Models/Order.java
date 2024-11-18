/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class Order {

    private final int orderID;
    private final User user;
    private final ArrayList<OrderItem> orderItemList;
    private Voucher voucher;
    private final Date orderDate;
    private float totalAmount;
    private String orderStatus;

    public Order(int orderId, User user, ArrayList<OrderItem> orderItemList, Voucher voucher, Date orderDate, float totalAmount, String orderStatus) {
        this.orderID = orderId;
        this.user = user;
        this.orderItemList = orderItemList;
        this.voucher = voucher;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    public int getOrderID() {
        return orderID;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

}
