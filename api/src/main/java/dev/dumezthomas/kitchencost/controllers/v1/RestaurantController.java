package dev.dumezthomas.kitchencost.controllers.v1;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.models.restaurant.responses.RestaurantIndexResponse;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantIndexResponse>> findAll() {

        List<Restaurant> restaurants = restaurantService.getAll();

        List<RestaurantIndexResponse> responses = restaurants.stream()
                .map(RestaurantIndexResponse::fromRestaurant)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
