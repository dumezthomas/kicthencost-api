package dev.dumezthomas.kitchencost.models.ingredient.responses;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.enums.IngredientCategory;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record IngredientIndexResponse(

        UUID id,
        String name,
        IngredientCategory ingredientCategory,
        Unit defaultUnit,
        BigDecimal currentPriceByDefaultUnit,
        boolean archived

) {

    public static IngredientIndexResponse fromIngredient(Ingredient i) {

        return new IngredientIndexResponse(
                i.getId(),
                i.getName(),
                i.getIngredientCategory(),
                i.getDefaultUnit(),
                i.getCurrentPriceByDefaultUnit(),
                i.isArchived()
        );
    }
}