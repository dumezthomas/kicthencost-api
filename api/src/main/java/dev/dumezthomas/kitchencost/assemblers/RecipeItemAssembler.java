package dev.dumezthomas.kitchencost.assemblers;

import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.RecipeItemType;
import dev.dumezthomas.kitchencost.models.recipeitem.responses.RecipeItemIndexResponse;
import dev.dumezthomas.kitchencost.services.RecipeCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeItemAssembler {

    private final RecipeCalculationService recipeCalculationService;

    public List<RecipeItemIndexResponse> toIndexResponses(List<RecipeItem> items) {

        return items.stream()
                .map(this::toIndexResponse)
                .toList();
    }

    private RecipeItemIndexResponse toIndexResponse(RecipeItem item) {

        RecipeItemType type = item.isIngredient()
                ? RecipeItemType.INGREDIENT
                : RecipeItemType.SUB_RECIPE;

        UUID referenceId = item.isIngredient()
                ? item.getIngredient().getId()
                : item.getSubRecipe().getId();

        String name = item.isIngredient()
                ? item.getIngredient().getName()
                : item.getSubRecipe().getName();

        BigDecimal totalCost = recipeCalculationService.calculateCost(item);

        return new RecipeItemIndexResponse(
                item.getId(),
                type,
                referenceId,
                name,
                item.getQuantity(),
                item.getUnit(),
                totalCost
        );
    }
}