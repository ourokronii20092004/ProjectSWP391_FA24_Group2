/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Models.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {

    ArrayList<Category> categoryList = new ArrayList<>();
    private int upCount;

    public ArrayList<Category> viewCategory() {
        categoryList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Category where IsActive = 1");
            try {
                while (rs.next()) {
                    categoryList.add(new Category(rs.getInt("CategoryID"),
                            rs.getString("CategoryName"),
                            rs.getInt("ParentCategoryID")));
                }

                DBConnection.Disconnect();
            } catch (SQLException ex) {
                return new ArrayList<>(categoryList);
            }
        }
        return categoryList;
    }

    public Category readCategory(int id) {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                Category emp;
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * from [dbo].[Category] WHERE CategoryID = " + id);
                rs.next();
                emp = new Category(rs.getInt("CategoryID"),
                        rs.getNString("CategoryName"),
                        rs.getInt("ParentCategoryID"));
                DBConnection.Disconnect();
                return emp;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public boolean addCategory(Category cate) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre;
            try {
                pre = DBConnection.getPreparedStatement("INSERT INTO Category (CategoryName, ParentCategoryID) VALUES (?, ?)");
                pre.setString(1, cate.getCategoryName());
                if (cate.getParentCategoryID() == null) {
                    pre.setNull(2, java.sql.Types.INTEGER);
                } else if (cate.getParentCategoryID() == 0) {
                    cate.setParentCategoryID(null);
                    pre.setNull(2, java.sql.Types.INTEGER);
                } else {
                    pre.setInt(2, cate.getParentCategoryID());
                };
                pre.execute();
                pre.close();
                DBConnection.Disconnect();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    public boolean updateCategory(Category cate) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre;
            try {
                pre = DBConnection.getPreparedStatement("UPDATE Category SET CategoryName = ?, ParentCategoryID = ? WHERE CategoryID = ?");
                pre.setString(1, cate.getCategoryName());
                if (cate.getParentCategoryID() == null) {
                    pre.setNull(2, java.sql.Types.INTEGER);
                } else if (cate.getParentCategoryID() == 0) {
                    cate.setParentCategoryID(null);
                    pre.setNull(2, java.sql.Types.INTEGER);
                } else {
                    pre.setInt(2, cate.getParentCategoryID());
                };
                pre.setInt(3, cate.getCategoryId());
                pre.execute();
                pre.close();
                DBConnection.Disconnect();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    private boolean hasChildCategory(int id) throws SQLException {
        try ( PreparedStatement pre = DBConnection.getPreparedStatement("SELECT COUNT(*) FROM Category WHERE ParentCategoryID = ?")) {
            pre.setInt(1, id);
            try ( ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private void updateChildCategory(int parentId) throws SQLException {
        String sql = "UPDATE Category SET ParentCategoryID = 0 WHERE ParentCategoryID = ?";
        try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
            pre.setInt(1, parentId);
            pre.executeUpdate();
        }
    }

    public String removeCategory(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            if (hasChildCategory(id)) {
                return "Không thể xóa category này vì nó có các category con.";
            } else {
                // Tiến hành SOFT DELETE
                upCount = DBConnection.ExecuteUpdate("UPDATE Category SET IsActive = 0 WHERE CategoryID =" + id);
                if (upCount > 0) {
                    upCount = 0;
                    return "Xóa category thành công.";
                }
            }
            DBConnection.Disconnect();
        }
        return "Xóa category thất bại";
    }

    public boolean removeCategoryFinal(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            // HARD DELETE
            upCount = DBConnection.ExecuteUpdate("DELETE FROM Category WHERE CategoryID = " + id);
            DBConnection.Disconnect();
            if (upCount > 0) {
                upCount = 0;
                return true;
            }
        }
        return false;
    }

    public ArrayList<Category> getSubCategories(int categoryId) {
        ArrayList<Category> subCategories = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Category WHERE ParentCategoryID = " + categoryId + " and IsActive = 1"); // and IsActive = 1
                while (rs.next()) {
                    subCategories.add(new Category(rs.getInt("CategoryID"),
                            rs.getString("CategoryName"),
                            rs.getInt("ParentCategoryID")));
                }

                DBConnection.Disconnect();
            } catch (SQLException ex) {
                // Handle exceptions appropriately, e.g., log the error
                ex.printStackTrace(); // or use a logger
                return new ArrayList<>(); // Return empty list in case of error

            }
        }
        return subCategories;
    }
}