package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class AccountPage extends BasePage {
    private final By usernameFld =  By.cssSelector("#username");
    private final By passwordFld =  By.cssSelector("#password");
    private final By loginBtn =  By.cssSelector("button[value='Log in']");
    private final By errorMessage = By.xpath("//ul[@class='woocommerce-error']/child::li");
    private final By ordersList = By.cssSelector("a[href='https://askomdch.com/account/orders/']");
    private final By lastOrderNumbber = By.cssSelector("tbody tr:nth-child(1) td:nth-child(1) a:nth-child(1)");
    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage login(String username, String password){
        waitShort.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        waitShort.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        waitShort.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }

    public AccountPage load(){
        load("/account/");
        return this;
    }

    public String getErrorTxt(){
        return driver.findElement(errorMessage).getText();
    }
    public AccountPage getOrderList(){
        waitShort.until(ExpectedConditions.elementToBeClickable(ordersList)).click();
        return this;
    }

    public String getOrderNo(){
        return waitShort.until(ExpectedConditions.visibilityOfElementLocated(lastOrderNumbber)).getText();
    }

}
