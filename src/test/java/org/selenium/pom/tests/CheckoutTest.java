package org.selenium.pom.tests;
import io.qameta.allure.Description;
import io.restassured.http.Cookies;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignupApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pojo.BillingAddress;
import org.selenium.pom.pojo.Product;
import org.selenium.pom.pojo.User;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;

@Epic("Checkout Process")
public class CheckoutTest extends BaseTest {

    @Test(description = "Verify that a guest user can complete checkout using Direct Bank Transfer as the payment method.")
    @Feature("Guest Checkout - Payment Methods")
    @Story("Complete checkout using Direct Bank Transfer")
    @Description("This test verifies that a guest user can complete the checkout process by selecting Direct Bank Transfer as the payment method and successfully places an order.")
    public void guestCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress guestUsers = JacksonUtils.deserializedJson("guestUsers.json", BillingAddress.class);
        CartApi cartApi = new CartApi(new Cookies());
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load()
                .setBillingAddress(guestUsers)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
    @Test(description = "Verify that a guest user can complete checkout using Cash on Delivery as the payment method.")
    @Feature("Guest Checkout - Payment Methods")
    @Story("Complete checkout using Cash on Delivery")
    @Description("This test verifies that a guest user can complete the checkout process by selecting Cash on Delivery as the payment method and successfully places an order.")
    public void guestCheckoutUsingCashOnDelivery() throws IOException, InterruptedException {
        BillingAddress guestUsers = JacksonUtils.deserializedJson("guestUsers.json", BillingAddress.class);
        CartApi cartApi = new CartApi(new Cookies());
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load()
                .setBillingAddress(guestUsers)
                .selectCashOnDelivery()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Test(description = "Verify that a logged-in user can complete checkout using Direct Bank Transfer after adding a product to the cart.")
    @Feature("Logged-in User Checkout - Payment Methods")
    @Story("Complete checkout using Direct Bank Transfer")
    @Description("This test ensures that a logged-in user can complete the checkout process using Direct Bank Transfer as the payment method after adding a product to their cart.")
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
        injectCookiesToBrowser(signupApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load()
                .setBillingAddress(existingUser)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
    @Test(description = "Verify that a logged-in user can complete checkout using Cash on Delivery after adding a product to the cart.")
    @Feature("Logged-in User Checkout - Payment Methods")
    @Story("Complete checkout using Cash on Delivery")
    @Description("This test ensures that a logged-in user can complete the checkout process using Cash on Delivery as the payment method after adding a product to their cart.")
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
        injectCookiesToBrowser(signupApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load()
                .setBillingAddress(existingUser)
                .selectCashOnDelivery()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
}
