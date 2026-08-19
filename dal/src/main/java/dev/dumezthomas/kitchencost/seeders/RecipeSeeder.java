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
                        "Sweat the onions and garlic in olive oil, add the tomatoes, season and simmer for 30 minutes.",
                        BigDecimal.ONE,
                        Unit.KG
                ),

                new Recipe(
                        "Béchamel",
                        "Prepare a roux with butter and flour, gradually whisk in the milk, then season with salt, pepper and nutmeg.",
                        BigDecimal.ONE,
                        Unit.KG
                ),

                new Recipe(
                        "Bolognese",
                        "Brown the beef, add the onions, stir in the tomato sauce and simmer until reduced.",
                        BigDecimal.ONE,
                        Unit.KG
                ),

                new Recipe(
                        "Pesto",
                        "Blend basil, parmesan, pine nuts and olive oil until smooth.",
                        BigDecimal.valueOf(250),
                        Unit.G
                ),

                new Recipe(
                        "Pesto Pasta",
                        "Cook the pasta, toss with pesto and finish with parmesan.",
                        BigDecimal.ONE,
                        Unit.PIECE
                ),

                new Recipe(
                        "Garlic Butter",
                        "Mix softened butter, garlic and parsley until evenly combined.",
                        BigDecimal.valueOf(250),
                        Unit.G
                ),

                new Recipe(
                        "Garlic Bread",
                        "Spread garlic butter on bread and toast until golden.",
                        BigDecimal.ONE,
                        Unit.PIECE
                ),

                new Recipe(
                        "Vinaigrette",
                        "Whisk olive oil, lemon juice, mustard, salt and pepper until emulsified.",
                        BigDecimal.valueOf(500),
                        Unit.ML
                ),

                new Recipe(
                        "House Salad",
                        "Combine fresh vegetables and toss with vinaigrette.",
                        BigDecimal.ONE,
                        Unit.PIECE
                ),

                new Recipe(
                        "Lasagna",
                        "Layer pasta sheets with bolognese, béchamel and cheeses, then bake until golden.",
                        BigDecimal.ONE,
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