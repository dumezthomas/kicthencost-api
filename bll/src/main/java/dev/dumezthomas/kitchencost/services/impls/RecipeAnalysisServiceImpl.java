package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.results.RecipeAnalysis;
import dev.dumezthomas.kitchencost.results.RecipeItemAnalysis;
import dev.dumezthomas.kitchencost.services.RecipeAnalysisService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import dev.dumezthomas.kitchencost.services.UnitConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecipeAnalysisServiceImpl implements RecipeAnalysisService {

    private final RecipeItemService recipeItemService;
    private final UnitConversionService unitConversionService;

    @Override
    public RecipeAnalysis analyze(Recipe recipe) {

        List<RecipeItem> items = recipeItemService.getAll(recipe.getId());

        Map<UUID, RecipeItemAnalysis> itemAnalyses = new LinkedHashMap<>();

        BigDecimal totalCost = BigDecimal.ZERO;
        Set<Allergen> allergens = new HashSet<>();
        DietType dietType = DietType.VEGAN;

        for (RecipeItem item : items) {

            RecipeItemAnalysis itemAnalysis = analyze(item);

            itemAnalyses.put(item.getId(), itemAnalysis);

            totalCost = totalCost.add(itemAnalysis.totalCost());
            allergens.addAll(itemAnalysis.allergens());
            dietType = DietType.merge(dietType, itemAnalysis.dietType());
        }

        return RecipeAnalysis.builder()
                .totalCost(totalCost)
                .allergens(allergens)
                .dietType(dietType)
                .itemAnalyses(itemAnalyses)
                .build();
    }

    @Override
    public RecipeItemAnalysis analyzeSummary(Recipe recipe) {

        List<RecipeItem> items = recipeItemService.getAll(recipe.getId());

        BigDecimal totalCost = BigDecimal.ZERO;
        Set<Allergen> allergens = new HashSet<>();
        DietType dietType = DietType.VEGAN;

        for (RecipeItem item : items) {

            RecipeItemAnalysis itemAnalysis = analyze(item);

            totalCost = totalCost.add(itemAnalysis.totalCost());
            allergens.addAll(itemAnalysis.allergens());
            dietType = DietType.merge(dietType, itemAnalysis.dietType());
        }

        return RecipeItemAnalysis.builder()
                .totalCost(totalCost)
                .allergens(allergens)
                .dietType(dietType)
                .build();
    }

    private RecipeItemAnalysis analyze(RecipeItem item) {

        return item.isIngredient()
                ? analyzeIngredient(item)
                : analyzeSubRecipe(item);
    }

    private RecipeItemAnalysis analyzeIngredient(RecipeItem item) {

        Ingredient ingredient = item.getIngredient();

        BigDecimal quantityInDefaultUnit = unitConversionService.convert(
                item.getQuantity(),
                item.getUnit(),
                ingredient.getDefaultUnit()
        );

        BigDecimal totalCost = quantityInDefaultUnit.multiply(ingredient.getCurrentPriceByDefaultUnit());

        return RecipeItemAnalysis.builder()
                .totalCost(totalCost)
                .allergens(ingredient.getAllergens())
                .dietType(ingredient.getIngredientCategory().getDietType())
                .build();
    }

    private RecipeItemAnalysis analyzeSubRecipe(RecipeItem item) {

        Recipe subRecipe = item.getSubRecipe();

        RecipeItemAnalysis subRecipeAnalysis = analyzeSummary(subRecipe);

        BigDecimal quantityInYieldUnit = unitConversionService.convert(
                item.getQuantity(),
                item.getUnit(),
                subRecipe.getYieldUnit()
        );

        BigDecimal ratio = quantityInYieldUnit.divide(subRecipe.getYieldQuantity(), 8, RoundingMode.HALF_UP);

        return RecipeItemAnalysis.builder()
                .totalCost(subRecipeAnalysis.totalCost().multiply(ratio))
                .allergens(subRecipeAnalysis.allergens())
                .dietType(subRecipeAnalysis.dietType())
                .build();
    }
}