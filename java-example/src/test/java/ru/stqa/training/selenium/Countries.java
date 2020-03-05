package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Countries {
    private ChromeDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@name='login']")).click();
    }

    @Test
    public void testCountries() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<CountriesData> actualCountries = listElement(By.cssSelector("tr.row"), 4, By.tagName("a"), false);
        List<CountriesData> expectedCountries = actualCountries;
        equalsSort(expectedCountries, actualCountries);

        List<CountriesData> linksCountries = listElement(By.cssSelector("tr.row"), 4, By.tagName("a"), true);
        for (int i = 0; i < linksCountries.size(); i++) {
            linksCountries.get(i).getLocator().click();
            List<CountriesData> actualZones = listElement(By.cssSelector(".dataTable tr:not(.header)"), 2, null, false);
            actualZones.remove(actualZones.size() - 1);
            List<CountriesData> expectedZones = actualZones;
            equalsSort(expectedZones, actualZones);
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            linksCountries = listElement(By.cssSelector("tr.row"), 4, By.tagName("a"), true);
        }

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> linksZones = driver.findElements(By.cssSelector("tr.row"));
        for (int i = 0; i < linksZones.size(); i++) {
            linksZones.get(i).findElement(By.tagName("a")).click();
            List<CountriesData> actualGeoZones = listElement(By.cssSelector(".dataTable tr:not(.header)"), 2, By.cssSelector("option[selected = selected]"), false);
            List<CountriesData> expectedGeoZones = actualGeoZones;
            equalsSort(expectedGeoZones, actualGeoZones);
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            linksZones = driver.findElements(By.cssSelector("tr.row"));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private List<CountriesData> listElement(By locator, int indexCells,By locatorCells, boolean getLocator) {
        List<CountriesData> countries = new ArrayList<>();
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement element: elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            if (cells.size() > 3) {
                WebElement cell = cells.get(indexCells);
                String name;
                if (locatorCells == null) {
                    name = cell.getText();
                } else name = cell.findElement(locatorCells).getText();
                if (getLocator == true) {
                    String zone = cells.get(5).getText();
                    if (! zone.equals("0")) {
                        CountriesData country = new CountriesData(null, cell.findElement(locatorCells));
                        countries.add(country);
                    }
                } else {
                    CountriesData country = new CountriesData(name, null);
                    countries.add(country);
                }
            }
        }
        return countries;
    }

    private void equalsSort(List<CountriesData> expected, List<CountriesData> actual) {
        Comparator<? super CountriesData> byId = (c1, c2) -> CharSequence.compare(c1.getName(), c2.getName());
        expected.sort(byId);
        Assert.assertEquals(expected, actual);
    }
}
