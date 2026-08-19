package dev.dumezthomas.kitchencost.models.restaurant.responses;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.CuisineType;

import java.util.UUID;

public record PublicRestaurantIndexResponse(

        UUID id,
        String name,
        String description,
        CuisineType cuisineType
) {

    public static PublicRestaurantIndexResponse fromRestaurant(Restaurant r) {

        return new PublicRestaurantIndexResponse(
                
                r.getId(),
                r.getName(),
                r.getDescription(),
                r.getCuisineType()
        );
    }
}
