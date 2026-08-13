package dev.dumezthomas.kitchencost.models.ingredient.requests;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.enums.Allergen;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public record UpdateIngredientRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        Set<Allergen> allergens,

        @NotNull
        @PositiveOrZero
        BigDecimal currentPriceByDefaultUnit
) {

    public Ingredient toIngredient() {

        return new Ingredient(
                name,
                allergens,
                currentPriceByDefaultUnit
        );
    }
}