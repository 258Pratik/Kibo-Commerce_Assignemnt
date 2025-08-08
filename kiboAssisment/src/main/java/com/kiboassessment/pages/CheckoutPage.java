package com.kiboassessment.pages;

import com.kiboassessment.utils.ConfigReader;
import com.kiboassessment.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * CheckoutPage class representing the checkout process
 */
public class CheckoutPage extends BasePage {

    // Billing address elements
    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement billingFirstName;

    @FindBy(id = "checkout")
    private WebElement checkoutBillingPage;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement billingLastName;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement billingEmail;

    @FindBy(id = "BillingNewAddress_Company")
    private WebElement billingCompany;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement billingCountry;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement billingState;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement billingCity;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement billingAddress1;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement billingZipCode;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement billingPhone;

    // Shipping address elements
    @FindBy(id = "ShippingNewAddress_FirstName")
    private WebElement shippingFirstName;

    @FindBy(id = "ShippingNewAddress_LastName")
    private WebElement shippingLastName;

    @FindBy(id = "ShippingNewAddress_Email")
    private WebElement shippingEmail;

    @FindBy(id = "ShippingNewAddress_Company")
    private WebElement shippingCompany;

    @FindBy(id = "ShippingNewAddress_CountryId")
    private WebElement shippingCountry;

    @FindBy(id = "ShippingNewAddress_StateProvinceId")
    private WebElement shippingState;

    @FindBy(id = "ShippingNewAddress_City")
    private WebElement shippingCity;

    @FindBy(id = "ShippingNewAddress_Address1")
    private WebElement shippingAddress1;

    @FindBy(id = "ShippingNewAddress_ZipPostalCode")
    private WebElement shippingZipCode;

    @FindBy(id = "ShippingNewAddress_PhoneNumber")
    private WebElement shippingPhone;

    @FindBy(id = "billing-address-select")
    private WebElement billingAddressSelect;

    // Checkout buttons
    @FindBy(css = "input[onclick='Billing.save()']")
    private WebElement billingContinueButton;

    @FindBy(css = "input[onclick='Shipping.save()']")
    private WebElement shippingContinueButton;

    @FindBy(css = "input[onclick='ShippingMethod.save()']")
    private WebElement shippingMethodContinueButton;

    @FindBy(css = "input[onclick='PaymentMethod.save()']")
    private WebElement paymentMethodContinueButton;

    @FindBy(css = "input[onclick='PaymentInfo.save()']")
    private WebElement paymentInfoContinueButton;

    @FindBy(css = "input[onclick='ConfirmOrder.save()']")
    private WebElement confirmOrderButton;

    // Shipping method
    @FindBy(id = "shippingoption_0")
    private WebElement groundShippingOption;

    @FindBy(id = "shippingoption_1")
    private WebElement nextDayAirShippingOption;

    @FindBy(id = "shippingoption_2")
    private WebElement secondDayAirShippingOption;

    // Payment method
    @FindBy(id = "paymentmethod_0")
    private WebElement checkMoneyOrderOption;

    @FindBy(id = "paymentmethod_1")
    private WebElement creditCardOption;

    // Order summary
    @FindBy(css = ".order-summary-content")
    private WebElement orderSummary;

    // Terms of service
    @FindBy(id = "termsofservice")
    private WebElement termsOfServiceCheckbox;

    /**
     * Select billing address dropdown
     * @param address billing address
     */
    public void selectBillingAddressdropdown(String address) {
        Select addressSelect = new Select(billingAddressSelect);
        addressSelect.selectByVisibleText(address);
        logger.info("Selected billing address: " + address);
    }

    /**
     * Fill billing address information
     */
    public void fillBillingAddress() {
        TestUtils.safeSendKeys(billingFirstName, ConfigReader.getProperty("billing.firstname"));
        TestUtils.safeSendKeys(billingLastName, ConfigReader.getProperty("billing.lastname"));
        TestUtils.safeSendKeys(billingEmail, ConfigReader.getProperty("billing.email"));
        TestUtils.safeSendKeys(billingCompany, ConfigReader.getProperty("billing.company"));
        
        // Select country
        Select countrySelect = new Select(billingCountry);
        countrySelect.selectByVisibleText(ConfigReader.getProperty("billing.country"));
        
        // Select state
        Select stateSelect = new Select(billingState);
        stateSelect.selectByVisibleText(ConfigReader.getProperty("billing.state"));
        
        TestUtils.safeSendKeys(billingCity, ConfigReader.getProperty("billing.city"));
        TestUtils.safeSendKeys(billingAddress1, ConfigReader.getProperty("billing.address1"));
        TestUtils.safeSendKeys(billingZipCode, ConfigReader.getProperty("billing.zipcode"));
        TestUtils.safeSendKeys(billingPhone, ConfigReader.getProperty("billing.phone"));
        
        logger.info("Filled billing address information");
    }

