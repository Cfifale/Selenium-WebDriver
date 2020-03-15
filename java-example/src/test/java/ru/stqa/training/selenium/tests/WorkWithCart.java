package ru.stqa.training.selenium.tests;

import org.junit.Test;

public class WorkWithCart extends TestBase {

    @Test
    public void testWorkWithCart() {
        for (int i = 0; i < 3; i++) {
            app.getHomePage().productSelection();
            app.getProductPage().addCartProduct();
        }
        app.getHomePage().goInCart();
        app.getCartPage().clearCartProduct();
    }
}
