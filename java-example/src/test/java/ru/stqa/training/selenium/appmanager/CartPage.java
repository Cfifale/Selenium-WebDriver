package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CartPage extends PageBase {

    public CartPage(WebDriver wd) {
        super(wd);
    }

    public void clearCartProduct() {
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
}
