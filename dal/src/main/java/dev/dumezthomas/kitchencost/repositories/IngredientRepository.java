package dev.dumezthomas.kitchencost.repositories;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    List<Ingredient> findAllByRestaurantIdAndArchivedFalse(UUID restaurantId);

    Optional<Ingredient> findByRestaurantIdAndId(UUID restaurantId, UUID ingredientId);

    boolean existsByRestaurantIdAndNameIgnoreCase(UUID restaurantId, String ingredientName);
}
