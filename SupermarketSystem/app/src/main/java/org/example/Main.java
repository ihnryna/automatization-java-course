package org.example;

public class Main {
    public static void main(String[] args) {
        Fruit apple = new Fruit("Apple", 63, "Apples Company");
        FruitPriceTag appleTag = new FruitPriceTag(apple);
        System.out.println(appleTag.format());

        Toy teddyBear = new Toy("Teddy Bear", 350, "Toy Story");
        ToyPriceTag teddyBearTag = new ToyPriceTag(teddyBear);
        System.out.println(teddyBearTag.format());

        try{
            Toy doll = new Toy("Barbie Doll", -250, "Mattel");
            PriceValidator.validate(doll);
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            Toy doll2 = new Toy("Barbie Doll", 250.000516, "Mattel");
            PriceValidator.validate(doll2);
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
