/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Tran Le Gia Huy - CE180068
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private Integer parentCategoryID;
     private String parentCategoryName;

    public Category(int categoryId, String categoryName, Integer parentCategoryID) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentCategoryID = parentCategoryID;
    }
    
    public Category(int categoryId, String categoryName, Integer parentCategoryID, String parentCategoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentCategoryID = parentCategoryID;
        this.parentCategoryName = parentCategoryName;
    }
    

    public Category(String categoryName, Integer parentCategoryID) {
        this.categoryName = categoryName;
        this.parentCategoryID = parentCategoryID;
    }
    
    

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(Integer parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
}
