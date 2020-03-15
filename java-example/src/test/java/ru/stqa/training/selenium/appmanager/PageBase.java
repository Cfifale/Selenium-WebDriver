package ru.stqa.training.selenium.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class PageBase {
    public WebDriver wd;

    public PageBase(WebDriver wd) {
        this.wd = wd;
    }

    public void click(By locator) {
        wd.findElement(locator).click();
    }

    public void select(By locator, String selectByVisibleText) {
        Select country = new Select(wd.findElement(locator));
        country.selectByVisibleText(selectByVisibleText);
    }
}
