package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends PageBase{

    public HomePage(WebDriver wd) {
        super(wd);
    }

    public void productSelection() {
        click(By.cssSelector(".product"));
    }

    public void goInCart() {
        click(By.xpath("//a[contains(text(),'Checkout »')]"));
    }

}
