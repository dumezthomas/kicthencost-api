package dev.dumezthomas.kitchencost.enums;

public enum DietType {

    NONE,
    VEGETARIAN,
    VEGAN;

    public static DietType merge(DietType a, DietType b) {

        return a.ordinal() < b.ordinal() ? a : b;
    }
}