package org.selenium.pom.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.utils.ConfigLoader;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait waitLong;
    protected WebDriverWait waitShort;

    public BasePage(WebDriver driver){
        this.driver = driver;
        waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        waitShort = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    public void load(String endPoint){
        driver.get(ConfigLoader.getInstance().getBaseUrl()+ endPoint);
    }

    public void waitForOverlaysToDisappear(By overlay){
        List<WebElement> overlays = driver.findElements(overlay);
        if(!overlays.isEmpty()){
            waitLong.until(
                    ExpectedConditions.invisibilityOfAllElements(overlays)
            );
        }else{
            System.out.println("OVERLAY NOT FOUND");
        }
    }

    public WebElement waitForElementToBeVisible(By element){
        return waitShort.until(ExpectedConditions.visibilityOfElementLocated(element));
    }
}
