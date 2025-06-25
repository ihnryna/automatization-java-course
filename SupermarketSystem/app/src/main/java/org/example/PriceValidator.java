package org.example;

import java.lang.reflect.Field;


public class PriceValidator {

    public static void validate(Object obj) {
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Price.class)) {
                Price annotation = field.getAnnotation(Price.class);
                double min = annotation.min();
                double max = annotation.max();

                try {
                    Object value = field.get(obj);
                    if (value instanceof Number number) {
                        if (number.doubleValue() < min ||number.doubleValue() > max) {
                            throw new IllegalArgumentException("Price " + number.doubleValue() + " is not in the appropriate range.");
                        }
                        if (number.doubleValue() != Math.round(number.doubleValue() * 100.0) / 100.0) {
                            throw new IllegalArgumentException("Price " + number.doubleValue() + " is not valid");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
