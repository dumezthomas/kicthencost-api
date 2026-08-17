package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;

import java.math.BigDecimal;
import java.util.Set;

public interface RecipeCalculationService {

    BigDecimal calculateCost(Recipe recipe);

    BigDecimal calculateCost(RecipeItem item);

    DietType calculateDietType(Recipe recipe);

    Set<Allergen> calculateAllergens(Recipe recipe);
}
