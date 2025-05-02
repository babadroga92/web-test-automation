package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.constants.Route;
import org.selenium.pom.pojo.User;
import org.selenium.pom.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SignupApi {

    private Cookies cookies;

    public Cookies getCookies(){
        return cookies;
    }

    private String fetchRegisterNonceValue(){
        Response response = getAccount();
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element element = document.selectFirst("#woocommerce-register-nonce");
        return element.attr("value");
    }

    public Response getAccount(){
        Cookies cookies = new Cookies();
        Response response =
                given()
                        .baseUri(ConfigLoader.getInstance().getBaseUrl())
                    .cookies(cookies)
                    .log().all()
                .when()
                    .get("/account")
                .then()
                    .log().all()
                    .extract()
                    .response();
        if (response.getStatusCode()!= 200){
            throw new RuntimeException(("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode()));
        }
        return response;
    }
    public Response register(User user){
        Cookies cookies = new Cookies();
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("username", user.getUsername());
        formParams.put("password", user.getPassword());
        formParams.put("email", user.getEmail());
        formParams.put("woocommerce-register-nonce", fetchRegisterNonceValue());
        formParams.put("register", "Register");
        Response response = APIRequest.post(Route.ACCOUNT.url, headers, formParams, cookies);
        if (response.getStatusCode()!= 302){
            throw new RuntimeException(("Failed to register the account, HTTP Status Code: " + response.getStatusCode()));
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }
}
