package dev.dumezthomas.kitchencost.models.recipe.responses;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.models.recipeitem.responses.RecipeItemIndexResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record RecipeResponse(

        UUID id,
        String name,
        String instructions,

        BigDecimal yieldQuantity,
        Unit yieldUnit,

        BigDecimal totalCost,
        DietType dietType,
        Set<Allergen> allergens,

        List<RecipeItemIndexResponse> items,

        boolean archived
) {

}
