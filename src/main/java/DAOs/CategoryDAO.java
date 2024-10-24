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
            ResultSet rs = DBConnection.ExecuteQuery("SELECT * FROM Category");
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
                pre.setInt(2, cate.getParentCategoryID());                
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
                pre.setInt(2, cate.getParentCategoryID());
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

    public boolean removeCategory(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            if (hasChildCategory(id)) {
                return false;
            }
            updateChildCategory(id);
            upCount = DBConnection.ExecuteUpdate("DELETE FROM Category WHERE CategoryID = " + id);
            DBConnection.Disconnect();
            if (upCount > 0) {
                viewCategory(); // Cập nhật lại danh sách category
                upCount = 0;
                return true;
            }
        }
        return false;
    }
}
