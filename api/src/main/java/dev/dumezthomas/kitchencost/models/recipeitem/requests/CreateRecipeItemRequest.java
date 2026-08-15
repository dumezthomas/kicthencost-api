package dev.dumezthomas.kitchencost.models.recipeitem.requests;

import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Unit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateRecipeItemRequest(

        @NotNull
        UUID referenceId,

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
