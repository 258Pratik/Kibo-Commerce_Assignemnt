# E-commerce Test Automation Framework

## Overview

This project implements an end-to-end test automation framework for the demo e-commerce site (https://demowebshop.tricentis.com) using Java, Selenium WebDriver, and TestNG. The framework follows the Page Object Model (POM) design pattern and includes comprehensive reporting capabilities.

## Features

- **Page Object Model (POM)**: Well-structured page objects for maintainable test code
- **WebDriverManager**: Automatic browser driver management
- **ExtentReports**: Comprehensive HTML test reports with screenshots
- **Log4j2**: Detailed logging for debugging and monitoring
- **Configuration Management**: Externalized configuration using properties file
- **Modular Design**: Reusable utility classes and components
- **Cross-browser Support**: Chrome, Firefox, and Edge browser support
- **Screenshot Capture**: Automatic screenshots for failed and successful tests

## Project Structure

```
kiboAssisment/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/kiboassessment/
│   │   │       ├── pages/           # Page Object classes
│   │   │       ├── utils/           # Utility classes
│   │   │       └── listeners/       # TestNG listeners
│   │   └── resources/
│   │       ├── config.properties    # Configuration file
│   │       └── log4j2.xml          # Logging configuration
│   └── test/
│       └── java/
│           └── com/kiboassessment/
│               └── tests/           # Test classes
├── test-output/                     # Test reports and screenshots
├── pom.xml                         # Maven configuration
├── testng.xml                      # TestNG configuration
└── README.md                       # Project documentation
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome, Firefox, or Edge browser installed

## Installation and Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd kiboAssisment
```

### 2. Install Dependencies

```bash
mvn clean install
```

### 3. Configure Test Data

Edit `src/main/resources/config.properties` to modify:
- Application URL
- Login credentials
- Test data
- Browser settings
- Report paths

### 4. Run Tests

#### Run all tests:
```bash
mvn test
```

#### Run specific test class:
```bash
mvn test -Dtest=EndToEndPurchaseTest
```

#### Run with specific browser:
```bash
mvn test -Dbrowser=firefox
```

## Test Scenarios

### 1. End-to-End Purchase Flow Test
- **Objective**: Complete purchase cycle simulation
- **Steps**:
  1. Launch browser and navigate to homepage
  2. Login with provided credentials
  3. Search for a product (e.g., "computer")
  4. Add a product to cart from search results
  5. Navigate to cart and verify product is listed
  6. Proceed to checkout
  7. Fill billing and shipping details
  8. Select shipping and payment methods
  9. Confirm the order
  10. Verify order success message
  11. Logout

### 2. Individual Functionality Tests
- Login functionality verification
- Product search functionality
- Add to cart functionality

## Configuration

### config.properties

```properties
# Application Configuration
base.url=https://demowebshop.tricentis.com
browser=chrome
implicit.wait=10
explicit.wait=20

# Login Credentials
email=qa.user123@mailinator.com
password=Engineer@09876

# Test Data
search.product=computer
product.name=Build your own computer

# Billing Information
billing.firstname=John
billing.lastname=Doe
billing.email=john.doe@example.com
# ... additional billing fields

# Test Reports
extent.report.path=test-output/ExtentReport.html
screenshot.path=test-output/screenshots/
```

## Page Objects

### HomePage
- Login/logout functionality
- Product search
- Navigation elements

### SearchResultsPage
- Search results handling
- Product selection
- Add to cart functionality

### CartPage
- Cart management
- Product verification
- Checkout initiation

### CheckoutPage
- Billing information
- Shipping information
- Payment method selection
- Order confirmation

### OrderConfirmationPage
- Order success verification
- Order details extraction

## Utility Classes

### ConfigReader
- Configuration file management
- Property value retrieval

### WebDriverManagerUtil
- Browser driver initialization
- WebDriver lifecycle management

### TestUtils
- Common test operations
- Element interactions
- Screenshot capture
- Assertion helpers

## Reporting

### ExtentReports
- HTML-based test reports
- Screenshot integration
- Test step logging
- System information

### Log4j2
- Console and file logging
- Different log levels
- Structured logging format

## Test Execution

### Command Line Execution

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=EndToEndPurchaseTest#testEndToEndPurchaseFlow

# Run with specific browser
mvn test -Dbrowser=firefox

# Run with parallel execution
mvn test -Dparallel=methods -DthreadCount=2
```

### IDE Execution
- Import project as Maven project
- Run `testng.xml` file
- Or run individual test classes

## Reports Location

- **ExtentReports**: `test-output/ExtentReport.html`
- **Screenshots**: `test-output/screenshots/`
- **Logs**: `test-output/automation.log`
- **TestNG Reports**: `test-output/index.html`

## Best Practices Implemented

1. **Page Object Model**: Separation of test logic from page interactions
2. **Configuration Management**: Externalized test data and settings
3. **Wait Strategies**: Explicit and implicit waits for element interactions
4. **Error Handling**: Comprehensive exception handling and logging
5. **Reporting**: Detailed test reports with screenshots
6. **Modularity**: Reusable utility classes and components
7. **Logging**: Structured logging for debugging and monitoring

## Troubleshooting

### Common Issues

1. **WebDriver Issues**
   - Ensure browser is installed and up to date
   - Check WebDriverManager configuration

2. **Element Not Found**
   - Verify element locators in page objects
   - Check if page structure has changed

3. **Test Failures**
   - Check application availability
   - Verify test data in config.properties
   - Review logs for detailed error information

### Debug Mode

Enable debug logging by modifying `log4j2.xml`:

```xml
<Root level="debug">
```

## Contributing

1. Follow the existing code structure
2. Add appropriate logging and comments
3. Update documentation for new features
4. Ensure all tests pass before submitting

## License

This project is created for assessment purposes.

## Contact

For questions or issues, please refer to the project documentation or create an issue in the repository.

---

## AI Tools Usage Documentation

### Tools Used
- **Claude Sonnet 4**: Primary code generation and framework design
- **Cursor IDE**: Code editing and project management
- **GitHub Copilot**: Code suggestions and completion

### Example Prompts Used

1. **Framework Design**:
   ```
   "Create a comprehensive Selenium WebDriver test automation framework using Java, TestNG, and Page Object Model for an e-commerce site with the following requirements..."
   ```

2. **Page Object Creation**:
   ```
   "Create a HomePage class with elements and methods for login, search, and navigation functionality using PageFactory annotations"
   ```

3. **Utility Classes**:
   ```
   "Create utility classes for WebDriver management, configuration reading, and common test operations with proper error handling and logging"
   ```

4. **Test Implementation**:
   ```
   "Implement an end-to-end test that follows the complete purchase flow with proper assertions and error handling"
   ```

### Development Process

1. **Initial Setup**: Used AI to generate project structure and Maven configuration
2. **Page Objects**: Generated page classes with proper element locators and methods
3. **Utilities**: Created reusable utility classes for common operations
4. **Tests**: Implemented comprehensive test scenarios with proper assertions
5. **Documentation**: Generated README and inline documentation

### Code Quality Enhancements

- AI-assisted code review and optimization
- Automated generation of comprehensive documentation
- Best practice implementation suggestions
- Error handling and logging improvements 