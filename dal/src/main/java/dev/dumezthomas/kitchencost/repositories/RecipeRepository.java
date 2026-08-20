package dev.dumezthomas.kitchencost.repositories;

import dev.dumezthomas.kitchencost.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    List<Recipe> findAllByRestaurantId(UUID restaurantId);

    Optional<Recipe> findByRestaurantIdAndId(UUID restaurantId, UUID recipeId);

    boolean existsByRestaurantIdAndNameIgnoreCase(UUID restaurantId, String recipeName);

    long countByRestaurantIdAndArchivedFalse(UUID restaurantId);
}
