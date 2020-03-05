package ru.stqa.training.selenium;

import org.openqa.selenium.WebElement;

public class CountriesData {
    private String name;
//    private String zone;
    private WebElement locator;

    public CountriesData(String name, WebElement locator) {
        this.name = name;
//        this.zone = zone;
        this.locator = locator;
    }

    public String getName() {
        return name;
    }

    public WebElement getLocator() {
        return locator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountriesData that = (CountriesData) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CountriesData{" +
                "name='" + name + '\'' +
                ", locator=" + locator +
                '}';
    }
}
