package dev.dumezthomas.kitchencost.repositories;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    List<Ingredient> findAllByRestaurantId(UUID restaurantId);

    boolean existsByNameIgnoreCaseAndRestaurantId(String name, UUID restaurantId);
}
