/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;


import Models.Voucher;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    private int upCount;

    public ArrayList<Voucher> getAllVouchers() {
        ArrayList<Voucher> voucherList = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers where isActive is not null";

        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql);  ResultSet rs = pre.executeQuery()) {
                    while (rs.next()) {
                        voucherList.add(createVoucherFromResultSet(rs));
                    }
                }
            } else {
                Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Not connected to database");
                return new ArrayList<>();
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "SQL Error in getAllVouchers", e);
            return new ArrayList<>();
        } finally {
            DBConnection.Disconnect();
        }
        return voucherList;

    }

    public Voucher getVoucherById(int voucherId) {
        String sql = "SELECT * FROM Vouchers WHERE VoucherID = ? and isActive is not null";
        Voucher voucher = null;
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, voucherId);
                    try ( ResultSet rs = pre.executeQuery()) {
                        if (rs.next()) {
                            voucher = createVoucherFromResultSet(rs);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error fetching voucher:", ex);
        } finally {
            DBConnection.Disconnect();
        }
        return voucher;
    }

    public void addVoucher(Voucher voucher) {
        String sql = "INSERT INTO Vouchers (VoucherCode, DiscountType, DiscountValue, StartDate, EndDate, VoucherName, IsActive, CreatedAt, UpdatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setString(1, voucher.getVoucherCode());
                    pre.setBoolean(2, voucher.isType());
                    pre.setFloat(3, voucher.getValue());
                    pre.setTimestamp(4, Timestamp.valueOf(voucher.getStartDate()));
                    pre.setTimestamp(5, Timestamp.valueOf(voucher.getEndDate()));
                    pre.setString(6, voucher.getVoucherName());
                    pre.setBoolean(7, voucher.isIsActive());
                    pre.setTimestamp(8, Timestamp.valueOf(voucher.getCreateAt()));
                    pre.setTimestamp(9, Timestamp.valueOf(voucher.getUpdateAt()));
                    upCount = pre.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error adding voucher:", ex);
        } finally {
            DBConnection.Disconnect();
        }
    }

    public void updateVoucher(Voucher voucher) {
        String sql = "UPDATE Vouchers SET VoucherCode=?, DiscountType=?, DiscountValue=?, StartDate=?, EndDate=?, VoucherName=?, IsActive=?, UpdatedAt=? WHERE VoucherID=?";
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setString(1, voucher.getVoucherCode());
                    pre.setBoolean(2, voucher.isType());
                    pre.setFloat(3, voucher.getValue());
                    pre.setTimestamp(4, Timestamp.valueOf(voucher.getStartDate()));
                    pre.setTimestamp(5, Timestamp.valueOf(voucher.getEndDate()));
                    pre.setString(6, voucher.getVoucherName());
                    pre.setBoolean(7, voucher.isIsActive());
                    pre.setTimestamp(8, Timestamp.valueOf(voucher.getUpdateAt()));
                    pre.setInt(9, voucher.getVoucherID());

                    upCount = pre.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error updating voucher:", ex);

        } finally {
            DBConnection.Disconnect();
        }
    }

    public boolean deleteVoucher(int voucherId) {
        System.out.println("Deleting Voucher: " + voucherId);
        String sql = "UPDATE Vouchers SET IsActive = NULL WHERE VoucherID = ?";
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, voucherId);
                    upCount = pre.executeUpdate();
                    return upCount > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error deleting voucher:", ex);
        } finally {
            DBConnection.Disconnect();
        }
        return false;
    }

    public boolean isVoucherCodeExists(String voucherCode) {
        String sql = "SELECT 1 FROM Vouchers WHERE VoucherCode = ? and isActive is not null";

        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setString(1, voucherCode);
                    try ( ResultSet rs = pre.executeQuery()) {
                        return rs.next();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error checking voucher code:", ex);
        } finally {
            DBConnection.Disconnect();
        }
        return false;
    }

    public ArrayList<Voucher> getVoucherList(boolean isActive) {
        ArrayList<Voucher> voucherList = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers WHERE IsActive = ?";

        try {
            DBConnection.Connect();

            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {

                    pre.setBoolean(1, isActive);

                    try ( ResultSet rs = pre.executeQuery()) {

                        while (rs.next()) {
                            Voucher voucher = createVoucherFromResultSet(rs);
                            voucherList.add(voucher);
                        }
                    }
                }
            } else {
                Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Not connected to database");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error getting vouchers:", ex);
        } finally {
            DBConnection.Disconnect();
        }

        return voucherList;
    }

    public ArrayList<Voucher> getExpiredVouchers() {
        ArrayList<Voucher> expiredVouchers = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers WHERE EndDate < ? and isActive is not null";
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setDate(1, new Date(System.currentTimeMillis()));
                    try ( ResultSet rs = pre.executeQuery()) {
                        while (rs.next()) {
                            expiredVouchers.add(createVoucherFromResultSet(rs));
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error getting expired vouchers:", ex);
        } finally {
            DBConnection.Disconnect();
        }
        return expiredVouchers;
    }

    public void activateVoucher(int voucherId) {
        updateVoucherStatus(voucherId, true);
    }

    public void deactivateVoucher(int voucherId) {
        updateVoucherStatus(voucherId, false);
    }

    private void updateVoucherStatus(int voucherId, boolean isActive) {
        String sql = "UPDATE Vouchers SET IsActive = ? WHERE VoucherID = ? and isActive is not null";
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setBoolean(1, isActive);
                    pre.setInt(2, voucherId);
                    pre.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error updating voucher status:", ex);
        } finally {
            DBConnection.Disconnect();
        }
    }

    private Voucher createVoucherFromResultSet(ResultSet rs) throws SQLException {
        return new Voucher(
                rs.getInt("VoucherID"),
                rs.getString("VoucherCode"),
                rs.getBoolean("DiscountType"),
                rs.getFloat("DiscountValue"),
                rs.getTimestamp("StartDate").toLocalDateTime(),
                rs.getTimestamp("EndDate").toLocalDateTime(),
                rs.getString("VoucherName"),
                rs.getBoolean("IsActive"),
                rs.getTimestamp("CreatedAt").toLocalDateTime(),
                rs.getTimestamp("UpdatedAt").toLocalDateTime()
        );
    }

    public ArrayList<Voucher> searchVouchers(String keyword, int discountType, boolean isActive) {
        ArrayList<Voucher> searchResult = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers WHERE VoucherName LIKE ? AND DiscountType = ? AND IsActive = ?";

        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setString(1, "%" + keyword + "%");
                    pre.setInt(2, discountType);
                    pre.setBoolean(3, isActive);

                    try ( ResultSet rs = pre.executeQuery()) {
                        while (rs.next()) {
                            searchResult.add(createVoucherFromResultSet(rs));
                        }
                    }
                }
            } else {
                Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Not connected to database");
                return new ArrayList<>(); // Return empty list if not connected
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error searching vouchers", e);
            return new ArrayList<>(); // Return empty list in case of error
        } finally {
            DBConnection.Disconnect();
        }
        return searchResult;
    }

    public ArrayList<Voucher> searchVouchersAllDiscount(String keyword, boolean isActive) {
        ArrayList<Voucher> searchResult = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers WHERE VoucherName LIKE ? AND IsActive = ?";

        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setString(1, "%" + keyword + "%");
                    pre.setBoolean(2, isActive);

                    try ( ResultSet rs = pre.executeQuery()) {
                        while (rs.next()) {
                            searchResult.add(createVoucherFromResultSet(rs));
                        }
                    }
                }
            } else {
                Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Not connected to database");
                return new ArrayList<>(); // Return empty list if not connected
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error searching vouchers", e);
            return new ArrayList<>(); // Return empty list in case of error
        } finally {
            DBConnection.Disconnect();
        }
        return searchResult;
    }

    public boolean isVoucherNameExists(String voucherName) {
        String sql = "SELECT 1 FROM Vouchers WHERE VoucherName = ? and isActive is not null";
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement stmt = DBConnection.getPreparedStatement(sql)) {
                    stmt.setString(1, voucherName);
                    try ( ResultSet rs = stmt.executeQuery()) {
                        return rs.next();
                    }
                }
            } else {
                Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Not connected to database");
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error checking voucher name", e);
            return false;
        } finally {
            DBConnection.Disconnect();
        }
    }

    public ArrayList<Voucher> getVouchersNearExpirationOrActivation() {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers WHERE (EndDate BETWEEN ? AND ?) OR (StartDate BETWEEN ? AND ?) and isActive is not null ";

        LocalDateTime now = LocalDateTime.now();
        Timestamp oneMinuteFromNow = Timestamp.valueOf(now.plusMinutes(1));
        Timestamp oneMinuteAgo = Timestamp.valueOf(now.minusMinutes(1));

        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement stmt = DBConnection.getPreparedStatement(sql)) {
                    stmt.setTimestamp(1, oneMinuteAgo);        // EndDate check
                    stmt.setTimestamp(2, oneMinuteFromNow);

                    stmt.setTimestamp(3, oneMinuteAgo);        // StartDate check
                    stmt.setTimestamp(4, oneMinuteFromNow);

                    try ( ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            vouchers.add(createVoucherFromResultSet(rs));
                        }
                    }
                }
            } else {
                Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Not connected to database");
                return new ArrayList<>();
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, "Error getting vouchers near expiration or activation", e);
            return new ArrayList<>();
        } finally {
            DBConnection.Disconnect();
        }
        return vouchers;
    }



}
