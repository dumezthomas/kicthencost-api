package dev.dumezthomas.kitchencost.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IngredientCategory {

    MEAT("set_meal", DietType.NONE),
    POULTRY("set_meal", DietType.NONE),
    FISH("set_meal", DietType.NONE),
    SEAFOOD("set_meal", DietType.NONE),

    VEGETABLE("eco", DietType.VEGAN),
    FRUIT("nutrition", DietType.VEGAN),
    HERB("eco", DietType.VEGAN),
    MUSHROOM("eco", DietType.VEGAN),

    DAIRY("breakfast_dining", DietType.VEGETARIAN),
    EGG("egg", DietType.VEGETARIAN),

    GRAIN("grain", DietType.VEGAN),

    LEGUME("eco", DietType.VEGAN),
    NUT("nutrition", DietType.VEGAN),

    OIL("oil_barrel", DietType.VEGAN),
    SPICE("spa", DietType.VEGAN),
    SWEETENER("cake", DietType.VEGAN);

    private final String icon;
    private final DietType dietType;
}
