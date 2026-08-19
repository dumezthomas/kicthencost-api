package dev.dumezthomas.kitchencost.models.menuitem.responses;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.enums.FoodCostStatus;
import dev.dumezthomas.kitchencost.enums.MenuItemType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record MenuItemIndexResponse(

        UUID id,
        String name,
        MenuItemType type,
        BigDecimal price,

        BigDecimal totalCost,
        BigDecimal foodCostPercentage,
        FoodCostStatus foodCostStatus,

        DietType dietType,
        Set<Allergen> allergens,

        boolean archived
) {

}