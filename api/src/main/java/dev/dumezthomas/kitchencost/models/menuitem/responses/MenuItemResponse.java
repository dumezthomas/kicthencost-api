package dev.dumezthomas.kitchencost.models.menuitem.responses;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.enums.FoodCostStatus;
import dev.dumezthomas.kitchencost.enums.MenuItemType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record MenuItemResponse(

        UUID id,
        String name,
        String description,
        MenuItemType type,

        UUID recipeId,
        String recipeName,

        BigDecimal price,
        BigDecimal totalCost,
        BigDecimal margin,
        BigDecimal markup,
        BigDecimal foodCostPercentage,
        FoodCostStatus foodCostStatus,
        BigDecimal suggestedPrice,

        DietType dietType,
        Set<Allergen> allergens,

        boolean archived
) {

}