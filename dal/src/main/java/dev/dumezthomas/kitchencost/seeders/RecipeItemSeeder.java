package dev.dumezthomas.kitchencost.seeders;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.RecipeItem;
import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.repositories.IngredientRepository;
import dev.dumezthomas.kitchencost.repositories.RecipeItemRepository;
import dev.dumezthomas.kitchencost.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecipeItemSeeder {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeItemRepository recipeItemRepository;

    public void seed() {

        if (recipeItemRepository.count() > 0) {
            return;
        }

        List<Recipe> recipes = recipeRepository.findAll();
        List<Ingredient> ingredients = ingredientRepository.findAll();

        Recipe tomatoSauce = recipe(recipes, "Tomato Sauce");
        Recipe bechamel = recipe(recipes, "Béchamel");
        Recipe bolognese = recipe(recipes, "Bolognese");
        Recipe lasagna = recipe(recipes, "Lasagna");
        Recipe vinaigrette = recipe(recipes, "Vinaigrette");

        Ingredient flour = ingredient(ingredients, "Flour");
        Ingredient salt = ingredient(ingredients, "Salt");
        Ingredient pepper = ingredient(ingredients, "Black Pepper");
        Ingredient oliveOil = ingredient(ingredients, "Olive Oil");
        Ingredient milk = ingredient(ingredients, "Milk");
        Ingredient butter = ingredient(ingredients, "Butter");
        Ingredient parmesan = ingredient(ingredients, "Parmesan");
        Ingredient mozzarella = ingredient(ingredients, "Mozzarella");
        Ingredient tomato = ingredient(ingredients, "Tomato");
        Ingredient onion = ingredient(ingredients, "Onion");
        Ingredient garlic = ingredient(ingredients, "Garlic");
        Ingredient groundBeef = ingredient(ingredients, "Ground Beef");
        Ingredient pastaSheets = ingredient(ingredients, "Pasta Sheets");
        Ingredient nutmeg = ingredient(ingredients, "Nutmeg");

        List<RecipeItem> items = List.of(

                // Tomato Sauce
                createRecipeItem(tomatoSauce, tomato, BigDecimal.valueOf(800), Unit.G),
                createRecipeItem(tomatoSauce, onion, BigDecimal.valueOf(150), Unit.G),
                createRecipeItem(tomatoSauce, garlic, BigDecimal.valueOf(15), Unit.G),
                createRecipeItem(tomatoSauce, oliveOil, BigDecimal.valueOf(30), Unit.ML),
                createRecipeItem(tomatoSauce, salt, BigDecimal.valueOf(5), Unit.G),
                createRecipeItem(tomatoSauce, pepper, BigDecimal.valueOf(2), Unit.G),

                // Béchamel
                createRecipeItem(bechamel, milk, BigDecimal.valueOf(750), Unit.ML),
                createRecipeItem(bechamel, butter, BigDecimal.valueOf(75), Unit.G),
                createRecipeItem(bechamel, flour, BigDecimal.valueOf(75), Unit.G),
                createRecipeItem(bechamel, salt, BigDecimal.valueOf(5), Unit.G),
                createRecipeItem(bechamel, pepper, BigDecimal.valueOf(2), Unit.G),
                createRecipeItem(bechamel, nutmeg, BigDecimal.ONE, Unit.G),

                // Bolognese
                createRecipeItem(bolognese, groundBeef, BigDecimal.valueOf(700), Unit.G),
                createRecipeItem(bolognese, tomatoSauce, BigDecimal.ONE, Unit.KG),
                createRecipeItem(bolognese, onion, BigDecimal.valueOf(150), Unit.G),
                createRecipeItem(bolognese, oliveOil, BigDecimal.valueOf(30), Unit.ML),
                createRecipeItem(bolognese, salt, BigDecimal.valueOf(5), Unit.G),
                createRecipeItem(bolognese, pepper, BigDecimal.valueOf(2), Unit.G),

                // Lasagna
                createRecipeItem(lasagna, bolognese, BigDecimal.ONE, Unit.KG),
                createRecipeItem(lasagna, bechamel, BigDecimal.valueOf(500), Unit.G),
                createRecipeItem(lasagna, pastaSheets, BigDecimal.valueOf(300), Unit.G),
                createRecipeItem(lasagna, mozzarella, BigDecimal.valueOf(250), Unit.G),
                createRecipeItem(lasagna, parmesan, BigDecimal.valueOf(100), Unit.G),

                // Vinaigrette
                createRecipeItem(vinaigrette, oliveOil, BigDecimal.valueOf(300), Unit.ML),
                createRecipeItem(vinaigrette, salt, BigDecimal.valueOf(5), Unit.G),
                createRecipeItem(vinaigrette, pepper, BigDecimal.valueOf(2), Unit.G)
        );

        recipeItemRepository.saveAll(items);

        log.info("Seeded {} recipe items.", items.size());
    }

    private RecipeItem createRecipeItem(Recipe recipe, Ingredient ingredient, BigDecimal quantity, Unit unit) {

        RecipeItem recipeItem = new RecipeItem();
        recipeItem.setRecipe(recipe);
        recipeItem.setIngredient(ingredient);
        recipeItem.setQuantity(quantity);
        recipeItem.setUnit(unit);

        return recipeItem;
    }

    private RecipeItem createRecipeItem(Recipe recipe, Recipe subRecipe, BigDecimal quantity, Unit unit) {

        RecipeItem recipeItem = new RecipeItem();
        recipeItem.setRecipe(recipe);
        recipeItem.setSubRecipe(subRecipe);
        recipeItem.setQuantity(quantity);
        recipeItem.setUnit(unit);

        return recipeItem;
    }

    private Recipe recipe(List<Recipe> recipes, String name) {

        return recipes.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Recipe not found: " + name)
                );
    }

    private Ingredient ingredient(List<Ingredient> ingredients, String name) {

        return ingredients.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Ingredient not found: " + name)
                );
    }
}