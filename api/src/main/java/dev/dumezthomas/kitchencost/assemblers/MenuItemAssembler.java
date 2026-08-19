package dev.dumezthomas.kitchencost.assemblers;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.models.menuitem.responses.MenuItemIndexResponse;
import dev.dumezthomas.kitchencost.models.menuitem.responses.MenuItemResponse;
import dev.dumezthomas.kitchencost.models.menuitem.responses.PublicMenuItemResponse;
import dev.dumezthomas.kitchencost.results.MenuItemAnalysis;
import dev.dumezthomas.kitchencost.services.MenuItemAnalysisService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemAssembler {

    private final MenuItemAnalysisService menuItemAnalysisService;

    private final RecipeItemService recipeItemService;

    public MenuItemResponse toResponse(MenuItem menuItem) {

        MenuItemAnalysis analysis = menuItemAnalysisService.analyze(menuItem);

        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getType(),
                menuItem.getRecipe().getId(),
                menuItem.getRecipe().getName(),
                menuItem.getPrice(),
                analysis.totalCost(),
                analysis.margin(),
                analysis.markup(),
                analysis.foodCostPercentage(),
                analysis.foodCostStatus(),
                analysis.suggestedPrice(),
                analysis.dietType(),
                analysis.allergens(),
                menuItem.isArchived()
        );
    }

    public List<MenuItemIndexResponse> toIndexResponses(List<MenuItem> menuItems) {

        return menuItems.stream()
                .map(this::toIndexResponse)
                .toList();
    }

    public List<PublicMenuItemResponse> toPublicResponses(List<MenuItem> menuItems) {

        return menuItems.stream()
                .map(this::toPublicResponse)
                .toList();
    }

    private PublicMenuItemResponse toPublicResponse(MenuItem menuItem) {

        MenuItemAnalysis analysis = menuItemAnalysisService.analyze(menuItem);

        return new PublicMenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                getDescription(menuItem),
                menuItem.getType(),
                menuItem.getPrice(),
                analysis.dietType(),
                analysis.allergens()
        );
    }

    private MenuItemIndexResponse toIndexResponse(MenuItem menuItem) {

        MenuItemAnalysis analysis = menuItemAnalysisService.analyze(menuItem);

        return new MenuItemIndexResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getType(),
                menuItem.getPrice(),
                analysis.totalCost(),
                analysis.foodCostPercentage(),
                analysis.foodCostStatus(),
                analysis.dietType(),
                analysis.allergens(),
                menuItem.isArchived()
        );
    }

    private String getDescription(MenuItem menuItem) {

        if (StringUtils.hasText(menuItem.getDescription())) {
            return menuItem.getDescription();
        }

        List<RecipeItem> items = recipeItemService.getAll(menuItem.getRecipe().getId());

        return items.stream()
                .map(item -> item.isIngredient()
                        ? item.getIngredient().getName()
                        : item.getSubRecipe().getName())
                .collect(Collectors.joining(", "));
    }
}