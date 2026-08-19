package dev.dumezthomas.kitchencost.controllers.v1;

import dev.dumezthomas.kitchencost.assemblers.RestaurantAssembler;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.models.restaurant.responses.PublicRestaurantIndexResponse;
import dev.dumezthomas.kitchencost.models.restaurant.responses.PublicRestaurantResponse;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class PublicRestaurantController {

    private final RestaurantService restaurantService;

    private final RestaurantAssembler restaurantAssembler;

    @GetMapping
    public ResponseEntity<List<PublicRestaurantIndexResponse>> getAll() {

        List<Restaurant> restaurants = restaurantService.getAll();

        List<PublicRestaurantIndexResponse> responses = restaurants.stream()
                .map(PublicRestaurantIndexResponse::fromRestaurant)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<PublicRestaurantResponse> getById(
            @PathVariable UUID restaurantId
    ) {

        Restaurant restaurant = restaurantService.getById(restaurantId);

        PublicRestaurantResponse response = restaurantAssembler.toPublicResponse(restaurant);

        return ResponseEntity.ok(response);
    }

}
