package dev.dumezthomas.kitchencost.assemblers;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.models.menuitem.responses.PublicMenuItemResponse;
import dev.dumezthomas.kitchencost.models.restaurant.responses.PublicRestaurantResponse;
import dev.dumezthomas.kitchencost.services.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantAssembler {

    private final MenuItemService menuItemService;

    private final MenuItemAssembler menuItemAssembler;

    public PublicRestaurantResponse toPublicResponse(Restaurant restaurant) {

        List<MenuItem> menuItems = menuItemService.getAllActive(restaurant.getId());

        List<PublicMenuItemResponse> menu = menuItemAssembler.toPublicResponses(menuItems);

        return new PublicRestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getCuisineType(),
                menu
        );
    }
}