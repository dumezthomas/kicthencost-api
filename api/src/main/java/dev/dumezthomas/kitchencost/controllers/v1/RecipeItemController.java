package dev.dumezthomas.kitchencost.controllers.v1;

import dev.dumezthomas.kitchencost.models.recipeitem.requests.CreateRecipeItemRequest;
import dev.dumezthomas.kitchencost.models.recipeitem.requests.UpdateRecipeItemRequest;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}")
@RequiredArgsConstructor
public class RecipeItemController {

    // TODO to delete
    private final RestaurantService restaurantService;

    private final RecipeItemService recipeItemService;

    @PostMapping("/ingredients")
    public ResponseEntity<Void> createIngredient(
            @PathVariable UUID recipeId,
            @Valid @RequestBody CreateRecipeItemRequest request
    ) {

        recipeItemService.createIngredient(
                getRestaurantId(),
                recipeId,
                request.referenceId(),
                request.toRecipeItem()
        );

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/recipes/{recipeId}")
                .buildAndExpand(recipeId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/sub-recipes")
    public ResponseEntity<Void> createSubRecipe(
            @PathVariable UUID recipeId,
            @Valid @RequestBody CreateRecipeItemRequest request
    ) {

        recipeItemService.createSubRecipe(
                getRestaurantId(),
                recipeId,
                request.referenceId(),
                request.toRecipeItem()
        );

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/recipes/{recipeId}")
                .buildAndExpand(recipeId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> update(
            @PathVariable UUID recipeId,
            @PathVariable UUID itemId,
            @Valid @RequestBody UpdateRecipeItemRequest request
    ) {

        recipeItemService.update(getRestaurantId(), recipeId, itemId, request.toRecipeItem());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID recipeId,
            @PathVariable UUID itemId
    ) {

        recipeItemService.delete(getRestaurantId(), recipeId, itemId);

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
