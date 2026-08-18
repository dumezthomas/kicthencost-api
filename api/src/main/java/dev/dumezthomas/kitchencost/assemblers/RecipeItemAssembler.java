package dev.dumezthomas.kitchencost.assemblers;

import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.RecipeItemType;
import dev.dumezthomas.kitchencost.models.recipeitem.responses.RecipeItemIndexResponse;
import dev.dumezthomas.kitchencost.results.RecipeAnalysis;
import dev.dumezthomas.kitchencost.results.RecipeItemAnalysis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeItemAssembler {

    public List<RecipeItemIndexResponse> toIndexResponses(List<RecipeItem> items, RecipeAnalysis analysis) {

        return items.stream()
                .map(item -> toIndexResponse(item, analysis.itemAnalyses().get(item.getId())))
                .toList();
    }

    private RecipeItemIndexResponse toIndexResponse(RecipeItem item, RecipeItemAnalysis itemAnalysis) {

        RecipeItemType type = item.isIngredient()
                ? RecipeItemType.INGREDIENT
                : RecipeItemType.SUB_RECIPE;

        UUID referenceId = item.isIngredient()
                ? item.getIngredient().getId()
                : item.getSubRecipe().getId();

        String name = item.isIngredient()
                ? item.getIngredient().getName()
                : item.getSubRecipe().getName();

        return new RecipeItemIndexResponse(
                item.getId(),
                type,
                referenceId,
                name,
                item.getQuantity(),
                item.getUnit(),
                itemAnalysis.totalCost(),
                itemAnalysis.dietType(),
                itemAnalysis.allergens()
        );
    }
}