package org.selenium.pom.tests;
import io.restassured.http.Cookies;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignupApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.AccountPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pojo.BillingAddress;
import org.selenium.pom.pojo.Product;
import org.selenium.pom.pojo.User;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AccountOrdersTest extends BaseTest {

    @Test
    public void shouldBeAbleToSeeExistingOrder() throws IOException, InterruptedException {
        BillingAddress guestUsers = JacksonUtils.deserializedJson("guestUsers.json", BillingAddress.class);
        User user = User.builder()
                .username("lorena" + new FakerUtils().generateRandomNumber())
                .email("lorena" + new FakerUtils().generateRandomNumber() + "@gmail.com")
                .password("secure123")
                .build();
        SignupApi signupApi = new SignupApi();
        signupApi.register(user);
        CartApi cartApi = new CartApi(new Cookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 3);
        injectCookiesToBrowser(cartApi.getCookies());
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load()
                .loginLink()
                .login(user)
                .setBillingAddress(guestUsers)
                .placeOrder();
        String orderNumber = checkoutPage.getOrderNumber();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
        AccountPage accountPage = new AccountPage(getDriver()).load()
                .getOrderList();
        Assert.assertEquals(orderNumber, accountPage.getOrderNo().trim().replace("#", ""));
    }
}
