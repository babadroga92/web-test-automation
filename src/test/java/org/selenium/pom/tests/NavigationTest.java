package org.selenium.pom.tests;
import io.qameta.allure.Description;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.pojo.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class NavigationTest extends BaseTest {

    @Test(description = "Verify that users can navigate from the Home page to the Store page using the main menu.")
    @Description("This test verifies that when users interact with the main menu on the Home page, they are able to navigate successfully to the Store page.")
    public void NavigateFromHomeToStoreUsingMainMenu() {
        StorePage storePage = new HomePage(getDriver()).load()
                .getMyHeader().navigateToStoreUsingMenu();
        Assert.assertEquals(storePage.getTitle(), "Store");
    }

    @Test(description = "Verify that users can navigate from the Store page to a specific product page.")
    @Description("This test simulates navigation from the Store page to a specific product page, ensuring that the product displayed matches the one selected.")
    public void NavigateFromStoreToTheProduct() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage = new StorePage(getDriver())
                .load()
                .navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle(), product.getName());
    }

    @Test(description = "Verify that users can access the featured product directly from the Home page.")
    @Description("This test ensures that the featured product on the Home page is accessible directly, and the product page displayed matches the expected product.")
    public void NavigateFromHomeToTheFeaturedProduct() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage = new HomePage(getDriver()).load()
                .getProductThumbnail()
                .navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle(), product.getName());
    }
}
