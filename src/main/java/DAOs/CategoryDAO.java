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

    public boolean removeCategory(int id) throws SQLException {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            if (hasChildCategory(id)) {
                return false;
            } else {
                // Tiến hành SOFT DELETE
                upCount = DBConnection.ExecuteUpdate("UPDATE Category SET IsActive = 0 WHERE CategoryID =" + id);
                if (upCount > 0) {
                    upCount = 0;
                    return true;
                }
            }
            DBConnection.Disconnect();
        }
        return false;
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

    public ArrayList<Category> getCategoryWithParentName() {
        ArrayList<Category> categoryListWithParentName = new ArrayList<>();
        DBConnection.Connect();

        if (DBConnection.isConnected()) {
            String query = " SELECT c.CategoryID, c.CategoryName, c.ParentCategoryID,\n"
                    + " p.CategoryName AS ParentCategoryName\n"
                    + " FROM Category c\n"
                    + " LEFT JOIN Category p ON c.ParentCategoryID = p.CategoryID\n"
                    + " WHERE c.IsActive = 1\n";
            try ( PreparedStatement pre = DBConnection.getPreparedStatement(query);  ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    int categoryId = rs.getInt("CategoryID");
                    String categoryName = rs.getString("CategoryName");
                    int parentCategoryId = rs.getInt("ParentCategoryID");
                    String parentCategoryName = rs.getString("ParentCategoryName");

                    // Nếu ParentCategoryName null, thay thế bằng "None"
                    parentCategoryName = (parentCategoryName != null) ? parentCategoryName : "None";

                    // Thêm category vào danh sách
                    categoryListWithParentName.add(
                            new Category(
                                    rs.getInt("CategoryID"),
                                    rs.getString("CategoryName"),
                                    rs.getInt("ParentCategoryID"),
                                    rs.getString("ParentCategoryName") != null ? rs.getString("ParentCategoryName") : "None"
                            )
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new ArrayList<>(); // Trả về danh sách rỗng nếu xảy ra lỗi
            } finally {
                DBConnection.Disconnect();
            }
        }
        return categoryListWithParentName;
    }

    public boolean reactivateCategory(int categoryId) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                String sql = "UPDATE Category SET IsActive = 1 WHERE CategoryID = ?";
                PreparedStatement statement = DBConnection.getPreparedStatement(sql);
                statement.setInt(1, categoryId);
                int rowsAffected = statement.executeUpdate();
                DBConnection.Disconnect();
                return rowsAffected > 0; // Return true if at least one row was updated
            } catch (SQLException e) {
                // Handle the exception appropriately (log, etc.)
                e.printStackTrace(); // For debugging purposes
                return false;
            }
        }
        return false; // Database connection failed
    }

    public String addOrUpdateCategory(Category cate) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                // Check if CategoryName already exists
                String sqlCheck = "SELECT * FROM Category WHERE CategoryName = ?";
                PreparedStatement checkStatement = DBConnection.getPreparedStatement(sqlCheck);
                checkStatement.setString(1, cate.getCategoryName());
                ResultSet rs = checkStatement.executeQuery();

                if (rs.next()) {
                    if (rs.getInt("IsActive") == 0) {
                        //Reactivate the category
                        String sqlUpdate = "UPDATE Category SET IsActive = 1, ParentCategoryID = ? WHERE CategoryName = ?";
                        PreparedStatement updateStatement = DBConnection.getPreparedStatement(sqlUpdate);
                        if (cate.getParentCategoryID() == null) {
                            updateStatement.setNull(1, java.sql.Types.INTEGER);
                        } else if (cate.getParentCategoryID() == 0) {
                            cate.setParentCategoryID(null);
                            updateStatement.setNull(1, java.sql.Types.INTEGER);
                        } else {
                            updateStatement.setInt(1, cate.getParentCategoryID());
                        };
                        updateStatement.setString(2, cate.getCategoryName());
                        updateStatement.executeUpdate();
                        DBConnection.Disconnect();
                        return "Add success"; // No error, successfully updated
                    } else {
                        // CategoryName already exists and is active
                        DBConnection.Disconnect();
                        return "There already exists a Category with this name.";
                    }
                } else {
                    // CategoryName does not exist, add new category
                    String sqlInsert = "INSERT INTO Category (CategoryName, ParentCategoryID) VALUES (?, ?)";
                    PreparedStatement insertStatement = DBConnection.getPreparedStatement(sqlInsert);
                    insertStatement.setString(1, cate.getCategoryName());
                    insertStatement.setInt(2, cate.getParentCategoryID() != null ? cate.getParentCategoryID() : 0);
                    insertStatement.executeUpdate();
                    DBConnection.Disconnect();
                    return null; // No error, successfully added
                }
            } catch (SQLException e) {
                return "Database error: " + e.getMessage();
            }
        }
        return "Database connection error.";
    }

    public boolean checkCategoryName(Category cate) {
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                // Check if CategoryName already exists
                String sqlCheck = "SELECT * FROM Category WHERE CategoryName = ?";
                PreparedStatement checkStatement = DBConnection.getPreparedStatement(sqlCheck);
                checkStatement.setString(1, cate.getCategoryName());
                ResultSet rs = checkStatement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    public ArrayList<Category> searchByParentCategoryId(int parentCategoryID) {
        ArrayList<Category> matchingCategories = new ArrayList<>();
        DBConnection.Connect();
        if (DBConnection.isConnected()) {
            try {
                String sql = "SELECT * FROM Category WHERE ParentCategoryID = ? AND IsActive = 1";
                PreparedStatement statement = DBConnection.getPreparedStatement(sql);
                statement.setInt(1, parentCategoryID);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    matchingCategories.add(new Category(
                            rs.getInt("CategoryID"),
                            rs.getString("CategoryName"),
                            rs.getInt("ParentCategoryID")
                    ));
                }
            } catch (SQLException e) {
                System.err.println("Error searching by ParentCategoryID: " + e.getMessage());
                //Consider throwing a custom exception or logging more details
            } finally {
                DBConnection.Disconnect();
            }
        }
        return matchingCategories;
    }
}
