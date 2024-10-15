/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author phanp
 */
public class DBConnection {
    public static Connection getConnection() {
        Connection conn;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             String url ="jdbc:sqlserver://PHUC_BINH:1433;databaseName=FlowerShop;user=binh;password=123;encrypt=true;trustServerCertificate=true;";

            conn = DriverManager.getConnection(url);
        } catch (Exception ex) {
            conn = null;
        }
        return conn;
    }
}
