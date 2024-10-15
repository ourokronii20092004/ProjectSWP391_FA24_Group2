/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




package DB;
import java.sql.*;
/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class DBConnection {

    private static String cnnString = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=FlowerShop;"
            + "user=sa;"
            + "password=sa;"
            + "encrypt=true;trustServerCertificate=true;";
    private static Connection conn = null;

    public static boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }

    public static void Connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(cnnString);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());

            }
        } catch (SQLException | ClassNotFoundException ex) {
        }
    }

    public static void Disconnect() {
        try {
            if (isConnected()) {
                conn.close();
            }

        } catch (SQLException ex) {
        }
    }

    public static ResultSet ExecuteQuery(String query) {
        ResultSet result = null;
        try {
            if (isConnected()) {
                Statement st = conn.createStatement();
                result = st.executeQuery(query);

            }
        } catch (SQLException ex) {
            Disconnect();
        }

        return result;
    }

    public static int ExecuteUpdate(String query) {
        try {
            if (isConnected()) {
                Statement st = conn.createStatement();
                return st.executeUpdate(query);
            }
        } catch (SQLException ex) {
            Disconnect();
        }
        return 0;
    }
    
    public static PreparedStatement getPreparedStatement(String query) throws SQLException{
        return conn.prepareStatement(query);
    }
}
