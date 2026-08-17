package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.services.RecipeCalculationService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import dev.dumezthomas.kitchencost.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeCalculationServiceImpl implements RecipeCalculationService {

    private final RecipeService recipeService;
    private final RecipeItemService recipeItemService;

    @Override
    public BigDecimal calculateCost(Recipe recipe) {

        BigDecimal total = BigDecimal.ZERO;

        List<RecipeItem> items = recipeItemService.getAll(recipe.getId());

        for (RecipeItem item : items) {

            if (!item.isIngredient()) {
                continue;
            }

            // TODO conversion vers l'unité par défaut
            // TODO coût = quantité × prix

            // total = total.add(...);
        }

        return total;
    }

    @Override
    public DietType calculateDietType(Recipe recipe) {

        return null;
    }

    @Override
    public Set<Allergen> calculateAllergens(Recipe recipe) {

        return Set.of();
    }
}