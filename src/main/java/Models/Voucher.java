/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class Voucher {

    private final int voucherID;
    private int voucherCode;
    private boolean type;
    private float value;
    private Date startDate;
    private Date endDate;
    private String voucherName;
    private boolean isActive;
    private Date createAt;
    private Date updateAt;

    public Voucher(int voucherID, int voucherCode, boolean type, float value, Date startDate, Date endDate, String voucherName, boolean isActive, Date createAt, Date updateAt) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
        this.type = type;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
        this.voucherName = voucherName;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(int voucherCode) {
        this.voucherCode = voucherCode;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getVoucherID() {
        return voucherID;
    }

}
