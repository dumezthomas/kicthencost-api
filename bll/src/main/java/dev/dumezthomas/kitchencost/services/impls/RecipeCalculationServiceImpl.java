package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.services.RecipeCalculationService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import dev.dumezthomas.kitchencost.services.RecipeService;
import dev.dumezthomas.kitchencost.services.UnitConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeCalculationServiceImpl implements RecipeCalculationService {

    private final RecipeService recipeService;
    private final RecipeItemService recipeItemService;
    private final UnitConversionService unitConversionService;

    @Override
    public BigDecimal calculateCost(Recipe recipe) {

        return recipeItemService.getAll(recipe.getId())
                .stream()
                .map(this::calculateCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateCost(RecipeItem item) {

        return item.isIngredient()
                ? calculateIngredientCost(item)
                : calculateSubRecipeCost(item);
    }

    @Override
    public DietType calculateDietType(Recipe recipe) {

        return null;
    }

    @Override
    public Set<Allergen> calculateAllergens(Recipe recipe) {

        return Set.of();
    }

    private BigDecimal calculateIngredientCost(RecipeItem item) {

        Ingredient ingredient = item.getIngredient();

        BigDecimal quantityInDefaultUnit = unitConversionService.convert(
                item.getQuantity(),
                item.getUnit(),
                ingredient.getDefaultUnit()
        );

        return quantityInDefaultUnit.multiply(ingredient.getCurrentPriceByDefaultUnit());
    }

    private BigDecimal calculateSubRecipeCost(RecipeItem item) {

        Recipe subRecipe = item.getSubRecipe();

        BigDecimal recipeCost = calculateCost(subRecipe);

        BigDecimal quantityInYieldUnit = unitConversionService.convert(
                item.getQuantity(),
                item.getUnit(),
                subRecipe.getYieldUnit()
        );

        BigDecimal ratio = quantityInYieldUnit.divide(subRecipe.getYieldQuantity(), 8, RoundingMode.HALF_UP);

        return recipeCost.multiply(ratio);
    }
}