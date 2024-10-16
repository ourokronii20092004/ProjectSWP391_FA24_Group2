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

    public CategoryDAO() throws SQLException {
        viewCategory();
    }

    private void viewCategory() throws SQLException {
        categoryList.clear();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Category");
            while (rs.next()) {
                categoryList.add(new Category(rs.getInt("CategoryID"),
                        rs.getNString("CategoryName"),
                        rs.getInt("ParentCategoryID")));
            }
        }
    }

    public Category readCategory(int id) throws SQLException {
        if (categoryList.isEmpty()) {
            return null;
        }
        for (Category c : categoryList) {
            if (c.getCategoryId() == id) {
                return c;
            }
        }
        return null;
    }

    public void addCategory(Category cate) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("INSERT INTO [dbo].[Category] VALUES(?,?,?)");
            pre.setInt(1, cate.getCategoryId());
            pre.setString(2, cate.getCategoryName());
            pre.setInt(3, cate.getParentCategoryID());
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                viewCategory();
                upCount = 0;
            }
        }
    }

    public void updateCategory(Category cate) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            PreparedStatement pre = DBConnection.getPreparedStatement("UPDATE [dbo].[Category] SET "
                    + "[CategoryID] = ?"
                    + ", [CategoryName] = ?"
                    + ", [ParentCategoryID] = ?");
            pre.setInt(1, cate.getCategoryId());
            pre.setString(2, cate.getCategoryName());
            pre.setInt(3, cate.getParentCategoryID());
            upCount = pre.executeUpdate();
            pre.close();
            DBConnection.Disconnect();
            if (upCount > 0) {
                categoryList.set(categoryList.indexOf(readCategory(cate.getCategoryId())), cate);
                upCount = 0;
            }
        }
    }

    private boolean hasChildCategory(int id) throws SQLException {
        try ( PreparedStatement pre = DBConnection.getPreparedStatement("SELECT COUNT(*) FROM [dbo].[Category] WHERE ParentCategoryID = ?")) {
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
        String sql = "UPDATE [dbo].[Category] SET ParentCategoryID = NULL WHERE ParentCategoryID = ?";
        try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
            pre.setInt(1, parentId);
            pre.executeUpdate();
        }
    }

    public boolean removeCategory(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            if (hasChildCategory(id)) {
                return false;
            }
            updateChildCategory(id);
            String sql = "DELETE FROM [dbo].[Category] WHERE CategoryID = ?";
            try ( PreparedStatement pre = DBConnection.getPreparedStatement(sql)) {
                pre.setInt(1, id);
                upCount = pre.executeUpdate();
            }
            DBConnection.Disconnect();
            if (upCount > 0) {
                viewCategory();
                upCount = 0;
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Category> getAllCategories() {
        return new ArrayList<>(categoryList); // Trả về bản sao của categoryList
    }
}
