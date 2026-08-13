package dev.dumezthomas.kitchencost.controllers.v1;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.models.recipe.requests.CreateRecipeRequest;
import dev.dumezthomas.kitchencost.models.recipe.requests.UpdateRecipeRequest;
import dev.dumezthomas.kitchencost.models.recipe.responses.RecipeIndexResponse;
import dev.dumezthomas.kitchencost.models.recipe.responses.RecipeResponse;
import dev.dumezthomas.kitchencost.services.RecipeService;
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
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    // TODO to delete
    private final RestaurantService restaurantService;

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeIndexResponse>> getAll() {

        List<Recipe> recipes = recipeService.getAll(getRestaurantId());

        List<RecipeIndexResponse> responses = recipes.stream()
                .map(RecipeIndexResponse::fromRecipe)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getById(
            @PathVariable("id") UUID recipeId
    ) {

        Recipe recipe = recipeService.getById(getRestaurantId(), recipeId);

        RecipeResponse response = RecipeResponse.fromRecipe(recipe);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateRecipeRequest request
    ) {

        UUID id = recipeService.create(getRestaurantId(), request.toRecipe());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") UUID recipeId,
            @Valid @RequestBody UpdateRecipeRequest request
    ) {

        recipeService.update(getRestaurantId(), recipeId, request.toRecipe());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archive(
            @PathVariable("id") UUID recipeId
    ) {

        recipeService.archive(getRestaurantId(), recipeId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restore(
            @PathVariable("id") UUID recipeId
    ) {

        recipeService.restore(getRestaurantId(), recipeId);

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
