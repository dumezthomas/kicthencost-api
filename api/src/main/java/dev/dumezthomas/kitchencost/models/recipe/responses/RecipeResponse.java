package dev.dumezthomas.kitchencost.models.recipe.responses;

import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.models.recipeitem.responses.RecipeItemIndexResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RecipeResponse(

        UUID id,
        String name,

        BigDecimal yieldQuantity,
        Unit yieldUnit,

        BigDecimal totalCost,
        // TODO DietType dietType,
        // TODO Set<Allergen> allergens,

        List<RecipeItemIndexResponse> items,

        boolean archived
) {

}
