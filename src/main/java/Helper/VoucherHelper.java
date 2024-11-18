/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import DAOs.VoucherDAO;
import Models.Voucher;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class VoucherHelper {

    private final VoucherDAO voucherDAO = new VoucherDAO();
    private final Random random = new SecureRandom();

    public String generateUniqueVoucherCode() {
        String voucherCode;
        do {
            voucherCode = generateVoucherCode(10);
        } while (voucherDAO.isVoucherCodeExists(voucherCode));
        return voucherCode;
    }

    private String generateVoucherCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public boolean isVoucherNameUnique(String voucherName) throws SQLException {

        return !voucherDAO.isVoucherNameExists(voucherName);
    }

    public void updateVoucherStatusBasedOnDate(int voucherId) {

        try {

            Voucher voucher = voucherDAO.getVoucherById(voucherId);

            if (voucher != null) {

                long currentTime = System.currentTimeMillis();

                if (voucher.getEndDate().getTime() < currentTime && voucher.isIsActive()) {

                    voucherDAO.deactivateVoucher(voucherId);
                } else if (voucher.getStartDate().getTime() < currentTime && !voucher.isIsActive()) {
                    voucherDAO.activateVoucher(voucherId);
                }
            }

        } catch (Exception e) {

            Logger.getLogger(VoucherHelper.class.getName()).log(Level.SEVERE, "General error updating voucher based on date for voucherId:" + voucherId, e);

        }
    }

    public void updateVoucherDates(int voucherId, Date startDate, Date endDate) {
        try {
            Voucher voucher = voucherDAO.getVoucherById(voucherId);

            if (voucher != null) {

                voucher.setStartDate(startDate);
                voucher.setEndDate(endDate);
                voucherDAO.updateVoucher(voucher);
            }

        } catch (Exception e) {

            Logger.getLogger(VoucherHelper.class.getName()).log(Level.SEVERE, "Error updateing voucher date", e);
        }
    }
}
