package org.selenium.pom.tests;
import io.qameta.allure.Description;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.pojo.Product;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class SearchTest extends BaseTest {

    @Test(description = "Verify that the search results show relevant products when searching with a partial match.")
    @Description("This test simulates a search for products using a partial match of the word 'Blue' and verifies that the search results display products that match this term.")
    public void searchWithPartialMatch() {
        StorePage storePage = new StorePage(getDriver()).load()
                .search("Blue");
        Assert.assertEquals(storePage.getTitle(), "Search results: “Blue”");
    }

    @Test(description = "Verify that the search results show the exact product when searching with an exact match.")
    @Description("This test simulates a search using the exact product name and verifies that the exact product is displayed in the search results.")
    public void searchWithExactMatch() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage = new StorePage(getDriver())
                .load()
                .searchExactMatch(product.getName());
        Assert.assertEquals(productPage.getProductTitle(), product.getName());
    }

    @Test(description = "Verify that the system displays a 'no products found' message when searching for a non-existing product.")
    @Description("This test simulates a search for a non-existing product using a random name and verifies that the system displays a 'No products were found matching your selection.' message.")
    public void SearchForNonExistingProduct() {
        StorePage storePage = new StorePage(getDriver())
                .load()
                .search(new FakerUtils().generateRandomName());
        Assert.assertEquals(storePage.getInfo(), "No products were found matching your selection.");
    }
}
