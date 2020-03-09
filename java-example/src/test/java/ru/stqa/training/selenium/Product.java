package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

public class Product {
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
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wd.get("http://localhost/litecart/en/");
    }

    @Test
    public void testProduct() {
        ProductData productMainPage = product(By.cssSelector("#box-campaigns .name"), By.cssSelector("#box-campaigns .regular-price"), By.cssSelector("#box-campaigns .campaign-price"));
        Assert.assertEquals("gray", productMainPage.priceColor);
        Assert.assertEquals("red", productMainPage.discountPriceColor);
        Assert.assertTrue(productMainPage.priceFontSize < productMainPage.discountPriceFontSize);

        wd.findElement(By.cssSelector("#box-campaigns .product")).click();

        ProductData productPage = product(By.cssSelector("h1"), By.cssSelector(".information .regular-price"), By.cssSelector(".information .campaign-price"));
        Assert.assertEquals("gray", productPage.priceColor);
        Assert.assertEquals("red", productPage.discountPriceColor);
        Assert.assertTrue(productPage.priceFontSize < productPage.discountPriceFontSize);

        Assert.assertEquals(productMainPage, productPage);
    }

    @After
    public void stop() {
        wd.quit();
        wd = null;
    }

    public  ProductData product(By nameLocator, By priceLocator, By discountPriceLocator) {
        String name = wd.findElement(nameLocator).getText();
        String price = wd.findElement(priceLocator).getText();

        String priceTextDecorationLine;
        if (browser == BrowserType.IE) {
            priceTextDecorationLine = wd.findElement(priceLocator).getCssValue("text-decoration");
        } else priceTextDecorationLine = wd.findElement(priceLocator).getCssValue("text-decoration-line");
        Assert.assertEquals("line-through", priceTextDecorationLine);

        String discountPrice = wd.findElement(discountPriceLocator).getText();

        String discountPriceFontWeight = wd.findElement(discountPriceLocator).getCssValue("font-weight");
        if (discountPriceFontWeight.equals("700") || discountPriceFontWeight.equals("900")) {
            discountPriceFontWeight = "bold";
        } else discountPriceFontWeight = "normal";
        Assert.assertEquals("bold",discountPriceFontWeight);

        String priceColor = getColor(priceLocator);
        String discountPriceColor = getColor(discountPriceLocator);
        float priceFontSize = getFontSize(priceLocator);
        float discountPriceFontSize = getFontSize(discountPriceLocator);

        ProductData product = new ProductData(name, price, priceTextDecorationLine, discountPrice, discountPriceFontWeight, priceColor, discountPriceColor, priceFontSize, discountPriceFontSize);
        return product;
    }

    private float getFontSize(By textLocator) {
        String fontSizeString = wd.findElement(textLocator).getCssValue("font-size");
        fontSizeString = fontSizeString.substring(0, fontSizeString.indexOf("p"));
        return Float.parseFloat(fontSizeString);
    }

    private String getColor(By textLocator) {
        String colorCss = wd.findElement(textLocator).getCssValue("color");
        if (browser == BrowserType.FIREFOX) {
            colorCss = colorCss.substring(4, colorCss.indexOf(")"));
        } else colorCss = colorCss.substring(5, colorCss.lastIndexOf(","));
        String[] colorArray = colorCss.split(", ");
        String color;
        if (colorArray[0].equals(colorArray[1]) && colorArray[0].equals(colorArray[2])){
            color = "gray";
        } else if (colorArray[1].equals("0") && colorArray[2].equals("0")) {
            color = "red";
        } else color = "other";
        return color;
    }
}
