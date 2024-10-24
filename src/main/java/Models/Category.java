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

    public Category(int categoryId, String categoryName, Integer parentCategoryID) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentCategoryID = parentCategoryID;
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

   


}
