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

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getById(
            @PathVariable UUID id
    ) {

        Ingredient ingredient = ingredientService.getById(getRestaurantId(), id);

        IngredientResponse response = IngredientResponse.fromIngredient(ingredient);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateIngredientRequest request
    ) {

        UUID id = ingredientService.create(getRestaurantId(), request.toIngredient());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateIngredientRequest request
    ) {

        ingredientService.update(getRestaurantId(), id, request.toIngredient());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archive(
            @PathVariable UUID id
    ) {

        ingredientService.archive(getRestaurantId(), id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restore(
            @PathVariable UUID id
    ) {

        ingredientService.restore(getRestaurantId(), id);

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
