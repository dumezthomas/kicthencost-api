package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Resource;
import dev.dumezthomas.kitchencost.exceptions.InvalidOperationException;
import dev.dumezthomas.kitchencost.exceptions.ResourceNotFoundException;
import dev.dumezthomas.kitchencost.repositories.RecipeItemRepository;
import dev.dumezthomas.kitchencost.services.IngredientService;
import dev.dumezthomas.kitchencost.services.RecipeItemService;
import dev.dumezthomas.kitchencost.services.RecipeService;
import dev.dumezthomas.kitchencost.services.UnitConversionService;
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
    private final UnitConversionService unitConversionService;

    @Override
    public List<RecipeItem> getAll(UUID recipeId) {

        return recipeItemRepository.findAllByRecipeId(recipeId);
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
        unitConversionService.validateCompatibility(recipeItem.getUnit(), ingredient.getDefaultUnit());

        recipeItem.setIngredient(ingredient);
        recipeItem.setRecipe(recipe);

        recipeItemRepository.save(recipeItem);
    }

    @Transactional
    @Override
    public void createSubRecipe(UUID restaurantId, UUID recipeId, UUID subRecipeId, RecipeItem recipeItem) {

        Recipe recipe = recipeService.getById(restaurantId, recipeId);

        if (recipe.getId().equals(subRecipeId)) {

            throw new InvalidOperationException("Recipe cannot contain itself.");
        }

        if (recipeItemRepository.existsByRecipeIdAndSubRecipeId(recipe.getId(), subRecipeId)) {

            throw new InvalidOperationException("The sub-recipe is already part of this recipe.");
        }

        validateNoCircularReference(recipe.getId(), subRecipeId);

        Recipe subRecipe = recipeService.getById(restaurantId, subRecipeId);
        unitConversionService.validateCompatibility(recipeItem.getUnit(), subRecipe.getYieldUnit());

        recipeItem.setSubRecipe(subRecipe);
        recipeItem.setRecipe(recipe);

        recipeItemRepository.save(recipeItem);
    }

    @Transactional
    @Override
    public void update(UUID restaurantId, UUID recipeId, UUID itemId, RecipeItem recipeItem) {

        RecipeItem existing = getById(restaurantId, recipeId, itemId);

        unitConversionService.validateCompatibility(
                recipeItem.getUnit(),
                existing.isIngredient() ?
                        existing.getIngredient().getDefaultUnit() :
                        existing.getSubRecipe().getYieldUnit()
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

    private void validateNoCircularReference(UUID recipeId, UUID subRecipeId) {

        if (containsSubRecipe(subRecipeId, recipeId)) {

            throw new InvalidOperationException("This operation would create a circular recipe reference.");
        }
    }

    private boolean containsSubRecipe(UUID rootRecipeId, UUID searchedRecipeId) {

        if (rootRecipeId.equals(searchedRecipeId)) {

            return true;
        }

        List<RecipeItem> items = getAll(rootRecipeId);

        for (RecipeItem item : items) {

            if (!item.isIngredient() && containsSubRecipe(item.getSubRecipe().getId(), searchedRecipeId)) {

                return true;
            }
        }

        return false;
    }

}