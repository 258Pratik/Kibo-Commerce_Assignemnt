package com.kiboassessment.pages;

import com.kiboassessment.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * OrderConfirmationPage class representing the order confirmation page
 */
public class OrderConfirmationPage extends BasePage {

    // Order confirmation elements
    @FindBy(css = ".page-title")
    private WebElement pageTitle;

    @FindBy(css = ".order-completed")
    private WebElement orderCompletedMessage;

    @FindBy(css = ".order-overview")
    private WebElement orderOverView;

    @FindBy(css = ".order-details-area")
    private WebElement orderDetailArea;

    @FindBy(css = ".order-number")
    private WebElement orderNumber;

    @FindBy(css = ".order-details")
    private WebElement orderDetails;

    @FindBy(css = ".order-summary")
    private WebElement orderSummary;

    // Success message elements
    @FindBy(css = ".section.order-completed .title")
    private WebElement successMessage;

    @FindBy(css = ".section.order-completed")
    private WebElement orderCompletedSection;

    // Action buttons
    @FindBy(css = "input[value='Continue']")
    private WebElement continueButton;

    // Action buttons
    @FindBy(css = ".section.order-completed .details a")
    private WebElement orderDetailsLink;

    @FindBy(css = "a[href='/']")
    private WebElement homeLink;


    @FindBy(css = ".order-overview .order-total strong")
    private WebElement orderTotal;

    @FindBy(css = ".billing-info .name")
    private WebElement billingName;

    @FindBy(css = ".billing-info .email")
    private WebElement billingEmail;

    @FindBy(css = ".billing-info .phone")
    private WebElement billingPhone;

    @FindBy(css = ".billing-info .address1")
    private WebElement billingAddress;

    @FindBy(css = ".billing-info .city-state-zip")
    private WebElement billingCityStateZip;

    @FindBy(css = ".billing-info .country")
    private WebElement billingCountry;

    @FindBy(css = ".shipping-info .name")
    private WebElement shippingName;

    @FindBy(css = ".shipping-info .email")
    private WebElement shippingEmail;

    @FindBy(css = ".shipping-info .phone")
    private WebElement shippingPhone;

    @FindBy(css = ".shipping-info .address1")
    private WebElement shippingAddress;

    @FindBy(css = ".shipping-info .city-state-zip")
    private WebElement shippingCityStateZip;

    @FindBy(css = ".shipping-info .country")
    private WebElement shippingCountry;

    @FindBy(css = ".products .data-table .name em a")
    private WebElement productName;

    @FindBy(css = ".products .data-table .price")
    private WebElement productPrice;

    @FindBy(css = ".products .data-table .quantity")
    private WebElement productQuantity;

    @FindBy(css = ".products .data-table .total")
    private WebElement productTotal;


    /**
     * Get page title
     * @return page title text
     */
    public String getPageTitle() {
        return TestUtils.getElementText(pageTitle);
    }

/**
     * Get page title
     * @return page title text
     */
    public String getOrderOverView() {
        return TestUtils.getElementText(orderOverView);
    }

    /**
     * Get order completed message
     * @return order completed message text
     */
    public String getOrderCompletedMessage() {
        return TestUtils.getElementText(orderCompletedMessage);
    }

    /**
     * Get order number
     * @return order number text
     */
    public String getOrderNumber() {
        return TestUtils.getElementText(orderNumber);
    }

    /**
     * Get order details
     * @return order details text
     */
    public String getOrderDetails() {
        return TestUtils.getElementText(orderDetails);
    }

    /**
     * Get order summary
     * @return order summary text
     */
    public String getOrderSummary() {
        return TestUtils.getElementText(orderSummary);
    }

    /**
     * Get order summary
     * @return order summary text
     */
    public String getOrderDetailArea() {
        return TestUtils.getElementText(orderDetailArea);
    }

    /**
     * Get success message
     * @return success message text
     */
    public String getSuccessMessage() {
        return TestUtils.getElementText(successMessage);
    }

    /**
     * Check if order confirmation is displayed
     * @return true if order confirmation displayed, false otherwise
     */
    public boolean isOrderConfirmationDisplayed() {
        logger.info("isOrderConfirmationDisplayed ");
        return TestUtils.isElementDisplayed(orderCompletedSection);
    }

