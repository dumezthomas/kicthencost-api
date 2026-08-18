package dev.dumezthomas.kitchencost.results;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record RecipeItemAnalysis(

        BigDecimal totalCost,
        Set<Allergen> allergens,
        DietType dietType
) {

}