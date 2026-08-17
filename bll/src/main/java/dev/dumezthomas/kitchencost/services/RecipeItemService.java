package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.RecipeItem;

import java.util.List;
import java.util.UUID;

public interface RecipeItemService {

    List<RecipeItem> getAll(UUID recipeId);

    List<RecipeItem> getAll(UUID restaurantId, UUID recipeId);

    RecipeItem getById(UUID restaurantId, UUID recipeId, UUID itemId);

    void createIngredient(UUID restaurantId, UUID recipeId, UUID ingredientId, RecipeItem recipeItem);

    void createSubRecipe(UUID restaurantId, UUID recipeId, UUID subRecipeId, RecipeItem recipeItem);

    void update(UUID restaurantId, UUID recipeId, UUID itemId, RecipeItem recipeItem);

    void delete(UUID restaurantId, UUID recipeId, UUID itemId);
}
