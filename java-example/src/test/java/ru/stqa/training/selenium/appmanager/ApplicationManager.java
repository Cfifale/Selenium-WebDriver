package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

public class ApplicationManager {
    public WebDriver wd;
    public String browser;

    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() {
        if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }
        cartPage = new CartPage(wd);
        homePage = new HomePage(wd);
        productPage = new ProductPage(wd);
        wd.get("http://localhost/litecart/en/");
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public void stop() {
        wd.quit();
        wd = null;
    }
}
