package dev.dumezthomas.kitchencost.models.recipeitem.requests;

import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Unit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateRecipeItemRequest(

        @NotNull
        @Positive
        BigDecimal quantity,

        @NotNull
        Unit unit
) {

    public RecipeItem toRecipeItem() {

        return new RecipeItem(
                quantity,
                unit
        );
    }
}
