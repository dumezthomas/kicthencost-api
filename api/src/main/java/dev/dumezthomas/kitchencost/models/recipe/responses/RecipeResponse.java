package dev.dumezthomas.kitchencost.models.recipe.responses;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.models.recipeitem.responses.RecipeItemIndexResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RecipeResponse(

        UUID id,
        String name,

        // TODO DietType dietType,
        // TODO Set<Allergen> allergens,

        // TODO BigDecimal totalCost,

        BigDecimal yieldQuantity,
        Unit yieldUnit,

        List<RecipeItemIndexResponse> items,

        boolean archived
) {

    public static RecipeResponse fromRecipe(Recipe r, List<RecipeItem> items) {

        return new RecipeResponse(

                r.getId(),
                r.getName(),

                r.getYieldQuantity(),
                r.getYieldUnit(),

                items.stream()
                        .map(RecipeItemIndexResponse::fromRecipeItem)
                        .toList(),

                r.isArchived()
        );
    }
}
