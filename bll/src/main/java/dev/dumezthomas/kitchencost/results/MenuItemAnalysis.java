package dev.dumezthomas.kitchencost.results;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.enums.FoodCostStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record MenuItemAnalysis(

        BigDecimal totalCost,
        BigDecimal foodCostPercentage,
        FoodCostStatus foodCostStatus,
        BigDecimal margin,
        BigDecimal markup,
        BigDecimal suggestedPrice,

        Set<Allergen> allergens,
        DietType dietType
) {

}