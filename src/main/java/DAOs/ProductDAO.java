/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class ProductDAO {

    ArrayList<Product> productList = new ArrayList<>();
    private int upCount;

    public ArrayList<Product> viewProductList() {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * from Product WHERE StockQuantity > -1");
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
                    //System.out.println(rs.getInt("ProductID"));
                }
                rs.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error fetching product: ", e);
            return new ArrayList<>();
        } finally {
            DBConnection.Disconnect();
        }
        return new ArrayList<>(productList);
    }

    public ArrayList<Product> viewProductListControl() throws SQLException {
        productList.clear();
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                ResultSet rs = DBConnection.ExecuteQuery("select top (5) * from Product where StockQuantity > -1 Order By UpdatedAt desc");
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
                rs.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error fetching product: ", e);
            return new ArrayList<>();
        } finally {
            DBConnection.Disconnect();
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

    public Product readProduct(int id) {
        String sql = "select * from Product where ProductID = ?";
        Product product = null;
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, id);

                    try ( ResultSet rs = pre.executeQuery()) {
                        if (rs.next()) {
                            product = new Product(
                                    rs.getInt("ProductID"),
                                    rs.getString("ProductName"),
                                    rs.getString("Description"),
                                    rs.getFloat("Price"),
                                    rs.getString("ImageURL"),
                                    rs.getInt("CategoryID"),
                                    rs.getInt("StockQuantity"),
                                    rs.getDate("CreatedAt"),
                                    rs.getDate("UpdatedAt")
                            );
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error fetching product: " + ex.getMessage(), ex);
            throw new RuntimeException("Error reading product", ex);
        } finally {
            DBConnection.Disconnect();
        }

        return product;
    }

    public void addProduct(Product product) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "insert into Product (ProductName, Description, Price, ImageURL, CategoryID, StockQuantity) values (?, ?, ?, ?, ?, ?)";
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setString(1, product.getProductName());
                    pre.setString(2, product.getDescription());
                    pre.setFloat(3, product.getPrice());
                    pre.setString(4, product.getImageURL());
                    pre.setInt(5, product.getCategoryID());
                    pre.setInt(6, product.getStockQuantity());
                    upCount = pre.executeUpdate();
                }
                if (upCount > 0) {
                    viewProductList();
                    upCount = 0;
                } else {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.WARNING, "Failed to add product: No rows affected.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error adding product: " + ex.getMessage(), ex);
        } finally {
            DBConnection.Disconnect();
        }
    }

    public void updateProduct(Product product) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "update Product set ProductName = ?, Description = ?, Price = ?, ImageURL = ?, CategoryID = ?, StockQuantity = ? where ProductID = ?";
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setString(1, product.getProductName());
                    pre.setString(2, product.getDescription());
                    pre.setFloat(3, product.getPrice());
                    pre.setString(4, product.getImageURL());
                    pre.setInt(5, product.getCategoryID());
                    pre.setInt(6, product.getStockQuantity());
                    pre.setInt(7, product.getProductID());
                    upCount = pre.executeUpdate();
                }

                if (upCount > 0) {
                    int index = productList.indexOf(readProduct(product.getProductID()));
                    if (index != -1) {
                        productList.set(index, product);
                    }
                    upCount = 0;
                } else {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.WARNING, "Failed to update product: No rows affected.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error updating product: " + ex.getMessage(), ex);
        } finally {
            DBConnection.Disconnect();
        }
    }

    public boolean removeProduct(int id) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "update Product set StockQuantity = -1 where ProductID = ?"; // soft delete
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, id);
                    upCount = pre.executeUpdate();
                }
                if (upCount > 0) {
                    Product product = readProduct(id);
                    if (product != null) {
                        product.setStockQuantity(-1);
                    }
                    upCount = 0;
                    return true; // ok
                } else {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.WARNING, "Failed to remove product: No rows affected.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error removing product: " + ex.getMessage(), ex);
            ex.printStackTrace(); //test
        } finally {
            DBConnection.Disconnect();
        }
        return false; // not ok
    }

    public boolean removeProductFinal(int id) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "update Product set StockQuantity = -2 where ProductID = ?"; // hard delete
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, id);
                    upCount = pre.executeUpdate();
                }
                if (upCount > 0) {
                    Product productToRemove = readProduct(id);
                    if (productToRemove != null) {
                        productList.remove(productToRemove);
                    }
                    upCount = 0;
                    return true; // ok
                } else {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.WARNING, "Failed to delete product: No rows affected.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error deleting product: " + ex.getMessage(), ex);
        } finally {
            DBConnection.Disconnect();
        }
        return false; // not ok
    }

    public boolean restoreProduct(int id) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "update Product set StockQuantity = 0 where ProductID = ? and StockQuantity = -1";
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, id);
                    upCount = pre.executeUpdate();
                }

                if (upCount > 0) {
                    Product productToRestore = readProduct(id);
                    if (productToRestore != null) {
                        productToRestore.setStockQuantity(0);
                    }

                    upCount = 0;
                    return true; // ok
                } else {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.WARNING, "Failed to restore product: No rows affected.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error restoring product: " + ex.getMessage(), ex);
        } finally {
            DBConnection.Disconnect();
        }
        return false; //not ok
    }

    public ArrayList<Product> searchProductsByName(String productName) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE ProductName LIKE ? and StockQuantity > -1");
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

    public ArrayList<Product> searchProductsByCategory(int categoryId) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE CategoryID = ? and StockQuantity > -1");
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
            String sql = "SELECT * FROM Product WHERE ProductName LIKE ? StockQuantity > -1";
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

    public ArrayList<Product> searchProductsByNameAndCategory(String productName, int categoryId) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try ( PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE ProductName LIKE ? AND CategoryID = ? AND StockQuantity > -1")) {
                pre.setString(1, "%" + productName + "%");
                pre.setInt(2, categoryId);
                try ( ResultSet rs = pre.executeQuery()) {
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
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error searching products", ex);
                throw ex;
            } finally {
                DBConnection.Disconnect();
            }
        }
        return results;
    }

    public boolean isValidCategoryId(int categoryId) {
        String sql = "select 1 from Category where CategoryID = ?";
        boolean isValid = false;
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, categoryId);
                    try ( ResultSet rs = pre.executeQuery()) {
                        isValid = rs.next();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error checking category ID: " + ex.getMessage(), ex);
        } finally {
            DBConnection.Disconnect();
        }
        return isValid;
    }

    public ArrayList<Product> searchDeletedProductsByName(String productName) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try ( PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE ProductName LIKE ? AND StockQuantity = -1")) {
                pre.setString(1, "%" + productName + "%");
                try ( ResultSet rs = pre.executeQuery()) {
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
                }
            }
        }
        return results;
    }

    public ArrayList<Product> searchDeletedProductsByCategory(int categoryId) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try ( PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE CategoryID = ? AND StockQuantity = -1")) {
                pre.setInt(1, categoryId);
                try ( ResultSet rs = pre.executeQuery()) {
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
                }
            }
        }
        return results;
    }

    public ArrayList<Product> searchDeletedProductsByNameAndCategory(String productName, int categoryId) throws SQLException {
        ArrayList<Product> results = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try ( PreparedStatement pre = DBConnection.getPreparedStatement("SELECT * FROM Product WHERE ProductName LIKE ? AND CategoryID = ? AND StockQuantity = -1")) {
                pre.setString(1, "%" + productName + "%");
                pre.setInt(2, categoryId);
                try ( ResultSet rs = pre.executeQuery()) {
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
                }
            }
        }
        return results;
    }

    ///////////////////////////////////////////////////
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
                while (rs.next()) {
                    System.out.println("phat");
                }
            } catch (Exception ex) {
                rs = null;
            }
        }
        return rs;
    }

    public boolean updateProductStock(int productID, int quantity) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "UPDATE Product SET StockQuantity = StockQuantity - ? WHERE ProductID = ? AND StockQuantity >= ?";
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, quantity);
                    pre.setInt(2, productID);
                    pre.setInt(3, quantity);

                    upCount = pre.executeUpdate();
                }

                if (upCount > 0) {
                    Product updatedProduct = readProduct(productID);
                    if (updatedProduct != null) {
                        int index = productList.indexOf(updatedProduct);
                        if (index != -1) {
                            productList.set(index, updatedProduct);
                        }
                    }

                    upCount = 0;
                    return true;
                } else {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.WARNING, "Failed to update product stock: No rows affected.");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error updating product stock: " + ex.getMessage(), ex);
            return false;
        } finally {
            DBConnection.Disconnect();
        }
        return true;
    }

    public boolean updateProductStockRefuse(int productID, int quantity) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "UPDATE Product SET StockQuantity = StockQuantity - ? WHERE ProductID = ?";
                try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                    pre.setInt(1, quantity);
                    pre.setInt(2, productID);
                    upCount = pre.executeUpdate();
                    return upCount > 0;
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error updating product stock: " + ex.getMessage(), ex);
            return false;
        } finally {
            DBConnection.Disconnect();
        }
        return false;
    }
}
