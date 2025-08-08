package com.kiboassessment.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration properties from config.properties file
 */
public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    static {
        loadProperties();
    }

    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("Configuration properties loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading configuration properties: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    /**
     * Get property value by key
     * @param key property key
     * @return property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found: " + key);
            return "";
        }
        return value;
    }

    /**
     * Get base URL from configuration
     * @return base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Get browser type from configuration
     * @return browser type
     */
    public static String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Get implicit wait time from configuration
     * @return implicit wait time
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    /**
     * Get explicit wait time from configuration
     * @return explicit wait time
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    /**
     * Get email from configuration
     * @return email
     */
    public static String getEmail() {
        return getProperty("email");
    }

    /**
     * Get password from configuration
     * @return password
     */
    public static String getPassword() {
        return getProperty("password");
    }

    /**
     * Get search product from configuration
     * @return search product
     */
    public static String getSearchProduct() {
        return getProperty("search.product");
    }

    /**
     * Get product name from configuration
     * @return product name
     */
    public static String getProductName() {
        return getProperty("product.name");
    }
} 