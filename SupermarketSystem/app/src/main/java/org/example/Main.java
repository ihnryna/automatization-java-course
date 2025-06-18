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
            ToyPriceTag dollTag = new ToyPriceTag(doll);
            System.out.println(dollTag.format());
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            Toy doll2 = new Toy("Barbie Doll", 250.000516, "Mattel");
            ToyPriceTag dollTag2 = new ToyPriceTag(doll2);
            System.out.println(dollTag2.format());
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
