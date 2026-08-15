package dev.dumezthomas.kitchencost.seeders;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.IngredientCategory;
import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.repositories.IngredientRepository;
import dev.dumezthomas.kitchencost.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class IngredientSeeder {

    private final RestaurantRepository restaurantRepository;
    private final IngredientRepository ingredientRepository;

    public void seed() {

        if (ingredientRepository.count() > 0) {
            return;
        }

        List<Ingredient> ingredients = List.of(

                new Ingredient(
                        "Sugar",
                        IngredientCategory.SWEETENER,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(1.60)
                ),

                new Ingredient(
                        "Flour",
                        IngredientCategory.GRAIN,
                        Set.of(Allergen.GLUTEN),
                        Unit.KG,
                        BigDecimal.valueOf(1.80)
                ),

                new Ingredient(
                        "Salt",
                        IngredientCategory.SPICE,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(0.80)
                ),

                new Ingredient(
                        "Black Pepper",
                        IngredientCategory.SPICE,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(18.50)
                ),

                new Ingredient(
                        "Olive Oil",
                        IngredientCategory.OIL,
                        Set.of(),
                        Unit.L,
                        BigDecimal.valueOf(11.90)
                ),

                new Ingredient(
                        "Milk",
                        IngredientCategory.DAIRY,
                        Set.of(Allergen.MILK),
                        Unit.L,
                        BigDecimal.valueOf(1.45)
                ),

                new Ingredient(
                        "Butter",
                        IngredientCategory.DAIRY,
                        Set.of(Allergen.MILK),
                        Unit.KG,
                        BigDecimal.valueOf(12.50)
                ),

                new Ingredient(
                        "Parmesan",
                        IngredientCategory.DAIRY,
                        Set.of(Allergen.MILK),
                        Unit.KG,
                        BigDecimal.valueOf(22.90)
                ),

                new Ingredient(
                        "Mozzarella",
                        IngredientCategory.DAIRY,
                        Set.of(Allergen.MILK),
                        Unit.KG,
                        BigDecimal.valueOf(14.90)
                ),

                new Ingredient(
                        "Egg",
                        IngredientCategory.EGG,
                        Set.of(Allergen.EGGS),
                        Unit.PIECE,
                        BigDecimal.valueOf(0.35)
                ),

                new Ingredient(
                        "Ground Beef",
                        IngredientCategory.MEAT,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(16.90)
                ),

                new Ingredient(
                        "Tomato",
                        IngredientCategory.VEGETABLE,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(3.20)
                ),

                new Ingredient(
                        "Onion",
                        IngredientCategory.VEGETABLE,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(2.40)
                ),

                new Ingredient(
                        "Garlic",
                        IngredientCategory.VEGETABLE,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(6.90)
                ),

                new Ingredient(
                        "Pasta Sheets",
                        IngredientCategory.GRAIN,
                        Set.of(Allergen.GLUTEN),
                        Unit.KG,
                        BigDecimal.valueOf(4.50)
                ),

                new Ingredient(
                        "Nutmeg",
                        IngredientCategory.SPICE,
                        Set.of(),
                        Unit.KG,
                        BigDecimal.valueOf(42.00)
                )
        );

        Restaurant restaurant = restaurantRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No restaurant found.")
                );

        ingredients.forEach(i -> i.setRestaurant(restaurant));
        ingredientRepository.saveAll(ingredients);

        log.info("Seeded {} ingredients.", ingredients.size());
    }
}