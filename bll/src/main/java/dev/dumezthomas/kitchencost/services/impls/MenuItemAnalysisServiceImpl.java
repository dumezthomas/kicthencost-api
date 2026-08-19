package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.FoodCostStatus;
import dev.dumezthomas.kitchencost.results.MenuItemAnalysis;
import dev.dumezthomas.kitchencost.results.RecipeItemAnalysis;
import dev.dumezthomas.kitchencost.services.MenuItemAnalysisService;
import dev.dumezthomas.kitchencost.services.RecipeAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class MenuItemAnalysisServiceImpl implements MenuItemAnalysisService {

    private static final BigDecimal PRICE_ROUNDING_STEP = BigDecimal.valueOf(0.50);

    private final RecipeAnalysisService recipeAnalysisService;

    @Override
    public MenuItemAnalysis analyze(MenuItem menuItem) {

        RecipeItemAnalysis recipeAnalysis = recipeAnalysisService.analyzeSummary(menuItem.getRecipe());

        BigDecimal totalCost = recipeAnalysis.totalCost();

        BigDecimal foodCostPercentage = calculateFoodCostPercentage(totalCost, menuItem.getPrice());
        FoodCostStatus foodCostStatus = calculateFoodCostStatus(foodCostPercentage, menuItem.getRestaurant());

        BigDecimal margin = menuItem.getPrice().subtract(totalCost);
        BigDecimal markup = calculateMarkup(totalCost, menuItem.getPrice());

        BigDecimal suggestedPrice = calculateSuggestedPrice(totalCost, menuItem.getRestaurant());

        return MenuItemAnalysis.builder()
                .totalCost(totalCost)
                .foodCostPercentage(foodCostPercentage)
                .foodCostStatus(foodCostStatus)
                .margin(margin)
                .markup(markup)
                .suggestedPrice(suggestedPrice)
                .dietType(recipeAnalysis.dietType())
                .allergens(recipeAnalysis.allergens())
                .build();
    }

    private BigDecimal calculateFoodCostPercentage(BigDecimal cost, BigDecimal price) {

        if (price.signum() == 0) {
            return BigDecimal.ZERO;
        }

        return cost
                .divide(price, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private FoodCostStatus calculateFoodCostStatus(BigDecimal foodCostPercentage, Restaurant restaurant) {

        if (foodCostPercentage.compareTo(restaurant.getCriticalFoodCostPercentage()) >= 0) {
            return FoodCostStatus.CRITICAL;
        }

        if (foodCostPercentage.compareTo(restaurant.getWarningFoodCostPercentage()) >= 0) {
            return FoodCostStatus.WARNING;
        }

        return FoodCostStatus.GOOD;
    }

    private BigDecimal calculateMarkup(BigDecimal cost, BigDecimal price) {

        if (cost.signum() == 0) {
            return BigDecimal.ZERO;
        }

        return price.divide(cost, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateSuggestedPrice(BigDecimal cost, Restaurant restaurant) {

        BigDecimal targetRatio = restaurant.getTargetFoodCostPercentage()
                .divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);

        return roundSuggestedPrice(cost.divide(targetRatio, 2, RoundingMode.HALF_UP));
    }

    private BigDecimal roundSuggestedPrice(BigDecimal price) {

        return price
                .divide(PRICE_ROUNDING_STEP, 0, RoundingMode.CEILING)
                .multiply(PRICE_ROUNDING_STEP);
    }
}