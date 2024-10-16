/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phanp
 */
public class ProductDAO {

    public List<Product> getAllProduct() {
        List<Product> listproduct = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("Select * from Product");
                while (rs.next()) {
                    listproduct.add(new Product(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getFloat(4),
                            rs.getString(5),
                            rs.getInt(6)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listproduct;
    }
    
    public 
}
