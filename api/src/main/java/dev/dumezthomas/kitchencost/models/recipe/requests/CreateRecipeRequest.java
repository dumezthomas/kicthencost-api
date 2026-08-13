package dev.dumezthomas.kitchencost.models.recipe.requests;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.enums.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateRecipeRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        @Positive
        BigDecimal yieldQuantity,

        @NotNull
        Unit yieldUnit
) {

    public Recipe toRecipe() {

        return new Recipe(
                name,
                yieldQuantity,
                yieldUnit
        );
    }
}
