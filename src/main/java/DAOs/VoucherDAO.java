/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.User;
import Models.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class VoucherDAO {

    //Phat
    public Voucher getVoucherByOrderID(int voucherID) {
        System.out.println("VoucherID = " + voucherID);
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [Vouchers] WHERE VoucherID = " + voucherID);
                System.out.println("Voucher found!");
                rs.next();
                return new Voucher(
                        rs.getInt("VoucherID"),
                        rs.getString("VoucherCode"),
                        (rs.getByte("DiscountType") == 1),
                        rs.getFloat("DiscountValue"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("VoucherName"),
                        rs.getByte("isActive") == 1,
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"));
            } catch (SQLException ex) {
                System.out.println("No voucher found!");
                System.out.println(ex);
                return null;
            }
        }
        return null;
    }
}
