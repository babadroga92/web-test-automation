package org.selenium.pom.tests;
import io.restassured.http.Cookies;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignupApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.AccountPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pojo.Product;
import org.selenium.pom.pojo.User;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginTest extends BaseTest {
    @Test
    public void loginDuringCheckout() throws IOException {
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
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load().loginLink()
                        .login(user);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }

    @Test
    public void shouldNotLoginWithAnInvalidPassword(){
        User user = User.builder()
                .username(ConfigLoader.getInstance().getUsername() + new FakerUtils().generateRandomNumber())
                .password(ConfigLoader.getInstance().getPassword())
                .email(ConfigLoader.getInstance().getUsername() + new FakerUtils().generateRandomNumber() + "@gmail.com")
                .build();
        new SignupApi().register(user);
        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(user.getUsername(), "invalid_password");
        Assert.assertEquals(accountPage.getErrorTxt(), "Error: The password you entered for the username "
                + user.getUsername() + " is incorrect. Lost your password?");
    }
    @Test
    public void shouldNotLoginWithANonExistingUser(){
        User user = User.builder()
                .username("lorenita89")
                .password("changito")
                .email("chica" + new FakerUtils().generateRandomNumber() + "@gmail.com")
                .build();
        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(user.getUsername(), user.getPassword());
        Assert.assertEquals(accountPage.getErrorTxt(), "Error: The username " + user.getUsername() +
                " is not registered on this site." +
                " If you are unsure of your username, try your email address instead.");
    }
}
