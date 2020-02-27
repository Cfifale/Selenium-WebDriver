package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class LiteCart {
    private ChromeDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void admin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@name='login']")).click();
        ArrayList<String> menu = listElement(By.cssSelector("li#app-"), By.className("name"));
        for (String s : menu) {
            driver.findElement(By.linkText(s)).click();
            assertTrue(areElementsPresent(By.cssSelector("h1")));
            ArrayList<String> podmenu = listElement(By.cssSelector("ul.docs > li"), By.className("name"));
            for (String s2 : podmenu) {
                driver.findElement(By.linkText(s2)).click();
                assertTrue(areElementsPresent(By.cssSelector("h1")));
            }
        }
    }

    @Test
    public void testProductSticker() {
        driver.get("http://localhost/litecart/en/");
        stickerCheck(By.id("box-most-popular"));
        stickerCheck(By.id("box-campaigns"));
        stickerCheck(By.id("box-latest-products"));
    }

    private void  stickerCheck (By productCategory) {
        WebElement category = driver.findElement(productCategory);
        List<WebElement> products = category.findElements(By.cssSelector("li"));
        for (WebElement element: products) {
            assertTrue(element.findElements(By.cssSelector("div.image-wrapper > div")).size() == 1);
        }
    }

    private ArrayList<String> listElement(By forma, By elementForms) {
        List<WebElement> elements = driver.findElements(forma);
        ArrayList<String> list = new ArrayList<>();
        for (WebElement element: elements) {
            list.add(element.findElement(elementForms).getText());
        }
        return list;
    }

    private boolean areElementsPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
