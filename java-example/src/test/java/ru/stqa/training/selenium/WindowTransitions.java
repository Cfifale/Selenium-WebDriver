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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class WindowTransitions {
    private WebDriver wd;
    public String browser = BrowserType.CHROME;
    public WebDriverWait wait;

    @Before
    public void start() {
        if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }
        wait = new WebDriverWait(wd, 10);
//        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testWindowTransitions() {
        wd.get("http://localhost/litecart/admin/");
        sendKeys(By.xpath("//input[@name='username']"), "admin");
        sendKeys(By.xpath("//input[@name='password']"), "admin");
        click(By.xpath("//button[@name='login']"));

        click(By.linkText("Countries"));
        click(By.cssSelector(".fa-pencil"));

        List<WebElement> elements = wd.findElements(By.cssSelector(".fa-external-link"));
        String mainWindow = wd.getWindowHandle();
        for (WebElement element : elements) {
            Set<String> oldWindows = wd.getWindowHandles();
            element.click();
            String newWindow = wait.until(anyWindowOtherThan(oldWindows));
            wd.switchTo().window(newWindow);
            wd.close();
            wd.switchTo().window(mainWindow);
        }
    }

    @After
    public void stop() {
        wd.quit();
        wd = null;
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    private void click(By locator) {
        wd.findElement(locator).click();
    }

    private void sendKeys(By locator, String text) {
        wd.findElement(locator).sendKeys(text);
    }
}
