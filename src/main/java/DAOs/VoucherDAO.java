/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Voucher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class VoucherDAO {
     public ArrayList<Voucher> getAllVouchers() throws SQLException {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try (ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Vouchers")) { // Thay đổi tên bảng nếu cần
                while (rs.next()) {
                    vouchers.add(new Voucher(
                        rs.getInt("VoucherID"),
                        rs.getInt("VoucherCode"),
                        rs.getBoolean("Type"), // Assuming "Type" column for boolean type
                        rs.getFloat("Value"),   // Assuming "Value" column for float value
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("VoucherName"),
                        rs.getBoolean("IsActive"),
                        rs.getDate("CreateAt"),
                        rs.getDate("UpdateAt")
                    ));
                }
            }
        }
        return vouchers;
    }

    public Voucher readVoucher(int voucherID) throws SQLException {
        Voucher voucher = null;
        DBConnection.Connect();

        if (DBConnection.isConnected()) {
            try (ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Vouchers WHERE VoucherID = " + voucherID)) {
                if (rs.next()) {
                   voucher = new Voucher(
                        rs.getInt("VoucherID"),
                        rs.getInt("VoucherCode"),
                        rs.getBoolean("Type"), // Assuming "Type" column for boolean type
                        rs.getFloat("Value"),   // Assuming "Value" column for float value
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("VoucherName"),
                        rs.getBoolean("IsActive"),
                        rs.getDate("CreateAt"),
                        rs.getDate("UpdateAt")
                    );
                }
            }
        }
        return voucher;

    }
    
}
