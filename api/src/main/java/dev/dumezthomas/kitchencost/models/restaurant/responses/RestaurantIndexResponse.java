package dev.dumezthomas.kitchencost.models.restaurant.responses;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.CuisineType;

import java.util.UUID;

public record RestaurantIndexResponse(

        UUID id,
        String name,
        String description,
        CuisineType cuisineType
) {

    public static RestaurantIndexResponse fromRestaurant(Restaurant r) {

        return new RestaurantIndexResponse(
                r.getId(),
                r.getName(),
                r.getDescription(),
                r.getCuisineType()
        );
    }
}
