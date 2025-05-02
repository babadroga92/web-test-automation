package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.ProductThumbnail;
import org.selenium.pom.pojo.Product;

import java.io.IOException;

public class StorePage extends BasePage {

    private final By searchField = By.id("woocommerce-product-search-field-0");
    private final By searchButton = By.cssSelector("#woocommerce_product_search-1 > form > button");
    private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By infoTxt = By.cssSelector(".woocommerce-info.woocommerce-no-products-found");

    private ProductThumbnail productThumbnail;

    public StorePage(WebDriver driver) {
        super(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public StorePage enterTextInSearchFld(String txt){
        waitShort.until(ExpectedConditions.visibilityOfElementLocated(searchField)).sendKeys(txt);
        return this;
    }

    public StorePage load(){
        load("/store");
        return this;
    }

    public StorePage clickSearchButton(){
        waitShort.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        return this;
    }
    public StorePage search(String txt){
        enterTextInSearchFld(txt);
        clickSearchButton();
        return this;
    }

    public String getTitle() {
        return waitShort.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public String getInfo(){
        return waitShort.until(ExpectedConditions.visibilityOfElementLocated(infoTxt)).getText();
    }

    public ProductPage searchExactMatch(String txt){
        search(txt);
        return new ProductPage(driver);
    }

    public ProductPage navigateToTheProduct(int id) throws IOException {
        waitShort.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[normalize-space()='"+ new Product(id).getName() + "']"))).click();
        return new ProductPage(driver);
    }
}
