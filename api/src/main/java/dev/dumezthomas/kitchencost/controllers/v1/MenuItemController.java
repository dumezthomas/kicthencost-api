package dev.dumezthomas.kitchencost.controllers.v1;

import dev.dumezthomas.kitchencost.assemblers.MenuItemAssembler;
import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.models.menuitem.requests.CreateMenuItemRequest;
import dev.dumezthomas.kitchencost.models.menuitem.requests.UpdateMenuItemRequest;
import dev.dumezthomas.kitchencost.models.menuitem.responses.MenuItemIndexResponse;
import dev.dumezthomas.kitchencost.models.menuitem.responses.MenuItemResponse;
import dev.dumezthomas.kitchencost.services.MenuItemAnalysisService;
import dev.dumezthomas.kitchencost.services.MenuItemService;
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
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    // TODO to delete
    private final RestaurantService restaurantService;

    private final MenuItemService menuItemService;
    private final MenuItemAnalysisService menuItemAnalysisService;

    private final MenuItemAssembler menuItemAssembler;

    @GetMapping
    public ResponseEntity<List<MenuItemIndexResponse>> getAll() {

        List<MenuItem> menuItems = menuItemService.getAll(getRestaurantId());

        List<MenuItemIndexResponse> responses = menuItemAssembler.toIndexResponses(menuItems);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> getById(
            @PathVariable UUID menuItemId
    ) {

        MenuItem menuItem = menuItemService.getById(getRestaurantId(), menuItemId);

        MenuItemResponse response = menuItemAssembler.toResponse(menuItem);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/best-food-cost")
    public ResponseEntity<List<MenuItemIndexResponse>> getBestFoodCostItems() {

        List<MenuItem> menuItems = menuItemAnalysisService.getBestFoodCostItems(getRestaurantId());

        List<MenuItemIndexResponse> responses = menuItemAssembler.toIndexResponses(menuItems);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/worst-food-cost")
    public ResponseEntity<List<MenuItemIndexResponse>> getWorstFoodCostItems() {

        List<MenuItem> menuItems = menuItemAnalysisService.getWorstFoodCostItems(getRestaurantId());

        List<MenuItemIndexResponse> responses = menuItemAssembler.toIndexResponses(menuItems);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateMenuItemRequest request
    ) {

        UUID menuItemId = menuItemService.create(getRestaurantId(), request.toMenuItem());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{menuItemId}")
                .buildAndExpand(menuItemId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<Void> update(
            @PathVariable UUID menuItemId,
            @Valid @RequestBody UpdateMenuItemRequest request
    ) {

        menuItemService.update(getRestaurantId(), menuItemId, request.toMenuItem());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{menuItemId}/archive")
    public ResponseEntity<Void> archive(
            @PathVariable UUID menuItemId
    ) {

        menuItemService.archive(getRestaurantId(), menuItemId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{menuItemId}/restore")
    public ResponseEntity<Void> restore(
            @PathVariable UUID menuItemId
    ) {

        menuItemService.restore(getRestaurantId(), menuItemId);

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
