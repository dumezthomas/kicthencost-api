package dev.dumezthomas.kitchencost.assemblers;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.models.recipe.responses.RecipeIndexResponse;
import dev.dumezthomas.kitchencost.models.recipe.responses.RecipeResponse;
import dev.dumezthomas.kitchencost.models.recipeitem.responses.RecipeItemIndexResponse;
import dev.dumezthomas.kitchencost.results.RecipeAnalysis;
import dev.dumezthomas.kitchencost.services.RecipeAnalysisService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeAssembler {

    private final RecipeItemService recipeItemService;
    private final RecipeAnalysisService recipeAnalysisService;

    private final RecipeItemAssembler recipeItemAssembler;

    public RecipeResponse toResponse(Recipe recipe) {

        List<RecipeItem> items = recipeItemService.getAll(recipe.getId());

        RecipeAnalysis analysis = recipeAnalysisService.analyze(recipe);

        List<RecipeItemIndexResponse> itemResponses = recipeItemAssembler.toIndexResponses(items, analysis);

        return new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getInstructions(),
                recipe.getYieldQuantity(),
                recipe.getYieldUnit(),
                analysis.totalCost(),
                analysis.dietType(),
                analysis.allergens(),
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

        RecipeAnalysis analysis = recipeAnalysisService.analyze(recipe);

        return new RecipeIndexResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getYieldQuantity(),
                recipe.getYieldUnit(),
                analysis.totalCost(),
                analysis.dietType(),
                analysis.allergens(),
                recipe.isArchived()
        );
    }
}