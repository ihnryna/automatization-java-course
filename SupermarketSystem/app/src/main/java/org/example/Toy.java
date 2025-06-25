package org.example;

@GeneratePriceTag(fields = {"name", "manufacturer", "price"}, unit = PriceUnit.PER_ONE)
public class Toy {
    public String name;

    @Price
    public double price;

    public String manufacturer;

    public Toy(String name, double price, String manufacturer) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
    }
}