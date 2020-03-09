package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class UserRegistration {
    private WebDriver wd;
    public String browser = BrowserType.FIREFOX;

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
    public void testProduct() {
        click(By.linkText("New customers click here"));
        WebDriverWait wait = new WebDriverWait(wd, 20);
        wait.until(presenceOfElementLocated(By.name("firstname")));
        senKey(By.name("firstname"), "Ivan");
        senKey(By.name("lastname"), "Ivanov");
        senKey(By.name("address1"), "Address");
        senKey(By.name("postcode"), "12345");
        senKey(By.name("city"), "Moscow");
//        Select country = new Select(wd.findElement(By.cssSelector(".select2-hidden-accessible")));
//        country.selectByVisibleText("United States");
        click(By.cssSelector(".select2-selection__arrow"));
        senKey(By.className("select2-search__field"),"United States" + Keys.ENTER);
        int r1 = 1000 + (int) (Math.random() * 10000);
        int r2 = 10000 + (int) (Math.random() * 20000);
        String email = r1+"@"+r2+".ru";
        senKey(By.name("email"), email);
        senKey(By.name("phone"), "+11231231212");
        senKey(By.name("password"), "password");
        senKey(By.name("confirmed_password"), "password");
        click(By.name("create_account"));
        click(By.linkText("Logout"));
        wait.until(presenceOfElementLocated(By.name("email")));
        senKey(By.name("email"), email);
        senKey(By.name("password"), "password");
        click(By.name("login"));
        click(By.linkText("Logout"));
    }

    @After
    public void stop() {
        wd.quit();
        wd = null;
    }

    private void click(By locator) {
        wd.findElement(locator).click();
    }

    private void senKey(By locator, String text) {
        click(locator);
        wd.findElement(locator).sendKeys(text);
    }
}
