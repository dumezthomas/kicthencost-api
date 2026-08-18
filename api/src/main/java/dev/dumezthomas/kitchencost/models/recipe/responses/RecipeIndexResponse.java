package dev.dumezthomas.kitchencost.models.recipe.responses;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record RecipeIndexResponse(

        UUID id,
        String name,

        BigDecimal yieldQuantity,
        Unit yieldUnit,

        BigDecimal totalCost,
        DietType dietType,
        Set<Allergen> allergens,

        boolean archived
) {

}
