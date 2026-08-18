package dev.dumezthomas.kitchencost.models.recipeitem.responses;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.enums.RecipeItemType;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record RecipeItemIndexResponse(

        UUID id,

        RecipeItemType type,
        UUID referenceId,

        String name,

        BigDecimal quantity,
        Unit unit,

        BigDecimal totalCost,
        DietType dietType,
        Set<Allergen> allergens
) {

}
