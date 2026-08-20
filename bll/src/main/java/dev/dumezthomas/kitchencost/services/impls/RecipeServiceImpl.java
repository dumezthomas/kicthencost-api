package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.Resource;
import dev.dumezthomas.kitchencost.exceptions.ResourceAlreadyExistsException;
import dev.dumezthomas.kitchencost.exceptions.ResourceNotFoundException;
import dev.dumezthomas.kitchencost.repositories.RecipeRepository;
import dev.dumezthomas.kitchencost.services.RecipeService;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RestaurantService restaurantService;

    @Override
    public List<Recipe> getAll(UUID restaurantId) {

        return recipeRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public Recipe getById(UUID restaurantId, UUID recipeId) {

        return recipeRepository.findByRestaurantIdAndId(restaurantId, recipeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Resource.RECIPE,
                        recipeId
                ));
    }

    @Transactional
    @Override
    public UUID create(UUID restaurantId, Recipe recipe) {

        if (recipeRepository.existsByRestaurantIdAndNameIgnoreCase(restaurantId, recipe.getName())) {

            throw new ResourceAlreadyExistsException(
                    Resource.RECIPE,
                    recipe.getName()
            );
        }

        Restaurant restaurant = restaurantService.getById(restaurantId);
        recipe.setRestaurant(restaurant);

        return recipeRepository.save(recipe).getId();
    }

    @Transactional
    @Override
    public void update(UUID restaurantId, UUID recipeId, Recipe recipe) {

        Recipe existing = getById(restaurantId, recipeId);

        if (!existing.getName().equalsIgnoreCase(recipe.getName())
                && recipeRepository.existsByRestaurantIdAndNameIgnoreCase(restaurantId, recipe.getName())) {

            throw new ResourceAlreadyExistsException(
                    Resource.RECIPE,
                    recipe.getName()
            );
        }

        existing.setName(recipe.getName());
        existing.setInstructions(recipe.getInstructions());
        existing.setYieldQuantity(recipe.getYieldQuantity());
    }

    @Transactional
    @Override
    public void archive(UUID restaurantId, UUID recipeId) {

        Recipe recipe = getById(restaurantId, recipeId);

        if (!recipe.isArchived()) {
            recipe.setArchived(true);
        }
    }

    @Transactional
    @Override
    public void restore(UUID restaurantId, UUID recipeId) {

        Recipe recipe = getById(restaurantId, recipeId);

        if (recipe.isArchived()) {
            recipe.setArchived(false);
        }
    }

    @Override
    public long count(UUID restaurantId) {

        return recipeRepository.countByRestaurantIdAndArchivedFalse(restaurantId);
    }
}