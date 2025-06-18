    package org.example;

    @GeneratePriceTag(fields = {"name", "price"}, unit = PriceUnit.PER_1KG)
    public class Fruit {
        public String name;

        @ValidPrice
        public double price;

        public String manufacturer;

        public Fruit(String name, double price, String manufacturer) {
            this.name = name;
            this.price = price;
            this.manufacturer = manufacturer;
        }
    }