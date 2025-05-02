package org.selenium.pom.constants;

public enum Route {
    STORE("/store"),
    ACCOUNT("/account"),
    ADD_TO_CART("/?wc-ajax=add_to_cart"),
    CHECKOUT("/checkout");

    public final String url;

    Route(String url) {
        this.url = url;
    }
}
