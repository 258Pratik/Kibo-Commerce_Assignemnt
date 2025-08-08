package com.kiboassessment.tests;

import com.kiboassessment.listeners.TestListener;
import com.kiboassessment.pages.*;
import com.kiboassessment.utils.ConfigReader;
import com.kiboassessment.utils.TestUtils;
import com.kiboassessment.utils.WebDriverManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * End-to-End Purchase Flow Test
 * This test class implements the complete purchase cycle on the demo e-commerce site
 */
public class EndToEndPurchaseTest {
    private static final Logger logger = LogManager.getLogger(EndToEndPurchaseTest.class);
    
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage orderConfirmationPage;

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up test environment");
        TestListener.logStep("Initializing WebDriver and page objects");
        
        // Initialize WebDriver
        WebDriverManagerUtil.initializeDriver();
        
        // Initialize page objects
        homePage = new HomePage();
        searchResultsPage = new SearchResultsPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        orderConfirmationPage = new OrderConfirmationPage();
        
        logger.info("Test environment setup completed");
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Cleaning up test environment");
        WebDriverManagerUtil.quitDriver();
        logger.info("Test environment cleanup completed");
    }

    @Test(description = "End-to-End Purchase Flow Test")
    public void testEndToEndPurchaseFlow() {
        try {
            // Step 1: Launch browser and navigate to homepage
            TestListener.logStep("Step 1: Launch browser and navigate to homepage");
            homePage.navigateToHomePage();
            Assert.assertEquals(WebDriverManagerUtil.getCurrentUrl(), ConfigReader.getBaseUrl(), 
                    "Failed to navigate to homepage");
            TestListener.logInfo("Successfully navigated to homepage: " + ConfigReader.getBaseUrl());
            TestUtils.takeScreenshotAndAttachToReport("HomePage", "Homepage loaded successfully");

            // Step 2: Login with credentials
            TestListener.logStep("Step 2: Login with provided credentials");
            homePage.login(ConfigReader.getEmail(), ConfigReader.getPassword());
            
            // Verify login success
            Assert.assertTrue(homePage.verifyLoginSuccess(), "Login failed");
            TestListener.logInfo("Login successful with email: " + ConfigReader.getEmail());
            TestUtils.takeScreenshotAndAttachToReport("Login", "Login completed successfully");

            // Step 3: Search for a product
            TestListener.logStep("Step 3: Search for product: " + ConfigReader.getSearchProduct());
            homePage.searchProduct(ConfigReader.getSearchProduct());
            
            // Wait for search results and verify
            searchResultsPage.waitForSearchResults();
            Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(), 
                    "No search results found for: " + ConfigReader.getSearchProduct());
            TestListener.logInfo("Search results displayed successfully. Found " + 
                    searchResultsPage.getNumberOfResults() + " products");
            TestUtils.takeScreenshotAndAttachToReport("Search Page", "Product Search sucessfully");


            // Step 4: Add a product to cart from search results
            TestListener.logStep("Step 4: Add product to cart");
            homePage.refreshPage();
            boolean productAdded = searchResultsPage.addProductToCartByName(ConfigReader.getProductName());
            Assert.assertTrue(productAdded, "Failed to add product to cart: " + ConfigReader.getProductName());
            TestListener.logInfo("Successfully added product to cart: " + ConfigReader.getProductName());
            TestUtils.takeScreenshotAndAttachToReport("Add to Card Page", "Add to Card Product sucessfully");

            searchResultsPage.clickProductdetailsPageAddToCart();
            Assert.assertTrue(productAdded, "Failed to add product to cart: " + ConfigReader.getProductName());

            // Step 5: Navigate to cart and verify product is listed
            TestListener.logStep("Step 5: Navigate to cart and verify product");
            homePage.clickCartLink();
            cartPage.waitForCartToLoad();
            
            // Verify product is in cart
            Assert.assertTrue(cartPage.verifyCartContainsProduct(ConfigReader.getProductName()), 
                    "Product not found in cart: " + ConfigReader.getProductName());
            TestListener.logInfo("Product verified in cart: " + ConfigReader.getProductName());
            TestListener.logInfo("Cart total: " + cartPage.getCartTotal());
            TestUtils.takeScreenshotAndAttachToReport("Cart Page", "Product Cart Added sucessfully");


            // Step 6: Proceed to checkout
            TestListener.logStep("Step 6: Proceed to checkout");
            cartPage.clickCheckoutButton();
            TestListener.logInfo("Proceeded to checkout");

            // Step 7: Fill billing and shipping details
            TestListener.logStep("Step 7: Fill billing and shipping details");
            checkoutPage.clickAgreeTermsOfService();
            checkoutPage.clickCheckoutBillingPage();
            checkoutPage.completeCheckout();
            TestListener.logInfo("Completed checkout process with billing and shipping details");

            // Step 8: Verify order confirmation
            TestListener.logStep("Step 8: Verify order confirmation");
            orderConfirmationPage.waitForOrderConfirmation();
            TestUtils.takeScreenshotAndAttachToReport("Confirmation Page", "Sucessfully Checkout With Added Product");

            // Verify success message contains expected text
            Assert.assertTrue(orderConfirmationPage.verifySuccessMessage("successfully"), 
            "Order success message not displayed correctly");
            
            // Verify order success
            Assert.assertTrue(orderConfirmationPage.verifyOrderSuccess(), "Order confirmation failed");
            TestListener.logInfo("Order confirmation successful");
            orderConfirmationPage.clickHereForOrderDetailsLink();
            
            // Verify order confirmation message
            String orderInfo = orderConfirmationPage.getCompleteOrderInformation();
            TestListener.logInfo("Order Information:\n" + orderInfo);
            TestUtils.takeScreenshotAndAttachToReport("OrderConfirmation", "Order confirmation page");
            
            // Step 9: Logout
            TestListener.logStep("Step 9: Logout from application");
            homePage.clickLogoutLink();
            
            // Verify logout success
            Assert.assertTrue(homePage.verifyLogoutSuccess(), "Logout failed");
            TestListener.logInfo("Logout successful");

            TestListener.logInfo("End-to-End Purchase Flow Test completed successfully!");
            
        } catch (Exception e) {
            TestListener.logError("Test failed with exception: " + e.getMessage());
            logger.error("Test failed", e);
            throw e;
        }
    }

    @Test(description = "Verify Login Functionality",enabled = false)
    public void testLoginFunctionality() {
        TestListener.logStep("Testing login functionality");
        
        homePage.navigateToHomePage();
        homePage.login(ConfigReader.getEmail(), ConfigReader.getPassword());
        
        Assert.assertTrue(homePage.verifyLoginSuccess(), "Login verification failed");
        TestListener.logInfo("Login functionality test passed");
    }

    @Test(description = "Verify Product Search Functionality",enabled = false)
    public void testProductSearchFunctionality() {
        TestListener.logStep("Testing product search functionality");
        
        homePage.navigateToHomePage();
        homePage.login(ConfigReader.getEmail(), ConfigReader.getPassword());
        homePage.searchProduct(ConfigReader.getSearchProduct());
        
        searchResultsPage.waitForSearchResults();
        Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(), "Search results not displayed");
        Assert.assertTrue(searchResultsPage.verifyProductInResults(ConfigReader.getProductName()), 
                "Expected product not found in search results");
        
        TestListener.logInfo("Product search functionality test passed");
    }

    @Test(description = "Verify Add to Cart Functionality", enabled = false)
    public void testAddToCartFunctionality() {
        TestListener.logStep("Testing add to cart functionality");
        
        homePage.navigateToHomePage();
        homePage.login(ConfigReader.getEmail(), ConfigReader.getPassword());
        homePage.searchProduct(ConfigReader.getSearchProduct());
        
        searchResultsPage.waitForSearchResults();
        boolean productAdded = searchResultsPage.addProductToCartByName(ConfigReader.getProductName());
        Assert.assertTrue(productAdded, "Failed to add product to cart");
        
        homePage.clickCartLink();
        cartPage.waitForCartToLoad();
        Assert.assertTrue(cartPage.verifyCartContainsProduct(ConfigReader.getProductName()), 
                "Product not found in cart");
        
        TestListener.logInfo("Add to cart functionality test passed");
    }
} 