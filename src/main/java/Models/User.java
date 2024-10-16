/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author phanp
 */
public class User {
    private final int id;
    private String userName;
    private String salt;
    private String password;
    private String Email;
    private String firstName;
    private String lastName;
    private String address;
    private int roleID;
    private boolean isActive;
    private final Date createdAt;
    private final Date updatedAt;

    public User() {
        this.id = 0;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public User(int id, String userName, String salt, String password, String Email, String firstName, String lastName, String address, int roleID, boolean isActive, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userName = userName;
        this.salt = salt;
        this.password = password;
        this.Email = Email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.roleID = roleID;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String Salt) {
        this.salt = Salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
