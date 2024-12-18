/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Nguyen Nhat Dang - CE180010
 */
public class Account {

    private String userName, password, salt;
    private int roleID;
    //Phat
    private boolean isActive;

    public Account() {
    }

    public Account(String userName, String password, String salt, int roleID) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.roleID = roleID;
    }
    
    //Phat
    public Account(String userName, String password, String salt, int roleID, boolean isActive) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.roleID = roleID;
        this.isActive = isActive;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    
    //Phat
    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
