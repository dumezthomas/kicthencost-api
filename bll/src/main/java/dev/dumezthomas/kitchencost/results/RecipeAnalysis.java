package dev.dumezthomas.kitchencost.results;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Builder
public record RecipeAnalysis(

        BigDecimal totalCost,
        Set<Allergen> allergens,
        DietType dietType,

        Map<UUID, RecipeItemAnalysis> itemAnalyses
) {

}