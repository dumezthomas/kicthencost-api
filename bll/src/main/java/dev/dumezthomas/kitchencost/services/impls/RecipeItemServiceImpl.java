package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Resource;
import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.exceptions.InvalidOperationException;
import dev.dumezthomas.kitchencost.exceptions.ResourceNotFoundException;
import dev.dumezthomas.kitchencost.repositories.RecipeItemRepository;
import dev.dumezthomas.kitchencost.services.IngredientService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import dev.dumezthomas.kitchencost.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeItemServiceImpl implements RecipeItemService {

    private final RecipeItemRepository recipeItemRepository;

    private final RecipeService recipeService;
    private final IngredientService ingredientService;


    @Override
    public List<RecipeItem> getAll(UUID restaurantId, UUID recipeId) {

        Recipe recipe = recipeService.getById(restaurantId, recipeId);

        return recipeItemRepository.findAllByRecipeId(recipe.getId());
    }

    @Override
    public RecipeItem getById(UUID restaurantId, UUID recipeId, UUID itemId) {

        Recipe recipe = recipeService.getById(restaurantId, recipeId);

        return recipeItemRepository.findByRecipeIdAndId(recipe.getId(), itemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Resource.RECIPE_ITEM,
                        itemId
                ));
    }

    @Transactional
    @Override
    public void createIngredient(UUID restaurantId, UUID recipeId, UUID ingredientId, RecipeItem recipeItem) {

        Recipe recipe = recipeService.getById(restaurantId, recipeId);

        if (recipeItemRepository.existsByRecipeIdAndIngredientId(recipe.getId(), ingredientId)) {

            throw new InvalidOperationException("The ingredient is already part of this recipe.");
        }

        Ingredient ingredient = ingredientService.getById(restaurantId, ingredientId);

        validateUnitCompatibility(recipeItem.getUnit(), ingredient.getDefaultUnit(), "ingredient");

        recipeItem.setIngredient(ingredient);
        recipeItem.setRecipe(recipe);

        recipeItemRepository.save(recipeItem);
    }

    @Transactional
    @Override
    public void createSubRecipe(UUID restaurantId, UUID recipeId, UUID subRecipeId, RecipeItem recipeItem) {

        Recipe recipe = recipeService.getById(restaurantId, recipeId);

        if (recipe.getId().equals(subRecipeId)) {

            // TODO Prevent circular references between sub-recipes (A -> B -> A)

            throw new InvalidOperationException("Recipe cannot contain itself.");
        }

        if (recipeItemRepository.existsByRecipeIdAndSubRecipeId(recipe.getId(), subRecipeId)) {

            throw new InvalidOperationException("The sub-recipe is already part of this recipe.");
        }

        Recipe subRecipe = recipeService.getById(restaurantId, subRecipeId);

        validateUnitCompatibility(recipeItem.getUnit(), subRecipe.getYieldUnit(), "sub-recipe");

        recipeItem.setSubRecipe(subRecipe);
        recipeItem.setRecipe(recipe);

        recipeItemRepository.save(recipeItem);
    }

    @Transactional
    @Override
    public void update(UUID restaurantId, UUID recipeId, UUID itemId, RecipeItem recipeItem) {

        RecipeItem existing = getById(restaurantId, recipeId, itemId);

        validateUnitCompatibility(
                recipeItem.getUnit(),
                existing.isIngredient() ?
                        existing.getIngredient().getDefaultUnit() :
                        existing.getSubRecipe().getYieldUnit(),
                existing.isIngredient() ?
                        "ingredient" :
                        "sub-recipe"
        );

        existing.setQuantity(recipeItem.getQuantity());
        existing.setUnit(recipeItem.getUnit());
    }

    @Transactional
    @Override
    public void delete(UUID restaurantId, UUID recipeId, UUID itemId) {

        RecipeItem item = getById(restaurantId, recipeId, itemId);

        recipeItemRepository.delete(item);
    }

    private void validateUnitCompatibility(Unit unit, Unit referenceUnit, String referenceType) {

        if (unit.getType() != referenceUnit.getType()) {

            throw new InvalidOperationException(
                    String.format("Unit '%s' is not compatible with %s unit '%s'.",
                            unit, referenceType, referenceUnit)
            );
        }
    }
}