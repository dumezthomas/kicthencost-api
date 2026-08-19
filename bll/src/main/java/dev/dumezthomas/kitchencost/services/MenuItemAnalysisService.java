package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.results.MenuItemAnalysis;

import java.util.List;
import java.util.UUID;

public interface MenuItemAnalysisService {

    MenuItemAnalysis analyze(MenuItem menuItem);

    List<MenuItem> getBestFoodCostItems(UUID restaurantId);

    List<MenuItem> getWorstFoodCostItems(UUID restaurantId);
}
