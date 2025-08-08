package com.kiboassessment.pages;

import com.kiboassessment.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * CartPage class representing the shopping cart page
 */
public class CartPage extends BasePage {

    // Cart elements
    @FindBy(css = ".cart-item-row")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart-item-row .product-name")
    private List<WebElement> cartItemNames;

    @FindBy(css = ".cart-item-row .product-unit-price")
    private List<WebElement> cartItemPrices;

    @FindBy(css = ".cart-item-row .qty-input")
    private List<WebElement> cartItemQuantities;

    @FindBy(css = ".cart-item-row .remove-from-cart")
    private List<WebElement> removeButtons;

    // Cart summary
    @FindBy(css = ".cart-total")
    private WebElement cartTotal;

    @FindBy(css = ".order-summary-content")
    private WebElement orderSummary;

    // Action buttons
    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = "input[name='updatecart']")
    private WebElement updateCartButton;

    @FindBy(css = "input[name='continueshopping']")
    private WebElement continueShoppingButton;

    @FindBy(css = "input[name='clearcart']")
    private WebElement clearCartButton;

    // Empty cart message
    @FindBy(css = ".order-summary-content .no-data")
    private WebElement emptyCartMessage;

    /**
     * Get number of items in cart
     * @return number of cart items
     */
    public int getNumberOfCartItems() {
        return cartItems.size();
    }

    /**
     * Check if cart is empty
     * @return true if cart is empty, false otherwise
     */
    public boolean isCartEmpty() {
        return getNumberOfCartItems() == 0 || TestUtils.isElementDisplayed(emptyCartMessage);
    }

    /**
     * Get cart item names
     * @return list of cart item names
     */
    public List<String> getCartItemNames() {
        return cartItemNames.stream()
                .map(TestUtils::getElementText)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get cart item prices
     * @return list of cart item prices
     */
    public List<String> getCartItemPrices() {
        return cartItemPrices.stream()
                .map(TestUtils::getElementText)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get cart total
     * @return cart total text
     */
    public String getCartTotal() {
        return TestUtils.getElementText(cartTotal);
    }

    /**
     * Find cart item by name
     * @param productName name of the product to find
     * @return index of the cart item, -1 if not found
     */
    public int findCartItemByName(String productName) {
        List<String> names = getCartItemNames();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).contains(productName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Verify product is in cart
     * @param productName name of the product to verify
     * @return true if product found in cart, false otherwise
     */
    public boolean verifyProductInCart(String productName) {
        return findCartItemByName(productName) >= 0;
    }

    /**
     * Remove item from cart by name
     * @param productName name of the product to remove
     * @return true if product removed successfully, false otherwise
     */
    public boolean removeItemFromCart(String productName) {
        int itemIndex = findCartItemByName(productName);
        if (itemIndex >= 0 && itemIndex < removeButtons.size()) {
            TestUtils.safeClick(removeButtons.get(itemIndex));
            logger.info("Removed item from cart: " + productName);
            return true;
        } else {
            logger.warn("Item not found in cart: " + productName);
            return false;
        }
    }

    /**
     * Update item quantity
     * @param productName name of the product
     * @param quantity new quantity
     * @return true if quantity updated successfully, false otherwise
     */
    public boolean updateItemQuantity(String productName, int quantity) {
        int itemIndex = findCartItemByName(productName);
        if (itemIndex >= 0 && itemIndex < cartItemQuantities.size()) {
            TestUtils.safeSendKeys(cartItemQuantities.get(itemIndex), String.valueOf(quantity));
            TestUtils.safeClick(updateCartButton);
            logger.info("Updated quantity for item: " + productName + " to " + quantity);
            return true;
        } else {
            logger.warn("Item not found in cart: " + productName);
            return false;
        }
    }

    /**
     * Click checkout button
     */
    public void clickCheckoutButton() {
        TestUtils.safeClick(checkoutButton);
        logger.info("Clicked checkout button");
    }

    /**
     * Click continue shopping button
     */
    public void clickContinueShoppingButton() {
        TestUtils.safeClick(continueShoppingButton);
        logger.info("Clicked continue shopping button");
    }

    /**
     * Click clear cart button
     */
    public void clickClearCartButton() {
        TestUtils.safeClick(clearCartButton);
        logger.info("Clicked clear cart button");
    }

    /**
     * Get order summary text
     * @return order summary text
     */
    public String getOrderSummaryText() {
        return TestUtils.getElementText(orderSummary);
    }

    /**
     * Verify cart contains expected product
     * @param expectedProductName expected product name
     * @return true if cart contains expected product, false otherwise
     */
    public boolean verifyCartContainsProduct(String expectedProductName) {
        return verifyProductInCart(expectedProductName);
    }

    /**
     * Wait for cart to load
     */
    public void waitForCartToLoad() {
        TestUtils.waitForElementPresent(org.openqa.selenium.By.cssSelector(".cart-item-row"), 
                com.kiboassessment.utils.ConfigReader.getExplicitWait());
        logger.info("Cart loaded");
    }
} 