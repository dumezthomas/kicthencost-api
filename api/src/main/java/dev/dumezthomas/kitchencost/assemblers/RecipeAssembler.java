package dev.dumezthomas.kitchencost.assemblers;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.models.recipe.responses.RecipeIndexResponse;
import dev.dumezthomas.kitchencost.models.recipe.responses.RecipeResponse;
import dev.dumezthomas.kitchencost.models.recipeitem.responses.RecipeItemIndexResponse;
import dev.dumezthomas.kitchencost.services.RecipeCalculationService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeAssembler {

    private final RecipeItemService recipeItemService;
    private final RecipeCalculationService recipeCalculationService;

    private final RecipeItemAssembler recipeItemAssembler;

    public RecipeResponse toResponse(Recipe recipe) {

        List<RecipeItem> items = recipeItemService.getAll(recipe.getId());

        List<RecipeItemIndexResponse> itemResponses = recipeItemAssembler.toIndexResponses(items);

        BigDecimal totalCost = itemResponses.stream()
                .map(RecipeItemIndexResponse::totalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getYieldQuantity(),
                recipe.getYieldUnit(),
                totalCost,
                itemResponses,
                recipe.isArchived()
        );
    }

    public List<RecipeIndexResponse> toIndexResponses(List<Recipe> recipes) {

        return recipes.stream()
                .map(this::toIndexResponse)
                .toList();
    }

    private RecipeIndexResponse toIndexResponse(Recipe recipe) {

        BigDecimal totalCost = recipeCalculationService.calculateCost(recipe);

        return new RecipeIndexResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getYieldQuantity(),
                recipe.getYieldUnit(),
                totalCost,
                recipe.isArchived()
        );
    }
}