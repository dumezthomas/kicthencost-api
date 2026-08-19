package dev.dumezthomas.kitchencost.results;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RestaurantAnalysis(

        long ingredients,
        long recipes,
        long menuItems,

        BigDecimal averageFoodCostPercentage,
        BigDecimal averageMarkup,
        BigDecimal averageMargin,

        long goodItems,
        long warningItems,
        long criticalItems
) {

}