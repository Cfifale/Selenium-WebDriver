package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class ProductPage extends PageBase {

    public ProductPage(WebDriver wd) {
        super(wd);
    }

    public void addCartProduct() {
        WebDriverWait wait = new WebDriverWait(wd, 10);
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
}
