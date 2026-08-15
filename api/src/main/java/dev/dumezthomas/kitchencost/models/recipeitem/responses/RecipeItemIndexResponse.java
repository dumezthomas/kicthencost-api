package dev.dumezthomas.kitchencost.models.recipeitem.responses;

import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.RecipeItemType;
import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeItemIndexResponse(

        UUID id,

        RecipeItemType type,
        UUID referenceId,

        String name,

        BigDecimal quantity,
        Unit unit

        // TODO DietType dietType,
        // TODO Set<Allergen> allergens,

        // TODO BigDecimal totalCost
) {

    public static RecipeItemIndexResponse fromRecipeItem(RecipeItem ri) {

        return new RecipeItemIndexResponse(
                
                ri.getId(),

                ri.isIngredient() ? RecipeItemType.INGREDIENT : RecipeItemType.SUB_RECIPE,
                ri.isIngredient() ? ri.getIngredient().getId() : ri.getSubRecipe().getId(),

                ri.isIngredient() ? ri.getIngredient().getName() : ri.getSubRecipe().getName(),

                ri.getQuantity(),
                ri.getUnit()
        );
    }
}