    /**
     * Fill shipping address information
     */
    public void fillShippingAddress() {
        TestUtils.safeSendKeys(shippingFirstName, ConfigReader.getProperty("shipping.firstname"));
        TestUtils.safeSendKeys(shippingLastName, ConfigReader.getProperty("shipping.lastname"));
        TestUtils.safeSendKeys(shippingEmail, ConfigReader.getProperty("shipping.email"));
        TestUtils.safeSendKeys(shippingCompany, ConfigReader.getProperty("shipping.company"));
        
        // Select country
        Select countrySelect = new Select(shippingCountry);
        countrySelect.selectByVisibleText(ConfigReader.getProperty("shipping.country"));
        
        // Select state
        Select stateSelect = new Select(shippingState);
        stateSelect.selectByVisibleText(ConfigReader.getProperty("shipping.state"));
        
        TestUtils.safeSendKeys(shippingCity, ConfigReader.getProperty("shipping.city"));
        TestUtils.safeSendKeys(shippingAddress1, ConfigReader.getProperty("shipping.address1"));
        TestUtils.safeSendKeys(shippingZipCode, ConfigReader.getProperty("shipping.zipcode"));
        TestUtils.safeSendKeys(shippingPhone, ConfigReader.getProperty("shipping.phone"));
        
        logger.info("Filled shipping address information");
    }

    /**
     * Click billing continue button
     */
    public void clickBillingContinue() {
        TestUtils.safeClick(billingContinueButton);
        logger.info("Clicked billing continue button");
    }

    /**
     * Click shipping continue button
     */
    public void clickShippingContinue() {
        TestUtils.safeClick(shippingContinueButton);
        logger.info("Clicked shipping continue button");
    }

    /**
     * Select shipping method
     * @param method shipping method (ground, nextday, secondday)
     */
    public void selectShippingMethod(String method) {
        switch (method.toLowerCase()) {
            case "ground":
                TestUtils.safeClick(groundShippingOption);
                break;
            case "nextday":
                TestUtils.safeClick(nextDayAirShippingOption);
                break;
            case "secondday":
                TestUtils.safeClick(secondDayAirShippingOption);
                break;
            default:
                TestUtils.safeClick(groundShippingOption);
                break;
        }
        logger.info("Selected shipping method: " + method);
    }

    /**
     * Click shipping method continue button
     */
    public void clickShippingMethodContinue() {
        TestUtils.safeClick(shippingMethodContinueButton);
        logger.info("Clicked shipping method continue button");
    }

    /**
     * Click shipping method continue button
     */
    public void clickAgreeTermsOfService() {
        TestUtils.safeClick(termsOfServiceCheckbox);
        logger.info("Clicked agree terms of service checkbox");
    }

    /**
     * Click shipping method continue button
     */
    public void clickCheckoutBillingPage() {
        TestUtils.safeClick(checkoutBillingPage);
        logger.info("Clicked checkout billing page");
    }

    /**
     * Select payment method
     * @param method payment method (check, creditcard)
     */
    public void selectPaymentMethod(String method) {
        switch (method.toLowerCase()) {
            case "check":
                TestUtils.safeClick(checkMoneyOrderOption);
                break;
            case "creditcard":
                TestUtils.safeClick(creditCardOption);
                break;
            default:
                TestUtils.safeClick(checkMoneyOrderOption);
                break;
        }
        logger.info("Selected payment method: " + method);
    }

    /**
     * Click payment method continue button
     */
    public void clickPaymentMethodContinue() {
        TestUtils.safeClick(paymentMethodContinueButton);
        logger.info("Clicked payment method continue button");
    }

    /**
     * Click payment info continue button
     */
    public void clickPaymentInfoContinue() {
        TestUtils.safeClick(paymentInfoContinueButton);
        logger.info("Clicked payment info continue button");
    }

    /**
     * Accept terms of service
     */
    public void acceptTermsOfService() {
        TestUtils.safeClick(termsOfServiceCheckbox);
        logger.info("Accepted terms of service");
    }

    /**
     * Click confirm order button
     */
    public void clickConfirmOrder() {
        TestUtils.safeClick(confirmOrderButton);
        logger.info("Clicked confirm order button");
    }

    /**
     * Get order summary text
     * @return order summary text
     */
    public String getOrderSummaryText() {
        return TestUtils.getElementText(orderSummary);
    }

    /**
     * Complete checkout process
     */
    public void completeCheckout() {
        selectBillingAddressdropdown("New Address");
        fillBillingAddress();
        clickBillingContinue();
        
        clickShippingContinue();
        
        selectShippingMethod("ground");
        clickShippingMethodContinue();
        
        selectPaymentMethod("check");
        clickPaymentMethodContinue();
        
        clickPaymentInfoContinue();
        
        clickConfirmOrder();
        
        logger.info("Completed checkout process");
    }
} 