package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.enums.FoodCostStatus;
import dev.dumezthomas.kitchencost.results.MenuItemAnalysis;
import dev.dumezthomas.kitchencost.results.RestaurantAnalysis;
import dev.dumezthomas.kitchencost.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RestaurantAnalysisServiceImpl implements RestaurantAnalysisService {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final MenuItemService menuItemService;
    private final MenuItemAnalysisService menuItemAnalysisService;

    @Override
    public RestaurantAnalysis analyze(UUID restaurantId) {

        long ingredientCount = ingredientService.count(restaurantId);
        long recipeCount = recipeService.count(restaurantId);

        List<MenuItem> menuItems = menuItemService.getAllActive(restaurantId);

        List<MenuItemAnalysis> menuItemAnalyses = menuItems.stream()
                .map(menuItemAnalysisService::analyze)
                .toList();

        return RestaurantAnalysis.builder()
                .ingredients(ingredientCount)
                .recipes(recipeCount)
                .menuItems(menuItems.size())
                .averageFoodCostPercentage(average(menuItemAnalyses, MenuItemAnalysis::foodCostPercentage))
                .averageMarkup(average(menuItemAnalyses, MenuItemAnalysis::markup))
                .averageMargin(average(menuItemAnalyses, MenuItemAnalysis::margin))
                .goodItems(countStatus(menuItemAnalyses, FoodCostStatus.GOOD))
                .warningItems(countStatus(menuItemAnalyses, FoodCostStatus.WARNING))
                .criticalItems(countStatus(menuItemAnalyses, FoodCostStatus.CRITICAL))
                .build();
    }

    private BigDecimal average(List<MenuItemAnalysis> menuItemAnalyses, Function<MenuItemAnalysis, BigDecimal> extractor) {

        if (menuItemAnalyses.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return menuItemAnalyses.stream()
                .map(extractor)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(menuItemAnalyses.size()), 2, RoundingMode.HALF_UP);
    }

    private long countStatus(List<MenuItemAnalysis> menuItemAnalyses, FoodCostStatus status) {

        return menuItemAnalyses.stream()
                .filter(menuItemAnalyse -> menuItemAnalyse.foodCostStatus() == status)
                .count();
    }

}