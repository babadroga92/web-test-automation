package org.selenium.pom.tests;
import io.restassured.http.Cookies;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignupApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pojo.BillingAddress;
import org.selenium.pom.pojo.Product;
import org.selenium.pom.pojo.User;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest {

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress guestUsers = JacksonUtils.deserializedJson("guestUsers.json", BillingAddress.class);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartApi cartApi = new CartApi(new Cookies());
        cartApi.addToCart(1215, 1);

        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load()
                .setBillingAddress(guestUsers)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
    @Test
    public void guestCheckoutUsingCashOnDelivery() throws IOException, InterruptedException {
        BillingAddress guestUsers = JacksonUtils.deserializedJson("guestUsers.json", BillingAddress.class);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartApi cartApi = new CartApi(new Cookies());
        cartApi.addToCart(1215, 1);

        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load()
                .setBillingAddress(guestUsers)
                .selectCashOnDelivery()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        BillingAddress existingUser = JacksonUtils.deserializedJson("existingUser.json", BillingAddress.class);
        User user = User.builder()
                .username("lorena" + new FakerUtils().generateRandomNumber())
                .email("lestage" + new FakerUtils().generateRandomNumber() + "@gmail.com")
                .password("secure123")
                .build();
        SignupApi signupApi = new SignupApi();
        signupApi.register(user);
        CartApi cartApi = new CartApi(signupApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signupApi.getCookies());
        checkoutPage.load()
                .setBillingAddress(existingUser)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
    @Test
    public void loginAndCheckoutUsingCashOnDelivery() throws IOException, InterruptedException {
        BillingAddress existingUser = JacksonUtils.deserializedJson("existingUser.json", BillingAddress.class);
        User user = User.builder()
                .username("lorena" + new FakerUtils().generateRandomNumber())
                .email("lestage" + new FakerUtils().generateRandomNumber() + "@gmail.com")
                .password("secure123")
                .build();
        SignupApi signupApi = new SignupApi();
        signupApi.register(user);

        CartApi cartApi = new CartApi(signupApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signupApi.getCookies());
        checkoutPage.load()
                .setBillingAddress(existingUser)
                .selectCashOnDelivery()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
}
