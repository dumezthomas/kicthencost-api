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
                        "Tomato Sauce",
                        BigDecimal.ONE,
                        Unit.KG
                ),

                new Recipe(
                        "Béchamel",
                        BigDecimal.ONE,
                        Unit.KG
                ),

                new Recipe(
                        "Bolognese",
                        BigDecimal.ONE,
                        Unit.KG
                ),

                new Recipe(
                        "Lasagna",
                        BigDecimal.ONE,
                        Unit.PIECE
                ),

                new Recipe(
                        "Vinaigrette",
                        BigDecimal.valueOf(500),
                        Unit.ML
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