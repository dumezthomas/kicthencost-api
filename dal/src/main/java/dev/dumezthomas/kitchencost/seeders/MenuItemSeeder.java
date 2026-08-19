package dev.dumezthomas.kitchencost.seeders;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.MenuItemType;
import dev.dumezthomas.kitchencost.repositories.MenuItemRepository;
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
public class MenuItemSeeder {

    private final RestaurantRepository restaurantRepository;
    private final RecipeRepository recipeRepository;
    private final MenuItemRepository menuItemRepository;

    public void seed() {

        if (menuItemRepository.count() > 0) {
            return;
        }

        List<Recipe> recipes = recipeRepository.findAll();

        Recipe lasagna = recipe(recipes, "Lasagna");
        Recipe pestoPasta = recipe(recipes, "Pesto Pasta");
        Recipe garlicBread = recipe(recipes, "Garlic Bread");
        Recipe houseSalad = recipe(recipes, "House Salad");

        List<MenuItem> menuItems = List.of(

                new MenuItem(
                        "Classic Lasagna",
                        "Traditional homemade lasagna.",
                        MenuItemType.MAIN,
                        lasagna,
                        BigDecimal.valueOf(18.50)
                ),

                new MenuItem(
                        "Pesto Pasta",
                        "Fresh pasta tossed in homemade pesto.",
                        MenuItemType.MAIN,
                        pestoPasta,
                        BigDecimal.valueOf(15.50)
                ),

                new MenuItem(
                        "Garlic Bread",
                        "Toasted bread with homemade garlic butter.",
                        MenuItemType.STARTER,
                        garlicBread,
                        BigDecimal.valueOf(6.50)
                ),

                new MenuItem(
                        "House Salad",
                        "Mixed salad served with homemade vinaigrette.",
                        MenuItemType.STARTER,
                        houseSalad,
                        BigDecimal.valueOf(8.50)
                )
        );

        Restaurant restaurant = restaurantRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No restaurant found.")
                );

        menuItems.forEach(m -> m.setRestaurant(restaurant));
        menuItemRepository.saveAll(menuItems);

        log.info("Seeded {} menu items.", menuItems.size());
    }

    private Recipe recipe(List<Recipe> recipes, String name) {

        return recipes.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Recipe not found: " + name)
                );
    }
}