package dev.dumezthomas.kitchencost.controllers.v1;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.models.restaurant.requests.UpdateRestaurantRequest;
import dev.dumezthomas.kitchencost.models.restaurant.responses.RestaurantAnalysisResponse;
import dev.dumezthomas.kitchencost.models.restaurant.responses.RestaurantResponse;
import dev.dumezthomas.kitchencost.results.RestaurantAnalysis;
import dev.dumezthomas.kitchencost.services.RestaurantAnalysisService;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantAnalysisService restaurantAnalysisService;

    @GetMapping
    public ResponseEntity<RestaurantResponse> get() {

        Restaurant restaurant = restaurantService.getById(getRestaurantId());

        RestaurantResponse response = RestaurantResponse.fromRestaurant(restaurant);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Void> update(
            @Valid @RequestBody UpdateRestaurantRequest request
    ) {

        restaurantService.update(getRestaurantId(), request.toRestaurant());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/analysis")
    public ResponseEntity<RestaurantAnalysisResponse> getAnalysis() {

        RestaurantAnalysis analysis = restaurantAnalysisService.analyze(getRestaurantId());

        RestaurantAnalysisResponse response = RestaurantAnalysisResponse.fromAnalysis(analysis);

        return ResponseEntity.ok(response);
    }

    // TODO to delete
    private UUID getRestaurantId() {

        return restaurantService.getAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No restaurant found.")
                )
                .getId();
    }
}
