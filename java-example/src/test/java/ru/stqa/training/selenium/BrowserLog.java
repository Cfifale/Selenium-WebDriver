package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.BrowserType;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserLog {
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
    public void testBrowserLog() {
        wd.get("http://localhost/litecart/admin/");
        sendKeys(By.xpath("//input[@name='username']"), "admin");
        sendKeys(By.xpath("//input[@name='password']"), "admin");
        click(By.xpath("//button[@name='login']"));

        click(By.linkText("Catalog"));
        click(By.linkText("Rubber Ducks"));

        List<WebElement> elements = wd.findElements(By.cssSelector("tr.row td:nth-child(3) a[href *= product]"));
        for (int i=0; i < elements.size(); i++) {
            elements.get(i).click();
            List<LogEntry> log = wd.manage().logs().get("browser").getAll();
            Assert.assertTrue(noLog(log));
            click(By.xpath("//button[@name='cancel']"));
            elements = wd.findElements(By.cssSelector("tr.row td:nth-child(3) a[href *= product]"));
        }
    }

    @After
    public void stop() {
        wd.quit();
        wd = null;
    }

    private boolean noLog(List<LogEntry> log) {
        return log.size() == 0;
    }

    private void click(By locator) {
        wd.findElement(locator).click();
    }

    private void sendKeys(By locator, String text) {
        wd.findElement(locator).sendKeys(text);
    }
}
