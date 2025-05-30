package org.selenium.pom.tests;
import io.qameta.allure.Description;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataprovider.MyDataProvider;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.pojo.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class AddToCartTest extends BaseTest {

    @Test(description = "Verify that a product can be added to the cart directly from the store page and is displayed correctly in the cart.")
    @Description("This test verifies that when a product is added to the cart directly from the store page, it is correctly displayed in the cart with the correct product name.")
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver()).load()
                .getProductThumbnail()
                .clickAddToCartButton(product.getName())
                .clickViewCartButton();
        Assert.assertEquals(cartPage.getProductText(), product.getName());
    }

    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class)
    public void addToCartFeaturedProducts(Product product) {
        CartPage cartPage = new HomePage(getDriver()).load()
                .getProductThumbnail()
                .clickAddToCartButton(product.getName()).clickViewCartButton();
        Assert.assertEquals(cartPage.getProductText(), product.getName());
    }
}
