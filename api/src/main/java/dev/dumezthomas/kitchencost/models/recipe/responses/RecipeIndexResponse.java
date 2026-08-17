package dev.dumezthomas.kitchencost.models.recipe.responses;

import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeIndexResponse(

        UUID id,
        String name,

        BigDecimal yieldQuantity,
        Unit yieldUnit,

        BigDecimal totalCost,
        // TODO DietType dietType,
        // TODO Set<Allergen> allergens,

        boolean archived
) {

}
