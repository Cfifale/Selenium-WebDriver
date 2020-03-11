package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AddProduct {
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
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testAddProduct() {
        /*Авторизация*/
        wd.get("http://localhost/litecart/admin/");
        sendKeys(By.xpath("//input[@name='username']"), "admin");
        sendKeys(By.xpath("//input[@name='password']"), "admin");
        click(By.xpath("//button[@name='login']"));

        /*Навигация*/
        click(By.linkText("Catalog"));
        click(By.xpath("//a[contains(text(),'Add New Product')]"));

        /*Заполнение вкладки General*/
        click(By.xpath("//label[contains(text(),'Enabled')]//input[@name='status']"));
        int r = 100 + (int) (Math.random() * 1000);
        String name = "Test Duck N"+r;
        sendKeys(By.name("name[en]"),name);
        sendKeys(By.name("code"),"rd999");
        click(By.cssSelector("input[data-name = Root]"));
        click(By.cssSelector("input[data-name = 'Rubber Ducks']"));
        click(By.xpath("//tr//tr//tr[4]//td[1]//input[1]"));
        wd.findElement(By.name("quantity")).clear();
        sendKeys(By.name("quantity"), "30");
        select(By.name("sold_out_status_id"), "Temporary sold out");
        wd.findElement(By.name("new_images[]")).sendKeys(new File("src\\test\\resources\\test-duck.png").getAbsolutePath());
        if (browser == BrowserType.IE) {
            sendKeys(By.name("date_valid_from"), Keys.HOME + "2020-01-01");
            sendKeys(By.name("date_valid_to"),Keys.HOME + "2020-12-31");
        } else {
            sendKeys(By.name("date_valid_from"),"01012020");
            sendKeys(By.name("date_valid_to"),"31122020");
        }

        /*Заполнение вкладки Information*/
        click(By.linkText("Information"));
        select(By.name("manufacturer_id"), "ACME Corp.");
        sendKeys(By.name("keywords"),"Test");
        sendKeys(By.name("short_description[en]"),"Краткое описание добавленного товара.");
        sendKeys(By.name("description[en]"),"Полное описание добавленного товара. Полное описание добавленного товара. Полное описание добавленного товара. Полное описание добавленного товара. Полное описание добавленного товара.");
        sendKeys(By.name("head_title[en]"),"Title");
        sendKeys(By.name("meta_description[en]"),"Meta");

        /*Заполнение вкладки Prices*/
        click(By.linkText("Prices"));
        wd.findElement(By.name("purchase_price")).clear();
        sendKeys(By.name("purchase_price"), "10,50");
        select(By.name("purchase_price_currency_code"), "US Dollars");
        wd.findElement(By.name("prices[USD]")).clear();
        sendKeys(By.name("prices[USD]"), "20");

        /*Сохранение*/
        click(By.name("save"));

        /*проверка добавленного товара*/
        String addProduct = wd.findElement(By.linkText(name)).getText();
        Assert.assertEquals(name, addProduct);
    }

    @After
    public void stop() {
        wd.quit();
        wd = null;
    }

    private void click(By locator) {
        wd.findElement(locator).click();
    }

    private void sendKeys(By locator, String text) {
        wd.findElement(locator).sendKeys(text);
    }

    private void select(By locator, String selectByVisibleText) {
        Select country = new Select(wd.findElement(locator));
        country.selectByVisibleText(selectByVisibleText);
    }
}
