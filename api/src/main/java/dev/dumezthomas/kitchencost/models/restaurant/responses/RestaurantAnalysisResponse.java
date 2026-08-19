package dev.dumezthomas.kitchencost.models.restaurant.responses;

import dev.dumezthomas.kitchencost.results.RestaurantAnalysis;

import java.math.BigDecimal;

public record RestaurantAnalysisResponse(

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

    public static RestaurantAnalysisResponse fromAnalysis(RestaurantAnalysis analysis) {

        return new RestaurantAnalysisResponse(
                
                analysis.ingredients(),
                analysis.recipes(),
                analysis.menuItems(),
                analysis.averageFoodCostPercentage(),
                analysis.averageMarkup(),
                analysis.averageMargin(),
                analysis.goodItems(),
                analysis.warningItems(),
                analysis.criticalItems()
        );
    }
}
