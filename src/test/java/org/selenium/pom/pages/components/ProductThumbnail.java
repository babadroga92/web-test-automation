package org.selenium.pom.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pojo.Product;

import java.io.IOException;

public class ProductThumbnail extends BasePage {

    private final By viewCartBtn = By.cssSelector("a[title='View cart']");

    public ProductThumbnail(WebDriver driver) {
        super(driver);
    }


    private By getAddToCartButtonElement(String productName){
        return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
    }

    public ProductThumbnail clickAddToCartButton(String productName){
        By addToCartButton = getAddToCartButtonElement(productName);
        waitShort.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        return this;
    }
    public CartPage clickViewCartButton(){
        waitShort.until(ExpectedConditions.elementToBeClickable(viewCartBtn)).click();
        return new CartPage(driver);
    }

    public ProductPage navigateToTheProduct(int id) throws IOException {
        driver.findElement(By.xpath("//h2[normalize-space()='"+ new Product(id).getName()+ "']")).click();
        return new ProductPage(driver);
    }
}