package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.Ingredient;

import java.util.List;
import java.util.UUID;

public interface IngredientService {

    List<Ingredient> getAll(UUID restaurantId);

    Ingredient getById(UUID restaurantId, UUID ingredientId);

    UUID create(UUID restaurantId, Ingredient ingredient);

    void update(UUID restaurantId, UUID ingredientId, Ingredient ingredient);

    void archive(UUID restaurantId, UUID ingredientId);

    void restore(UUID restaurantId, UUID ingredientId);

    long count(UUID restaurantId);
}