    /**
     * Check if success message is displayed
     * @return true if success message displayed, false otherwise
     */
    public boolean isSuccessMessageDisplayed() {
        logger.info("isSuccessMessageDisplayed ");
        return TestUtils.isElementDisplayed(successMessage);
    }

    /**
     * Verify order success
     * @return true if order successful, false otherwise
     */
    public boolean verifyOrderSuccess() {
        return isOrderConfirmationDisplayed() && isSuccessMessageDisplayed();
    }

    /**
     * Click continue button
     */
    public void clickContinueButton() {
        TestUtils.safeClick(continueButton);
        logger.info("Clicked continue button");
    }

    /**
     * Click here for Order Details link
     */
    public void clickHereForOrderDetailsLink() {
        TestUtils.safeClick(orderDetailsLink);
        logger.info("Clicked on order Details Link");
    }

    /**
     * Click home link
     */
    public void clickHomeLink() {
        TestUtils.safeClick(homeLink);
        logger.info("Clicked home link");
    }

    /**
     * Verify order confirmation message contains expected text
     * @param expectedText expected text to verify
     * @return true if message contains expected text, false otherwise
     */
    public boolean verifyOrderConfirmationMessage(String expectedText) {
        String actualMessage = getOrderCompletedMessage();
        return actualMessage.contains(expectedText);
    }

    /**
     * Verify success message contains expected text
     * @param expectedText expected text to verify
     * @return true if message contains expected text, false otherwise
     */
    public boolean verifySuccessMessage(String expectedText) {
        String actualMessage = getSuccessMessage();
        return actualMessage.contains(expectedText);
    }

    /**
     * Wait for order confirmation to load
     */
    public void waitForOrderConfirmation() {
        TestUtils.waitForElementPresent(org.openqa.selenium.By.cssSelector(".order-completed"), 
                com.kiboassessment.utils.ConfigReader.getExplicitWait());
        logger.info("Order confirmation loaded");
    }

    /**
     * Get complete order information
     * @return order information as string
     */
    public String getCompleteOrderInformation() {
        waitForPageLoad();
        logger.info("Inside getCompleteOrderInformation");
        StringBuilder orderInfo = new StringBuilder();

        orderInfo.append("Page Title: ").append(TestUtils.getElementText(pageTitle)).append("\n");
        orderInfo.append("Order Number: ").append(TestUtils.getElementText(orderNumber)).append("\n");
        orderInfo.append("Order Details: ").append(TestUtils.getElementText(orderDetails)).append("\n");
        orderInfo.append("Order Total: ").append(TestUtils.getElementText(orderTotal)).append("\n\n");

        orderInfo.append("Billing Information:\n")
                .append("Name: ").append(TestUtils.getElementText(billingName)).append("\n")
                .append("Email: ").append(TestUtils.getElementText(billingEmail)).append("\n")
                .append("Phone: ").append(TestUtils.getElementText(billingPhone)).append("\n")
                .append("Address: ").append(TestUtils.getElementText(billingAddress)).append("\n")
                .append("City/State/Zip: ").append(TestUtils.getElementText(billingCityStateZip)).append("\n")
                .append("Country: ").append(TestUtils.getElementText(billingCountry)).append("\n\n");

        orderInfo.append("Shipping Information:\n")
                .append("Name: ").append(TestUtils.getElementText(shippingName)).append("\n")
                .append("Email: ").append(TestUtils.getElementText(shippingEmail)).append("\n")
                .append("Phone: ").append(TestUtils.getElementText(shippingPhone)).append("\n")
                .append("Address: ").append(TestUtils.getElementText(shippingAddress)).append("\n")
                .append("City/State/Zip: ").append(TestUtils.getElementText(shippingCityStateZip)).append("\n")
                .append("Country: ").append(TestUtils.getElementText(shippingCountry)).append("\n\n");

        orderInfo.append("Product Information:\n")
                .append("Product Name: ").append(TestUtils.getElementText(productName)).append("\n")
                .append("Price: ").append(TestUtils.getElementText(productPrice)).append("\n")
                .append("Quantity: ").append(TestUtils.getElementText(productQuantity)).append("\n")
                .append("Total: ").append(TestUtils.getElementText(productTotal)).append("\n");

        logger.info("Complete order information retrieved");
        return orderInfo.toString();
    }

} 