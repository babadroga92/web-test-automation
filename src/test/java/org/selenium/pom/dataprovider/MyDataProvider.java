package org.selenium.pom.dataprovider;

import org.selenium.pom.pojo.Product;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class MyDataProvider {
    @DataProvider(name = "getFeaturedProducts", parallel = false)
    public Product[] getFeaturedProducts() throws IOException {
        return JacksonUtils.deserializedJson("featuredProducts.json", Product[].class);
    }
}
