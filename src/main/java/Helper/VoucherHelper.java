/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import DAOs.VoucherDAO;
import Models.Voucher;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoucherHelper {

    private final VoucherDAO voucherDAO;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random = new SecureRandom();

    public VoucherHelper(VoucherDAO voucherDAO) {
        this.voucherDAO = voucherDAO;
    }

    public String generateUniqueVoucherCode() {
        String voucherCode;
        do {
            voucherCode = generateVoucherCode(10);
        } while (voucherDAO.isVoucherCodeExists(voucherCode));
        return voucherCode;
    }

    private String generateVoucherCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
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
                LocalDateTime now = LocalDateTime.now();
                if (voucher.getEndDate().isBefore(now) && voucher.isIsActive()) {
                    voucherDAO.deactivateVoucher(voucherId);
                } else if (voucher.getStartDate().isBefore(now) && !voucher.isIsActive()) {
                    voucherDAO.activateVoucher(voucherId);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(VoucherHelper.class.getName()).log(Level.SEVERE, "Error updating voucher status for ID: " + voucherId, e);
        }
    }
}
