package com.kiboassessment.pages;

import com.kiboassessment.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * HomePage class representing the main homepage of the demo e-commerce site
 */
public class HomePage extends BasePage {

    // Header elements
    @FindBy(css = "a.ico-login")
    private WebElement loginLink;

    @FindBy(css = "a.ico-logout")
    private WebElement logoutLink;

    @FindBy(css = "a.ico-account")
    private WebElement accountLink;

    @FindBy(css = "a.ico-cart")
    private WebElement cartLink;

    // Search elements
    @FindBy(id = "small-searchterms")
    private WebElement searchBox;

    @FindBy(css = "input[type='submit'][value='Search']")
    private WebElement searchButton;

    // Login form elements
    @FindBy(id = "Email")
    private WebElement emailField;

    @FindBy(id = "Password")
    private WebElement passwordField;

    @FindBy(css = "input[type='submit'][value='Log in']")
    private WebElement loginButton;

    @FindBy(css = ".validation-summary-errors")
    private WebElement loginErrorMessage;

    // Welcome message
    @FindBy(css = ".header-links-wrapper .account")
    private WebElement welcomeMessage;

    /**
     * Navigate to homepage
     */
    public void navigateToHomePage() {
        navigateTo(com.kiboassessment.utils.ConfigReader.getBaseUrl());
        logger.info("Navigated to homepage");
    }

    /**
     * Click login link
     */
    public void clickLoginLink() {
        TestUtils.safeClick(loginLink);
        logger.info("Clicked login link");
    }

    /**
     * Enter email in login form
     * @param email email to enter
     */
    public void enterEmail(String email) {
        TestUtils.safeSendKeys(emailField, email);
        logger.info("Entered email: " + email);
    }

    /**
     * Enter password in login form
     * @param password password to enter
     */
    public void enterPassword(String password) {
        TestUtils.safeSendKeys(passwordField, password);
        logger.info("Entered password");
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        TestUtils.safeClick(loginButton);
        logger.info("Clicked login button");
    }

    /**
     * Login with credentials
     * @param email email
     * @param password password
     */
    public void login(String email, String password) {
        clickLoginLink();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        logger.info("Login completed");
    }

    /**
     * Check if user is logged in
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return TestUtils.isElementDisplayed(logoutLink);
    }

    /**
     * Check if user is logged out
     * @return true if logged out, false otherwise
     */
    public boolean isLoggedOut() {
        return TestUtils.isElementDisplayed(loginLink);
    }

    /**
     * Get welcome message
     * @return welcome message text
     */
    public String getWelcomeMessage() {
        return TestUtils.getElementText(welcomeMessage);
    }

    /**
     * Search for a product
     * @param productName product name to search
     */
    public void searchProduct(String productName) {
        TestUtils.safeSendKeys(searchBox, productName);
        TestUtils.safeClick(searchButton);
        logger.info("Searched for product: " + productName);
    }

    /**
     * Click cart link
     */
    public void clickCartLink() {
        TestUtils.safeClick(cartLink);
        logger.info("Clicked cart link");
    }

    /**
     * Click logout link
     */
    public void clickLogoutLink() {
        TestUtils.safeClick(logoutLink);
        logger.info("Clicked logout link");
    }

    /**
     * Verify login success
     * @return true if login successful, false otherwise
     */
    public boolean verifyLoginSuccess() {
        return isLoggedIn() && !TestUtils.isElementDisplayed(loginErrorMessage);
    }

    /**
     * Verify logout success
     * @return true if logout successful, false otherwise
     */
    public boolean verifyLogoutSuccess() {
        return isLoggedOut();
    }
} 