package DAOs;

import DB.DBConnection;
import Models.Product;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDAOTest {

    private ProductDAO instance;

    public ProductDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Setting up ProductDAOTest class...");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Tearing down ProductDAOTest class...");
    }

    @Before
    public void setUp() {
        instance = new ProductDAO();
        System.out.println("Setting up for test case...");
    }

    @After
    public void tearDown() {
        instance = null;
        System.out.println("Tearing down after test case...");
        System.out.println();
    }

    // === isValidCategoryId Tests ===
    @Test
    public void testIsValidCategoryId_ValidId() {
        System.out.println("Testing isValidCategoryId with a valid ID...");
        int validCategoryId = 1;
        boolean result = instance.isValidCategoryId(validCategoryId);
        assertTrue("Valid Category ID should return true", result);
    }

    @Test
    public void testIsValidCategoryId_InvalidId() {
        System.out.println("Testing isValidCategoryId with an invalid ID...");
        int invalidCategoryId = -1;
        boolean result = instance.isValidCategoryId(invalidCategoryId);
        assertFalse("Invalid Category ID should return false", result);
    }

    @Test
    public void testIsValidCategoryId_ZeroId() {
        System.out.println("Testing isValidCategoryId with an ID of 0...");
        int zeroId = 0; 
        boolean result = instance.isValidCategoryId(zeroId);
        assertFalse("Category ID 0 should be invalid (adjust assertion if needed)", result); 
    }

    // === removeProduct (Soft Delete) Tests ===
    @Test
    public void testRemoveProduct_ExistingProduct() {
        System.out.println("Testing removeProduct (soft delete) with an existing product...");
        Product testProduct = new Product(0, "Product For Removal", "Description", 10.0f, "test.jpg", 1, 10, new Date(0), new Date(0));
        instance.addProduct(testProduct);
        int productIdToRemove = getLastInsertedProductId();
        
        boolean removeResult = instance.removeProduct(productIdToRemove);
        assertTrue("Soft delete should be successful", removeResult);

        Product removedProduct = instance.readProduct(productIdToRemove);
        assertNotNull("Product should still exist (soft-deleted)", removedProduct);
        assertEquals("Stock quantity should be -1 (soft-deleted)", -1, removedProduct.getStockQuantity());
        
        resetProductIdentity();
    }

    @Test
    public void testRemoveProduct_NonExistingId() {
        System.out.println("Testing removeProduct (soft delete) with a non-existing ID...");
        int nonExistingId = 99999; 
        boolean removeResult = instance.removeProduct(nonExistingId);
        assertFalse("Soft delete should fail for a non-existing ID", removeResult);
    }
    
    @Test
    public void testRemoveProduct_ZeroId() {
        System.out.println("Testing removeProduct (soft delete) with a zero ID...");
        int nonExistingId = 0; 
        boolean removeResult = instance.removeProduct(nonExistingId);
        assertFalse("Soft delete should fail for a zero ID", removeResult);
    }

    // === removeProductFinal (Hard Delete) Tests ===
    @Test
    public void testRemoveProductFinal_ExistingProduct() { 
        System.out.println("Testing removeProductFinal (hard delete) with an existing product...");
        Product productToDelete = new Product(0, "Product for Hard Delete", "Description", 15.0f, "test.jpg", 2, 15, new Date(0), new Date(0));
        instance.addProduct(productToDelete);
        int productIdToDelete = getLastInsertedProductId();

        boolean deleteResult = instance.removeProductFinal(productIdToDelete);
        assertTrue("Hard delete should be successful", deleteResult);

        Product deletedProduct = instance.readProduct(productIdToDelete);
        assertNull("Product should not exist after hard delete", deletedProduct);

        resetProductIdentity();
    }

    @Test
    public void testRemoveProductFinal_NonExistingId() {
        System.out.println("Testing removeProductFinal (hard delete) with a non-existing ID...");
        int nonExistingId = 99999; 
        boolean deleteResult = instance.removeProductFinal(nonExistingId);
        assertFalse("Hard delete should fail for a non-existing ID", deleteResult);
    }
    
    @Test
    public void testRemoveProductFinal_ZeroId() {
        System.out.println("Testing removeProductFinal (hard delete) with a zero ID...");
        int nonExistingId = 0; 
        boolean deleteResult = instance.removeProductFinal(nonExistingId);
        assertFalse("Hard delete should fail for a zero ID", deleteResult);
    }
    
    // === restoreProduct Tests ===
    @Test
    public void testRestoreProduct_ExistingProduct() {
        System.out.println("Testing restoreProduct with an existing product...");
        Product testProduct = new Product(0, "Product For Removal", "Description", 10.0f, "test.jpg", 1, 10, new Date(0), new Date(0));
        instance.addProduct(testProduct);
        int productIdToRestore = getLastInsertedProductId();
        
        boolean restoreResult = instance.restoreProduct(productIdToRestore);
        assertFalse("Restore product shouldn't be successful", restoreResult);
        Product restoredProduct = instance.readProduct(productIdToRestore);
        assertFalse("Stock quantity should not be 0", restoredProduct.getStockQuantity() == 0);
        
        instance.removeProduct(productIdToRestore);
        resetProductIdentity();
    }
    
    @Test
    public void testRestoreProduct_RemovedProduct() {
        System.out.println("Testing restoreProduct with an existing removed product...");
        Product testProduct = new Product(0, "Product For Removal", "Description", 10.0f, "test.jpg", 1, -1, new Date(0), new Date(0));
        instance.addProduct(testProduct);
        int productIdToRestore = getLastInsertedProductId();
        
        boolean restoreResult = instance.restoreProduct(productIdToRestore);
        assertTrue("Restore product should be successful", restoreResult);

        Product restoredProduct = instance.readProduct(productIdToRestore);
        assertTrue("Stock quantity should be 0", restoredProduct.getStockQuantity() == 0);
        
        instance.removeProduct(productIdToRestore);
        resetProductIdentity();
    }
    
    @Test
    public void testRestoreProduct_NonExistingId() {
        System.out.println("Testing restoreProduct with a not existing product...");
        int productIdToRestore = 99999;
        
        boolean restoreResult = instance.restoreProduct(productIdToRestore);
        assertFalse("Restore product should not be successful", restoreResult);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    // === Helper Methods ===
    private int getLastInsertedProductId() {
        int lastInsertedId = 0;
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sql = "SELECT IDENT_CURRENT('Product') AS LastID";
                try (PreparedStatement pre = DBConnection.getPreparedStatement(sql); 
                     ResultSet rs = pre.executeQuery()) {

                    if (rs.next()) {
                        lastInsertedId = rs.getInt("LastID");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error getting last inserted ID: " + ex.getMessage(), ex);
        } finally {
            DBConnection.Disconnect();
        }
        return lastInsertedId;
    }

    public void resetProductIdentity() {
        try {
            DBConnection.Connect();
            if (DBConnection.isConnected()) {
                String sqlGetLastId = "SELECT MAX(ProductID) AS LastID FROM Product";
                int lastId = 0;

                try (PreparedStatement preGetLastId = DBConnection.getPreparedStatement(sqlGetLastId);
                     ResultSet rs = preGetLastId.executeQuery()) {

                    if (rs.next()) {
                        lastId = rs.getInt("LastID");
                    }
                }

                if (lastId > 0) {
                    String sqlResetIdentity = "DBCC CHECKIDENT ('Product', RESEED, ?)";
                    try (PreparedStatement preResetIdentity = DBConnection.getPreparedStatement(sqlResetIdentity)) {
                        preResetIdentity.setInt(1, lastId); 
                        preResetIdentity.execute();
                        Logger.getLogger(ProductDAO.class.getName()).log(Level.INFO, "Product identity reset to: " + lastId);
                    }
                } else {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.INFO, "No products found to reset identity.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Error resetting product identity: " + ex.getMessage(), ex);
        } finally {
            DBConnection.Disconnect();
        }
    }
}