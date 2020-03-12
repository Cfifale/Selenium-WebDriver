package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class WorkWithCart {
    private WebDriver wd;
    public String browser = BrowserType.CHROME;

    @Before
    public void start() {
        if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }
//        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.get("http://localhost/litecart/en/");
    }

    @Test
    public void testWorkWithCart() {
        addCartProduct();
        addCartProduct();
        addCartProduct();
        clearCartProduct();
    }

    @After
    public void stop() {
        wd.quit();
        wd = null;
    }

    public void addCartProduct() {
        WebDriverWait wait = new WebDriverWait(wd, 10);
        click(By.cssSelector(".product"));
        if (wd.findElements(By.cssSelector(".options")).size() == 1) {
            select(By.name("options[Size]"), "Small");
        }
        WebElement element = wd.findElement(By.cssSelector("span.quantity"));
        int quantityInt = Integer.parseInt(element.getText());
        click(By.name("add_cart_product"));
        quantityInt = quantityInt + 1;
        String quantity = String.valueOf(quantityInt);
        wait.until(textToBePresentInElement(element,quantity));
        click(By.cssSelector("img[title = 'My Store']"));
    }

    private void clearCartProduct() {
        click(By.xpath("//a[contains(text(),'Checkout »')]"));
        WebDriverWait wait = new WebDriverWait(wd, 10);
        wait.until(presenceOfElementLocated(By.cssSelector(".dataTable")));
        List<WebElement> elements = wd.findElements(By.cssSelector(".dataTable tr"));
        for (int i = 1; elements.size() > 0; i++) {
            List<WebElement> shortcut = wd.findElements(By.cssSelector(".shortcut"));
            if (shortcut.size() > 0) {
                shortcut.get(0).findElement(By.tagName("a")).click();
            }
            click(By.name("remove_cart_item"));
            wait.until(stalenessOf(elements.get(1)));
            elements = wd.findElements(By.cssSelector(".dataTable tr"));
        }
        click(By.xpath("//a[contains(text(),'<< Back')]"));
    }

    private void click(By locator) {
        wd.findElement(locator).click();
    }

    private void select(By locator, String selectByVisibleText) {
        Select country = new Select(wd.findElement(locator));
        country.selectByVisibleText(selectByVisibleText);
    }
}
