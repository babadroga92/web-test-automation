package org.selenium.pom.base;

import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.factory.DriverManager;
import org.selenium.pom.utils.CookieUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.List;


public class BaseTest {

    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driver.get();
    }

    private void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    @Parameters("browser")
    @BeforeMethod
    public void startDriver(String browser){
        browser = System.getProperty("browser", browser);
        setDriver(new DriverManager().initializeDriver(browser));
        System.out.println("CURRENT THREAD:" + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver()) ;
    }

    @AfterMethod
    public void quitDriver(){
        getDriver().quit();
    }

    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = new  CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie : seleniumCookies){
            getDriver().manage().addCookie(cookie);
        }
    }
}
