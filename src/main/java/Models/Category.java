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
    private int parentCategoryID;

    public Category(int categoryId, String categoryName, int parentCategoryID) {
        this.categoryId = categoryId;
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

    public int getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(int parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }


}
