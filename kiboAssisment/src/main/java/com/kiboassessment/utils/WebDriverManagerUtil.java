package com.kiboassessment.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WebDriver manager utility class for browser setup and management
 */
public class WebDriverManagerUtil {
    private static final Logger logger = LogManager.getLogger(WebDriverManagerUtil.class);
    private static WebDriver driver;
    private static WebDriverWait wait;

    /**
     * Initialize WebDriver based on configuration
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver() {
        if (driver == null) {
            String browser = ConfigReader.getBrowser().toLowerCase();
            logger.info("Initializing WebDriver for browser: " + browser);

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    logger.error("Unsupported browser: " + browser);
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
            
            // Initialize WebDriverWait
            wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));

            logger.info("WebDriver initialized successfully");
        }
        return driver;
    }

    /**
     * Get current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    /**
     * Get WebDriverWait instance
     * @return WebDriverWait instance
     */
    public static WebDriverWait getWait() {
        if (wait == null) {
            initializeDriver();
        }
        return wait;
    }

    /**
     * Quit WebDriver and clean up resources
     */
    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
            driver = null;
            wait = null;
        }
    }

    /**
     * Navigate to URL
     * @param url URL to navigate to
     */
    public static void navigateToUrl(String url) {
        logger.info("Navigating to URL: " + url);
        getDriver().get(url);
    }

    /**
     * Get current page title
     * @return page title
     */
    public static String getPageTitle() {
        return getDriver().getTitle();
    }

    /**
     * Get current page URL
     * @return current URL
     */
    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
} 