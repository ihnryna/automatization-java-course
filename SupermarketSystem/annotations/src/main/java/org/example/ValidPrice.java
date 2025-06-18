package org.example;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPrice {
    double min() default 0;
    double max() default Double.MAX_VALUE;
}

