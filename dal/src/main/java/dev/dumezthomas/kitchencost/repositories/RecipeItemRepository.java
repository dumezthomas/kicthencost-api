package dev.dumezthomas.kitchencost.repositories;

import dev.dumezthomas.kitchencost.entities.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, UUID> {

    List<RecipeItem> findAllByRecipeId(UUID recipeId);

    Optional<RecipeItem> findByRecipeIdAndId(UUID recipeId, UUID itemId);

    boolean existsByRecipeIdAndIngredientId(UUID recipeId, UUID ingredientId);

    boolean existsByRecipeIdAndSubRecipeId(UUID recipeId, UUID subRecipeId);
}
