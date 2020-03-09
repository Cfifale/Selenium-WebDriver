package ru.stqa.training.selenium;

public class ProductData {
    private String name;
    private String price;
    private String priceTextDecorationLine;
    private String discountPrice;
    private String discountPriceFontWeight;
    String priceColor;
    String discountPriceColor;
    float priceFontSize;
    float discountPriceFontSize;

    public ProductData(String name, String price, String priceTextDecorationLine,String discountPrice, String discountPriceFontWeight, String priceColor, String discountPriceColor, float priceFontSize, float discountPriceFontSize) {
        this.name = name;
        this.price = price;
        this.priceTextDecorationLine = priceTextDecorationLine;
        this.discountPrice = discountPrice;
        this.discountPriceFontWeight = discountPriceFontWeight;
        this.priceColor = priceColor;
        this.discountPriceColor = discountPriceColor;
        this.priceFontSize = priceFontSize;
        this.discountPriceFontSize = discountPriceFontSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductData that = (ProductData) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (priceTextDecorationLine != null ? !priceTextDecorationLine.equals(that.priceTextDecorationLine) : that.priceTextDecorationLine != null)
            return false;
        if (discountPrice != null ? !discountPrice.equals(that.discountPrice) : that.discountPrice != null)
            return false;
        return discountPriceFontWeight != null ? discountPriceFontWeight.equals(that.discountPriceFontWeight) : that.discountPriceFontWeight == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (priceTextDecorationLine != null ? priceTextDecorationLine.hashCode() : 0);
        result = 31 * result + (discountPrice != null ? discountPrice.hashCode() : 0);
        result = 31 * result + (discountPriceFontWeight != null ? discountPriceFontWeight.hashCode() : 0);
        return result;
    }
}
