package dev.dumezthomas.kitchencost.models.recipe.responses;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeResponse(

        UUID id,
        String name,
        // TODO DietType dietType,
        // TODO Set<Allergen> allergens,
        // TODO BigDecimal totalCost,
        // TODO BigDecimal costPerYieldUnit,
        BigDecimal yieldQuantity,
        Unit yieldUnit,
        boolean archived
        // TODO List<RecipeIngredientResponse> ingredients
) {

    public static RecipeResponse fromRecipe(Recipe r) {

        return new RecipeResponse(
                r.getId(),
                r.getName(),
                r.getYieldQuantity(),
                r.getYieldUnit(),
                r.isArchived()
        );
    }
}
