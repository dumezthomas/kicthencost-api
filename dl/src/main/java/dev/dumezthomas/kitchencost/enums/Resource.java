package dev.dumezthomas.kitchencost.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Resource {

    RESTAURANT("Restaurant"),
    USER("User"),
    INGREDIENT("Ingredient"),
    RECIPE("Recipe"),
    MENU_ITEM("Menu item"),
    INVOICE("Invoice");

    private final String displayName;

    @Override
    public String toString() {

        return displayName;
    }
}
