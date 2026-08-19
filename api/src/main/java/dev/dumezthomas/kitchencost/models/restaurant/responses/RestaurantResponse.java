package dev.dumezthomas.kitchencost.models.restaurant.responses;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.CuisineType;

import java.math.BigDecimal;
import java.util.UUID;

public record RestaurantResponse(

        UUID id,
        String name,
        String description,
        CuisineType cuisineType,

        BigDecimal targetFoodCostPercentage,
        BigDecimal warningFoodCostPercentage,
        BigDecimal criticalFoodCostPercentage
) {

    public static RestaurantResponse fromRestaurant(Restaurant r) {

        return new RestaurantResponse(
                
                r.getId(),
                r.getName(),
                r.getDescription(),
                r.getCuisineType(),
                r.getTargetFoodCostPercentage(),
                r.getWarningFoodCostPercentage(),
                r.getCriticalFoodCostPercentage()
        );
    }
}
