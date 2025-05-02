package org.selenium.pom.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pojo.BillingAddress;
import org.selenium.pom.pojo.User;

import java.time.Duration;

public class CheckoutPage extends BasePage {
    private final By fNameField = By.cssSelector("#billing_first_name");
    private final By lNameField = By.cssSelector("#billing_last_name");
    private final By streetAddressField = By.cssSelector("#billing_address_1");
    private final By cityField = By.cssSelector("#billing_city");
    private final By zipCodeField = By.cssSelector("#billing_postcode");
    private final By emailField = By.cssSelector("#billing_email");
    private final By loginLink = By.cssSelector(".showlogin");
    private final By usernameBox = By.cssSelector("#username");
    private final By passwordBox = By.cssSelector("#password");
    private final By loginButton = By.cssSelector("button[value='Login']");
    private final By placeOrderButton = By.cssSelector("#place_order");
    private final By confirmationText = By.cssSelector(".woocommerce-notice.woocommerce-notice--success.woocommerce-thankyou-order-received");
    private final By countryDropDown = By.id("select2-billing_country-container");
    private final By directBankTransferRadioBtn = By.id("payment_method_bacs");
    private final By productName = By.cssSelector("td[class='product-name']");
    private final By cashOnDeliveryRadioBtn = By.id("payment_method_cod");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage load(){
        load("/checkout/");
        return this;
    }

    public CheckoutPage enterFirstName(String firstName){
        return clearAndType(fNameField, firstName);
    }
    public CheckoutPage enterLastName(String lastName){
        return clearAndType(lNameField, lastName);
    }

    public CheckoutPage enterCountry(String countryName) {
        waitLong.until(ExpectedConditions.elementToBeClickable(countryDropDown)).click();
        WebElement searchInput = waitShort.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@role='combobox']")));
        searchInput.clear();
        searchInput.sendKeys(countryName);
        waitShort.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[text()='" + countryName + "']")));
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
        waitShort.until(ExpectedConditions.textToBePresentInElementLocated(countryDropDown, countryName));
        actions.sendKeys(Keys.TAB).perform();
        return this;
    }


    public CheckoutPage enterAddressLineOne(String addressLineOne){
        return clearAndType(streetAddressField, addressLineOne);
    }
    public CheckoutPage enterCity(String city){
        return clearAndType(cityField, city);
    }
    public CheckoutPage enterPostalCode(String postalCode){
        return clearAndType(zipCodeField, postalCode);
    }
    public CheckoutPage enterEmail(String email){
        return clearAndType(emailField, email);
    }

    private CheckoutPage clearAndType(By locator, String text) {
        WebElement e = waitForElementToBeVisible(locator);
        e.clear();
        waitLong.until(driver -> e.getText().isEmpty());
        e.sendKeys(text);
        return this;
    }

    public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
        enterFirstName(billingAddress.getFirstName())
                .enterPostalCode(billingAddress.getPostalCode())
                .enterLastName(billingAddress.getLastName())
                .enterCountry(billingAddress.getCountry())
                .enterAddressLineOne(billingAddress.getAddressLineOne())
                .enterCity(billingAddress.getCity())
                .enterEmail(billingAddress.getEmail());
        waitLong.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#payment.blockUI")));
        return this;
    }

    public CheckoutPage placeOrder() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForPageToBeReady();

                WebElement placeOrder = waitLong.until(ExpectedConditions.presenceOfElementLocated(placeOrderButton));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrder);
                waitShort.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrder);

                System.out.println("Clicked place order on attempt: " + (attempts + 1));
                return this;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                System.out.println("Retrying clicking Place Order button. Attempt: " + (attempts + 1));
                attempts++;
            } catch (Exception e) {
                System.out.println("Unexpected failure: " + e.getMessage());
                attempts++;
            }
        }
        throw new RuntimeException("Failed to click Place Order button after 3 attempts.");
    }
    private void waitForPageToBeReady() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }


    public String getNotice(){
        return waitForElementToBeVisible(confirmationText).getText();
    }
    public CheckoutPage loginLink(){
        waitLong.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return this;
    }

    private void inputUsername(String username){
        waitLong.until(ExpectedConditions.elementToBeClickable(usernameBox)).sendKeys(username);
    }
    private void inputPassword(String password){
        waitLong.until(ExpectedConditions.elementToBeClickable(passwordBox)).sendKeys(password);
    }
    private void loginButton(){
        waitLong.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
    public CheckoutPage login(User user){
        inputUsername(user.getUsername());
        inputPassword(user.getPassword());
        loginButton();
        return this;
    }

    public CheckoutPage selectDirectBankTransfer(){
        WebElement e = waitShort.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioBtn));
        if(!e.isSelected()){
            e.click();
        }
        return this;
    }
    public CheckoutPage selectCashOnDelivery() throws InterruptedException {
        waitLong.until(driver -> {
            try {
                WebElement e = driver.findElement(cashOnDeliveryRadioBtn);
                return e.isDisplayed() && e.isEnabled();
            } catch (StaleElementReferenceException ex) {
                return false;
            }
        });

        /*
            TO BE RESOLVED. Currently only THread.sleep solves the problem with page changing the class
        */
        Thread.sleep(2000);

        WebElement e = waitLong.until(ExpectedConditions.elementToBeClickable(cashOnDeliveryRadioBtn));
        if (!e.isSelected()) {
            e.click();
        }
        return this;
    }

    public String getProductName(){
       return waitShort.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }
}
