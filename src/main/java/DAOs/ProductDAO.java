/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class ProductDAO {

    ArrayList<Product> productList = new ArrayList<>();
    private int upCount;

    public ArrayList<Product> viewProductList() throws SQLException {
        productList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            // SOFT DELETE == STOCK -1
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * from Product where StockQuantity > -1");
            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getFloat("Price"),
                        rs.getString("ImageURL"),
                        rs.getInt("CategoryID"),
                        rs.getInt("StockQuantity"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")
                ));
            }
        }
        return new ArrayList<>(productList);
    }

    /*
    Nay la lay may cai "da xoa" ra, neu can thi dung
     */
    public ArrayList<Product> viewDeletedProductList() throws SQLException {
        productList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * from Product where StockQuantity = -1");
            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getFloat("Price"),
                        rs.getString("ImageURL"),
                        rs.getInt("CategoryID"),
                        rs.getInt("StockQuantity"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")
                ));
            }
        }
        return new ArrayList<>(productList);
    }

    public Product readProduct(int id) throws SQLException {
        if (productList.isEmpty()) {
            return null;
        }
        for (Product p : productList) {
            if (p.getProductID() == id) {
                return p;
            }
        }
        return null;
    }

    public void addProduct(Product product) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("INSERT INTO Product (ProductName, Description, Price, ImageURL, CategoryID, StockQuantity) VALUES (?, ?, ?, ?, ?, ?)");
            pre.setString(1, product.getProductName());
            pre.setString(2, product.getDescription());
            pre.setFloat(3, product.getPrice());
            pre.setString(4, product.getImageURL());
            pre.setInt(5, product.getCategoryID());
            pre.setInt(6, product.getStockQuantity());
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                viewProductList();
                upCount = 0;
            }
        }
    }

    public void updateProduct(Product product) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE Product SET ProductName = ?, Description = ?, Price = ?, ImageURL = ?, CategoryID = ?, StockQuantity = ? WHERE ProductID = ?");
            pre.setString(1, product.getProductName());
            pre.setString(2, product.getDescription());
            pre.setFloat(3, product.getPrice());
            pre.setString(4, product.getImageURL());
            pre.setInt(5, product.getCategoryID());
            pre.setInt(6, product.getStockQuantity());
            pre.setInt(7, product.getProductID());
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                for (Product p : productList) {
                    if (p.getProductID() == product.getProductID()) {
                        p.setProductName(product.getProductName());
                        p.setDescription(product.getDescription());
                        p.setPrice(product.getPrice());
                        p.setImageURL(product.getImageURL());
                        p.setCategoryID(product.getCategoryID());
                        p.setStockQuantity(product.getStockQuantity());
                        break;
                    }
                }
                upCount = 0;
            }
        }
    }

    public boolean removeProduct(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            // SOFT DELETE == STOCK -1
            upCount = DBConnection.ExecuteUpdate("UPDATE Product SET StockQuantity = -1 WHERE ProductID = " + id);
            DBConnection.Disconnect();
            if (upCount > 0) {
                upCount = 0;
                return true;
            }
        }
        return false;
    }

    public boolean removeProductFinal(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            // HARD DELETE
            upCount = DBConnection.ExecuteUpdate("DELETE FROM Product WHERE ProductID = " + id);
            DBConnection.Disconnect();
            if (upCount > 0) {
                upCount = 0;
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> searchProductsByName(String productName) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE ProductName LIKE ?");
            pre.setString(1, "%" + productName + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                results.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getFloat("Price"),
                        rs.getString("ImageURL"),
                        rs.getInt("CategoryID"),
                        rs.getInt("StockQuantity"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")
                ));
            }
            pre.close();
            DBConnection.Disconnect();
        }
        return results;
    }

    /*
    NAY SEARCH = ID, KHONG PHAI SEARCH TEN CATEGORY=))
    LAM BEN CAI JSP ROI NAP ID QUA CONTROL
     */
    public ArrayList<Product> searchProductsByCategory(int categoryId) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE CategoryID = ?");
            pre.setInt(1, categoryId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                results.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getFloat("Price"),
                        rs.getString("ImageURL"),
                        rs.getInt("CategoryID"),
                        rs.getInt("StockQuantity"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")
                ));
            }
            pre.close();
            DBConnection.Disconnect();
        }
        return results;
    }

    public ArrayList<Product> searchProduct(String keyword) throws SQLException {
        ArrayList<Product> searchResult = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            String sql = "SELECT * FROM Product WHERE ProductName LIKE ?";
            try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                pre.setString(1, "%" + keyword + "%");
                try ( ResultSet rs = pre.executeQuery()) {
                    while (rs.next()) {
                        searchResult.add(new Product(rs.getInt("ProductID"),
                                rs.getNString("ProductName"),
                                rs.getString("Description"),
                                rs.getFloat("Price"),
                                rs.getString("ImageURL"),
                                rs.getInt("CategoryID"),
                                rs.getInt("StockQuantity")));
                    }
                }
            }
            DBConnection.Disconnect();
        }
        return searchResult;
    }

    public Product getProductByID(int productID) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[Product] Where ProductID like '" + productID + "'");
                rs.next();
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getFloat("Price"),
                        rs.getString("ImageURL"),
                        rs.getInt("CategoryID"),
                        rs.getInt("StockQuantity"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"));
                DBConnection.Disconnect();
                return product;
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public ResultSet getAll() {
        DBConnection.Connect();
        ResultSet rs = null;

        if (DBConnection.isConnected()) {
            try {
               
                rs = DBConnection.ExecuteQuery("SELECT * FROM [dbo].[Product]");
                while(rs.next()){
                    System.out.println("phat");
                }
            } catch (Exception ex) {
                rs = null;
            }
        }
        return rs;
    }
}
