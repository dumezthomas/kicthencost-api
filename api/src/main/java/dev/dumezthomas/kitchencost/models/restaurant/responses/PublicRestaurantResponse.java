package dev.dumezthomas.kitchencost.models.restaurant.responses;

import dev.dumezthomas.kitchencost.enums.CuisineType;
import dev.dumezthomas.kitchencost.models.menuitem.responses.PublicMenuItemResponse;

import java.util.List;
import java.util.UUID;

public record PublicRestaurantResponse(

        UUID id,
        String name,
        String description,
        CuisineType cuisineType,

        List<PublicMenuItemResponse> menu
) {

}
