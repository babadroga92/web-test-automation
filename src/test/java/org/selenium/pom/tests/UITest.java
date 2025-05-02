package org.selenium.pom.tests;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.pojo.BillingAddress;
import org.selenium.pom.pojo.Product;
import org.selenium.pom.pojo.User;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;



public class UITest extends BaseTest {

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        BillingAddress guestUsers = JacksonUtils.deserializedJson("guestUsers.json", BillingAddress.class);
        Product product = new Product(1215);
        HomePage homePage = new HomePage(getDriver()).load();
        StorePage storePage = homePage.getMyHeader().navigateToStoreUsingMenu();
        storePage
                .search("Blue")
                .getProductThumbnail()
                .clickAddToCartButton(product.getName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCartButton();
        Assert.assertEquals(cartPage.getProductText(), product.getName());
        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage
                .setBillingAddress(guestUsers)
                .selectDirectBankTransfer()
                .placeOrder();
            Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        BillingAddress existingUser = JacksonUtils.deserializedJson("existingUser.json", BillingAddress.class);
        Product product = new Product(1215);
        User user = User.builder()
                .username(ConfigLoader.getInstance().getUsername())
                .password(ConfigLoader.getInstance().getPassword())
                .build();

        HomePage homePage = new HomePage(getDriver()).load();
        StorePage storePage = homePage.getMyHeader().navigateToStoreUsingMenu();
        storePage
                .enterTextInSearchFld("Blue")
                .clickSearchButton()
                .getProductThumbnail()
                .clickAddToCartButton(product.getName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCartButton();
        Assert.assertEquals(cartPage.getProductText(), product.getName());
        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage
                .loginLink()
                .login(user)
                .setBillingAddress(existingUser)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
}
