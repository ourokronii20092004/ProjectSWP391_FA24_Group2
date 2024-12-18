/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scheduler;

import DAOs.VoucherDAO;
import Helper.VoucherHelper;
import Models.Voucher;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Le Trung Hau - CE180481
 */
@WebListener
public class VoucherStatusUpdater implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Scheduler started.");
        scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            VoucherDAO voucherDAO = new VoucherDAO();
            VoucherHelper voucherHelper = new VoucherHelper(voucherDAO);
            try {
                ArrayList<Voucher> vouchers = voucherDAO.getVouchersNearExpirationOrActivation();
                for (Voucher voucher : vouchers) {
                    voucherHelper.updateVoucherStatusBasedOnDate(voucher.getVoucherID());
                    System.out.println("Updated status for: " + voucher.getVoucherID());
                }
            } catch (Exception ex) {
                Logger.getLogger(VoucherStatusUpdater.class.getName()).log(Level.SEVERE, "Error updating voucher status", ex);
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException ex) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}

