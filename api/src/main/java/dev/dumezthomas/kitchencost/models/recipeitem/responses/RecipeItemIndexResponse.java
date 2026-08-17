package dev.dumezthomas.kitchencost.models.recipeitem.responses;

import dev.dumezthomas.kitchencost.enums.RecipeItemType;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeItemIndexResponse(

        UUID id,

        RecipeItemType type,
        UUID referenceId,

        String name,

        BigDecimal quantity,
        Unit unit,

        BigDecimal totalCost
        // TODO DietType dietType,
        // TODO Set<Allergen> allergens,
) {

}
