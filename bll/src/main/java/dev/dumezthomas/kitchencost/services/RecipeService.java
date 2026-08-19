package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.Recipe;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    List<Recipe> getAll(UUID restaurantId);

    Recipe getById(UUID restaurantId, UUID recipeId);

    UUID create(UUID restaurantId, Recipe recipe);

    void update(UUID restaurantId, UUID recipeId, Recipe recipe);

    void archive(UUID restaurantId, UUID recipeId);

    void restore(UUID restaurantId, UUID recipeId);

    long count(UUID restaurantId);
}
