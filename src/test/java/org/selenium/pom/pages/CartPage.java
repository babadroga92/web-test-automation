package org.selenium.pom.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class CartPage extends BasePage {

    private final By proceedToCheckoutButton = By.cssSelector("#post-1220 > div > div > div > div > div.cart-collaterals > div > div > a");
    private final By productText = By.cssSelector("td[class='product-name'] a");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage checkout(){
        waitShort.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
        return new CheckoutPage(driver);
    }
    public String getProductText(){
        return waitShort.until(ExpectedConditions.visibilityOfElementLocated(productText)).getText();
    }
}
