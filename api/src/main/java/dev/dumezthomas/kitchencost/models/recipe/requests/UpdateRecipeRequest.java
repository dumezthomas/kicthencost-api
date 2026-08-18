package dev.dumezthomas.kitchencost.models.recipe.requests;

import dev.dumezthomas.kitchencost.entities.Recipe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateRecipeRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 2000)
        String instructions,

        @NotNull
        @Positive
        BigDecimal yieldQuantity
) {

    public Recipe toRecipe() {

        return new Recipe(
                name,
                instructions,
                yieldQuantity
        );
    }
}
