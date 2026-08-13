package dev.dumezthomas.kitchencost.models.ingredient.responses;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.IngredientCategory;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record IngredientResponse(

        UUID id,
        String name,
        IngredientCategory ingredientCategory,
        Set<Allergen> allergens,
        Unit defaultUnit,
        BigDecimal currentPriceByDefaultUnit,
        boolean archived
) {

    public static IngredientResponse fromIngredient(Ingredient i) {

        return new IngredientResponse(
                i.getId(),
                i.getName(),
                i.getIngredientCategory(),
                i.getAllergens(),
                i.getDefaultUnit(),
                i.getCurrentPriceByDefaultUnit(),
                i.isArchived()
        );
    }
}