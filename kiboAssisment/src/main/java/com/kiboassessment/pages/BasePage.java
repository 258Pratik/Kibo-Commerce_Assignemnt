package com.kiboassessment.pages;

import com.kiboassessment.utils.TestUtils;
import com.kiboassessment.utils.WebDriverManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base page class that all page objects extend
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger;

    /**
     * Constructor to initialize page elements
     */
    public BasePage() {
        this.driver = WebDriverManagerUtil.getDriver();
        this.wait = WebDriverManagerUtil.getWait();
        this.logger = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for page to load
     */
    public void waitForPageLoad() {
        TestUtils.waitForPageLoad();
    }

    /**
     * Get page title
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current URL
     * @return current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Navigate to URL
     * @param url URL to navigate to
     */
    public void navigateTo(String url) {
        logger.info("Navigating to: " + url);
        driver.get(url);
        waitForPageLoad();
    }

    /**
     * Refresh page
     */
    public void refreshPage() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
        waitForPageLoad();
    }

    /**
     * Go back to previous page
     */
    public void goBack() {
        logger.info("Going back to previous page");
        driver.navigate().back();
        waitForPageLoad();
    }

    /**
     * Go forward to next page
     */
    public void goForward() {
        logger.info("Going forward to next page");
        driver.navigate().forward();
        waitForPageLoad();
    }
} 