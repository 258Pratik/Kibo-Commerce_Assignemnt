package com.kiboassessment.pages;

import com.kiboassessment.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.FindAll;

import java.util.List;

/**
 * SearchResultsPage class representing the search results page
 */
public class SearchResultsPage extends BasePage {

    // Search results elements
    @FindBy(css = ".product-item")
    private List<WebElement> productItems;

    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> productTitles;

    @FindBy(css = "input[value='Add to cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".product-item .product-title")
    private List<WebElement> productNames;

    // No results message
    @FindBy(css = ".no-result")
    private WebElement noResultsMessage;

    // Search results count
    @FindBy(css = ".search-results")
    private WebElement searchResultsText;

    // Product details link
    @FindBy(css = ".product-item .product-title a")
    private WebElement firstProductLink;

    @FindBy(css = ".add-to-cart .button-1 ")
    private WebElement productDetailsPageAddToCart;

    /**
     * Get number of search results
     * @return number of products found
     */
    public int getNumberOfResults() {
        return productItems.size();
    }

    /**
     * Check if search results are displayed
     * @return true if results displayed, false otherwise
     */
    public boolean areSearchResultsDisplayed() {
        return getNumberOfResults() > 0;
    }

    /**
     * Check if no results message is displayed
     * @return true if no results message displayed, false otherwise
     */
    public boolean isNoResultsMessageDisplayed() {
        return TestUtils.isElementDisplayed(noResultsMessage);
    }

    /**
     * Get product names from search results
     * @return list of product names
     */
    public List<String> getProductNames() {
        return productNames.stream()
                .map(TestUtils::getElementText)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find product by name
     * @param productName name of the product to find
     * @return index of the product, -1 if not found
     */
    public int findProductByName(String productName) {
        List<String> names = getProductNames();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).contains(productName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Add first product to cart
     */
    public void addFirstProductToCart() {
        if (getNumberOfResults() > 0) {
            TestUtils.safeClick(addToCartButtons.get(0));
            logger.info("Added first product to cart");
        } else {
            logger.warn("No products found to add to cart");
        }
    }

    /**
     * Add product to cart by name
     * @param productName name of the product to add
     * @return true if product added successfully, false otherwise
     */
    public boolean addProductToCartByName(String productName) {
        int productIndex = findProductByName(productName);
        logger.info("product Name found at index: " + productIndex);
        if (productIndex >= 0 && productIndex < addToCartButtons.size()) {
            TestUtils.safeClick(addToCartButtons.get(productIndex));
            logger.info("Added product to cart: " + productName);
            return true;
        } else {
            logger.warn("Product not found: " + productName);
            return false;
        }
    }

    /**
     * Click on add to card on product Details Page
     */
    public void clickProductdetailsPageAddToCart() {
            TestUtils.safeClick(productDetailsPageAddToCart);
            logger.info("Clicked on Add to cart button on product Details Page");
    }

    /**
     * Click on first product to view details
     */
    public void clickFirstProduct() {
        if (getNumberOfResults() > 0) {
            TestUtils.safeClick(firstProductLink);
            logger.info("Clicked on first product");
        } else {
            logger.warn("No products found to click");
        }
    }

    /**
     * Click on product by name
     * @param productName name of the product to click
     * @return true if product clicked successfully, false otherwise
     */
    public boolean clickProductByName(String productName) {
        int productIndex = findProductByName(productName);
        if (productIndex >= 0 && productIndex < productTitles.size()) {
            TestUtils.safeClick(productTitles.get(productIndex));
            logger.info("Clicked on product: " + productName);
            return true;
        } else {
            logger.warn("Product not found: " + productName);
            return false;
        }
    }

    /**
     * Get search results text
     * @return search results text
     */
    public String getSearchResultsText() {
        return TestUtils.getElementText(searchResultsText);
    }

    /**
     * Verify product is in search results
     * @param productName name of the product to verify
     * @return true if product found, false otherwise
     */
    public boolean verifyProductInResults(String productName) {
        return findProductByName(productName) >= 0;
    }

    /**
     * Wait for search results to load
     */
    public void waitForSearchResults() {
        TestUtils.waitForElementPresent(org.openqa.selenium.By.cssSelector(".product-item"), 
                com.kiboassessment.utils.ConfigReader.getExplicitWait());
        logger.info("Search results loaded");
    }
} 