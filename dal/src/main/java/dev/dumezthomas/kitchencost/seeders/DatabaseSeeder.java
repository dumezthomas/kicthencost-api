package dev.dumezthomas.kitchencost.seeders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final RestaurantSeeder restaurantSeeder;
    private final IngredientSeeder ingredientSeeder;
    private final RecipeSeeder recipeSeeder;
    private final RecipeItemSeeder recipeItemSeeder;
    private final MenuItemSeeder menuItemSeeder;

    @Override
    public void run(String... args) {

        log.info("Seeding database...");

        restaurantSeeder.seed();
        ingredientSeeder.seed();
        recipeSeeder.seed();
        recipeItemSeeder.seed();
        menuItemSeeder.seed();

        log.info("Database seeded.");
    }
}
