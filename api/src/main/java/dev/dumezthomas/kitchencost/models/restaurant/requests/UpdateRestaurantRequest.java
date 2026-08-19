package dev.dumezthomas.kitchencost.models.restaurant.requests;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.CuisineType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateRestaurantRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 250)
        String description,

        @NotNull
        CuisineType cuisineType,

        @NotNull
        @Positive
        @DecimalMax("100")
        BigDecimal targetFoodCostPercentage,

        @NotNull
        @Positive
        @DecimalMax("100")
        BigDecimal warningFoodCostPercentage,

        @NotNull
        @Positive
        @DecimalMax("100")
        BigDecimal criticalFoodCostPercentage
) {

    public Restaurant toRestaurant() {

        return new Restaurant(

                name,
                description,
                cuisineType,
                targetFoodCostPercentage,
                warningFoodCostPercentage,
                criticalFoodCostPercentage
        );
    }

}
