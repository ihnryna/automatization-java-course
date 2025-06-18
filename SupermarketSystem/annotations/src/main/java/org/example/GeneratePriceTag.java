package org.example;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface GeneratePriceTag {
    String[] fields();
    PriceUnit unit();
}