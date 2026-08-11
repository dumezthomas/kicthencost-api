package dev.dumezthomas.kitchencost.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IngredientCategory {

    MEAT("set_meal", false, false),
    POULTRY("set_meal", false, false),
    FISH("set_meal", false, false),
    SEAFOOD("set_meal", false, false),

    VEGETABLE("eco", true, true),
    FRUIT("nutrition", true, true),
    HERB("eco", true, true),
    MUSHROOM("eco", true, true),

    DAIRY("breakfast_dining", true, false),
    EGG("egg", true, false),

    GRAIN("grain", true, true),
    BAKERY("bakery_dining", true, true),

    LEGUME("eco", true, true),
    NUT("nutrition", true, true),

    OIL("oil_barrel", true, true),
    SPICE("spa", true, true);

    private final String icon;
    private final boolean vegetarian;
    private final boolean vegan;
}
