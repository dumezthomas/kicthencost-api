package dev.dumezthomas.kitchencost.seeders;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.repositories.RecipeRepository;
import dev.dumezthomas.kitchencost.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecipeSeeder {

    private final RestaurantRepository restaurantRepository;
    private final RecipeRepository recipeRepository;

    public void seed() {

        if (recipeRepository.count() > 0) {
            return;
        }

        List<Recipe> recipes = List.of(

                new Recipe(
                        "Pizza Dough",
                        BigDecimal.valueOf(4),
                        Unit.KG
                ),

                new Recipe(
                        "Tomato Sauce",
                        BigDecimal.valueOf(5),
                        Unit.L
                ),

                new Recipe(
                        "Bolognese Sauce",
                        BigDecimal.valueOf(6),
                        Unit.L
                ),

                new Recipe(
                        "Garlic Butter",
                        BigDecimal.valueOf(1),
                        Unit.KG
                ),

                new Recipe(
                        "Caesar Dressing",
                        BigDecimal.valueOf(2),
                        Unit.L
                ),

                new Recipe(
                        "Pancake Batter",
                        BigDecimal.valueOf(3),
                        Unit.L
                ),

                new Recipe(
                        "Mashed Potatoes",
                        BigDecimal.valueOf(5),
                        Unit.KG
                ),

                new Recipe(
                        "Vegetable Soup",
                        BigDecimal.valueOf(8),
                        Unit.L
                ),

                new Recipe(
                        "Burger Patty",
                        BigDecimal.valueOf(10),
                        Unit.PIECE
                ),

                new Recipe(
                        "Falafel",
                        BigDecimal.valueOf(24),
                        Unit.PIECE
                )
        );

        Restaurant restaurant = restaurantRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No restaurant found.")
                );

        recipes.forEach(r -> r.setRestaurant(restaurant));
        recipeRepository.saveAll(recipes);

        log.info("Seeded {} recipes.", recipes.size());
    }
}