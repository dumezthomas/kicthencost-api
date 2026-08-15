package dev.dumezthomas.kitchencost.controllers.v1;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.models.ingredient.requests.CreateIngredientRequest;
import dev.dumezthomas.kitchencost.models.ingredient.requests.UpdateIngredientRequest;
import dev.dumezthomas.kitchencost.models.ingredient.responses.IngredientIndexResponse;
import dev.dumezthomas.kitchencost.models.ingredient.responses.IngredientResponse;
import dev.dumezthomas.kitchencost.services.IngredientService;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    // TODO to delete
    private final RestaurantService restaurantService;

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<IngredientIndexResponse>> getAll() {

        List<Ingredient> ingredients = ingredientService.getAll(getRestaurantId());

        List<IngredientIndexResponse> responses = ingredients.stream()
                .map(IngredientIndexResponse::fromIngredient)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity<IngredientResponse> getById(
            @PathVariable UUID ingredientId
    ) {

        Ingredient ingredient = ingredientService.getById(getRestaurantId(), ingredientId);

        IngredientResponse response = IngredientResponse.fromIngredient(ingredient);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateIngredientRequest request
    ) {

        UUID ingredientId = ingredientService.create(getRestaurantId(), request.toIngredient());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{ingredientId}")
                .buildAndExpand(ingredientId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{ingredientId}")
    public ResponseEntity<Void> update(
            @PathVariable UUID ingredientId,
            @Valid @RequestBody UpdateIngredientRequest request
    ) {

        ingredientService.update(getRestaurantId(), ingredientId, request.toIngredient());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ingredientId}/archive")
    public ResponseEntity<Void> archive(
            @PathVariable UUID ingredientId
    ) {

        ingredientService.archive(getRestaurantId(), ingredientId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ingredientId}/restore")
    public ResponseEntity<Void> restore(
            @PathVariable UUID ingredientId
    ) {

        ingredientService.restore(getRestaurantId(), ingredientId);

        return ResponseEntity.noContent().build();
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
