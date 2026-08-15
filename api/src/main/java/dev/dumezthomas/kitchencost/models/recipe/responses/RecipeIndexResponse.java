package dev.dumezthomas.kitchencost.models.recipe.responses;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeIndexResponse(

        UUID id,
        String name,

        // TODO DietType dietType,
        // TODO Set<Allergen> allergens,
        // TODO BigDecimal totalCost,

        BigDecimal yieldQuantity,
        Unit yieldUnit,

        boolean archived
) {

    public static RecipeIndexResponse fromRecipe(Recipe r) {

        return new RecipeIndexResponse(

                r.getId(),
                r.getName(),

                r.getYieldQuantity(),
                r.getYieldUnit(),
                
                r.isArchived()
        );
    }
}
