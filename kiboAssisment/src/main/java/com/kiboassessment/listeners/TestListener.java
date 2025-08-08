package com.kiboassessment.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.kiboassessment.utils.ConfigReader;
import com.kiboassessment.utils.TestUtils;
import com.kiboassessment.utils.WebDriverManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

/**
 * TestNG listener for handling test events and generating reports
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /**
     * Initialize ExtentReports
     */
    public static void initializeExtentReports() {
        if (extent == null) {
            extent = new ExtentReports();
            String reportPath = ConfigReader.getProperty("extent.report.path");
            
            // Create directory if it doesn't exist
            File reportDir = new File(reportPath).getParentFile();
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("E-commerce Test Automation Report");
            sparkReporter.config().setReportName("End-to-End Purchase Flow Test Report");
            sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
            
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "Demo Web Shop");
            extent.setSystemInfo("URL", ConfigReader.getBaseUrl());
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            
            logger.info("ExtentReports initialized");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started: " + result.getName());
        initializeExtentReports();
        
        ExtentTest extentTest = extent.createTest(result.getName());
        extentTest.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        test.set(extentTest);
        
        test.get().log(Status.INFO, "Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: " + result.getName());
        test.get().log(Status.PASS, "Test passed: " + result.getName());
        
        // Add Base64 screenshot for successful tests
        String base64Screenshot = TestUtils.takeScreenshotAsBase64(result.getName() + "_PASS");
        if (base64Screenshot != null) {
            attachScreenshotToReport("Test Pass Screenshot", base64Screenshot);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: " + result.getName());
        logger.error("Failure reason: " + result.getThrowable().getMessage());
        
        test.get().log(Status.FAIL, "Test failed: " + result.getName());
        test.get().log(Status.FAIL, "Failure reason: " + result.getThrowable().getMessage());
        
        // Add Base64 screenshot for failed tests
        String base64Screenshot = TestUtils.takeScreenshotAsBase64(result.getName() + "_FAIL");
        if (base64Screenshot != null) {
            attachScreenshotToReport("Test Fail Screenshot", base64Screenshot);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: " + result.getName());
        test.get().log(Status.SKIP, "Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("Test failed but within success percentage: " + result.getName());
        test.get().log(Status.WARNING, "Test failed but within success percentage: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test suite started: " + context.getName());
        initializeExtentReports();
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test suite finished: " + context.getName());
        
        // Flush ExtentReports
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports generated successfully");
        }
        
        // Quit WebDriver
        WebDriverManagerUtil.quitDriver();
    }

    /**
     * Log info message to ExtentReports
     * @param message message to log
     */
    public static void logInfo(String message) {
        if (test.get() != null) {
            test.get().log(Status.INFO, message);
        }
        logger.info(message);
    }

    /**
     * Log warning message to ExtentReports
     * @param message message to log
     */
    public static void logWarning(String message) {
        if (test.get() != null) {
            test.get().log(Status.WARNING, message);
        }
        logger.warn(message);
    }

    /**
     * Log error message to ExtentReports
     * @param message message to log
     */
    public static void logError(String message) {
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
        }
        logger.error(message);
    }

    /**
     * Log step to ExtentReports
     * @param step step description
     */
    public static void logStep(String step) {
        if (test.get() != null) {
            test.get().log(Status.INFO, "STEP: " + step);
        }
        logger.info("STEP: " + step);
    }

    /**
     * Attach Base64 screenshot to ExtentReports
     * @param description description for the screenshot
     * @param base64Screenshot Base64 encoded screenshot string
     */
    public static void attachScreenshotToReport(String description, String base64Screenshot) {
        if (test.get() != null && base64Screenshot != null) {
            try {
                test.get().info(description, 
                    com.aventstack.extentreports.MediaEntityBuilder
                        .createScreenCaptureFromBase64String(base64Screenshot)
                        .build());
                logger.info("Screenshot attached to report: " + description);
            } catch (Exception e) {
                logger.error("Failed to attach screenshot to report: " + e.getMessage());
            }
        }
    }
} 