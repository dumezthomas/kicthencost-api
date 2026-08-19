package dev.dumezthomas.kitchencost.assemblers;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.models.menuitem.responses.MenuItemIndexResponse;
import dev.dumezthomas.kitchencost.models.menuitem.responses.MenuItemResponse;
import dev.dumezthomas.kitchencost.results.MenuItemAnalysis;
import dev.dumezthomas.kitchencost.services.MenuItemAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemAssembler {

    private final MenuItemAnalysisService menuItemAnalysisService;

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
}