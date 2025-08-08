package com.kiboassessment.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for common test operations
 */
public class TestUtils {
    private static final Logger logger = LogManager.getLogger(TestUtils.class);

    /**
     * Wait for element to be visible
     * @param element WebElement to wait for
     * @param timeout timeout in seconds
     */
    public static void waitForElementVisible(WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverManagerUtil.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.info("PASS: Element is visible: " + element);
        } catch (TimeoutException e) {
            logger.error("Element not visible within timeout: " + element);
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     * @param element WebElement to wait for
     * @param timeout timeout in seconds
     */
    public static void waitForElementClickable(WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverManagerUtil.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.info("PASS: Element is clickable: " + element);
        } catch (TimeoutException e) {
            logger.error("Element not clickable within timeout: " + element);
            throw e;
        }
    }

    /**
     * Wait for element to be present in DOM
     * @param locator By locator
     * @param timeout timeout in seconds
     */
    public static void waitForElementPresent(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverManagerUtil.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("PASS: Element is present: " + locator);
        } catch (TimeoutException e) {
            logger.error("Element not present within timeout: " + locator);
            throw e;
        }
    }

    

    /**
     * Safe click with wait and highlight
     * @param element WebElement to click
     */
    public static void safeClick(WebElement element) {
        try {
            waitForElementClickable(element, ConfigReader.getExplicitWait());
            element.click();
            logger.info("PASS: Successfully clicked element: " + element);
        } catch (Exception e) {
            logger.error("Failed to click element: " + element + ", Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Safe send keys with wait and highlight
     * @param element WebElement to send keys to
     * @param text text to send
     */
    public static void safeSendKeys(WebElement element, String text) {
        try {
            waitForElementVisible(element, ConfigReader.getExplicitWait());
            
            element.clear();
            element.sendKeys(text);
            logger.info("PASS: Successfully sent keys to element: " + element + ", Text: " + text);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: " + element + ", Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get element text safely with highlight
     * @param element WebElement to get text from
     * @return element text
     */
    public static String getElementText(WebElement element) {
        try {
            waitForElementVisible(element, ConfigReader.getExplicitWait());
            
            String text = element.getText();
            logger.info("PASS: Got text from element: " + element + ", Text: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + element + ", Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Check if element is displayed
     * @param element WebElement to check
     * @return true if displayed, false otherwise
     */
    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Take screenshot and return file path
     * @param testName name of the test
     * @return screenshot file path
     */
    public static String takeScreenshot(String testName) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            String screenshotPath = ConfigReader.getProperty("screenshot.path") + fileName;
            
            // Create directory if it doesn't exist
            Path directory = Paths.get(ConfigReader.getProperty("screenshot.path"));
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            TakesScreenshot ts = (TakesScreenshot) WebDriverManagerUtil.getDriver();
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
            
            logger.info("PASS: Screenshot taken: " + screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            logger.error("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Take screenshot and return Base64 string
     * @param testName name of the test
     * @return Base64 encoded screenshot string
     */
    public static String takeScreenshotAsBase64(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) WebDriverManagerUtil.getDriver();
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
            logger.info("PASS: Base64 screenshot taken for: " + testName);
            return base64Screenshot;
        } catch (Exception e) {
            logger.error("Failed to take Base64 screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Take screenshot and attach to ExtentReports
     * @param testName name of the test
     * @param description description for the screenshot
     */
    public static void takeScreenshotAndAttachToReport(String testName, String description) {
        try {
            String base64Screenshot = takeScreenshotAsBase64(testName);
            if (base64Screenshot != null) {
                // Attach to ExtentReports using TestListener
                com.kiboassessment.listeners.TestListener.attachScreenshotToReport(description, base64Screenshot);
                logger.info("PASS: Screenshot attached to report: " + description);
            }
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to report: " + e.getMessage());
        }
    }

    /**
     * Assert element is displayed with highlight
     * @param element WebElement to check
     * @param message assertion message
     */
    public static void assertElementDisplayed(WebElement element, String message) {
        
        Assert.assertTrue(isElementDisplayed(element), message);
        logger.info("PASS: Assertion passed: " + message);
    }

    /**
     * Assert text contains expected value
     * @param actual actual text
     * @param expected expected text
     * @param message assertion message
     */
    public static void assertTextContains(String actual, String expected, String message) {
        Assert.assertTrue(actual.contains(expected), message + " - Expected: " + expected + ", Actual: " + actual);
        logger.info("PASS: Assertion passed: " + message);
    }

    /**
     * Assert text equals expected value
     * @param actual actual text
     * @param expected expected text
     * @param message assertion message
     */
    public static void assertTextEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
        logger.info("PASS: Assertion passed: " + message);
    }

    /**
     * Scroll to element with highlight
     * @param element WebElement to scroll to
     */
    public static void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) WebDriverManagerUtil.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            
            logger.info("PASS: Scrolled to element: " + element);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: " + element + ", Error: " + e.getMessage());
        }
    }

    /**
     * Wait for page to load
     */
    public static void waitForPageLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverManagerUtil.getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            logger.info("PASS: Page loaded successfully");
        } catch (Exception e) {
            logger.error("Failed to wait for page load: " + e.getMessage());
        }
    }
} 