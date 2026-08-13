package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Ingredient;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.Resource;
import dev.dumezthomas.kitchencost.exceptions.ResourceAlreadyExistsException;
import dev.dumezthomas.kitchencost.exceptions.ResourceNotFoundException;
import dev.dumezthomas.kitchencost.repositories.IngredientRepository;
import dev.dumezthomas.kitchencost.services.IngredientService;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final RestaurantService restaurantService;

    @Override
    public List<Ingredient> getAll(UUID restaurantId) {

        return ingredientRepository.findAllByRestaurantIdAndArchivedFalse(restaurantId);
    }

    @Override
    public Ingredient getById(UUID restaurantId, UUID ingredientId) {

        return ingredientRepository.findByRestaurantIdAndId(restaurantId, ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Resource.INGREDIENT,
                        ingredientId
                ));
    }

    @Transactional
    @Override
    public UUID create(UUID restaurantId, Ingredient ingredient) {

        if (ingredientRepository.existsByRestaurantIdAndNameIgnoreCase(restaurantId, ingredient.getName())) {

            throw new ResourceAlreadyExistsException(
                    Resource.INGREDIENT,
                    ingredient.getName()
            );
        }

        Restaurant restaurant = restaurantService.getById(restaurantId);
        ingredient.setRestaurant(restaurant);

        return ingredientRepository.save(ingredient).getId();
    }

    @Transactional
    @Override
    public void update(UUID restaurantId, UUID ingredientId, Ingredient ingredient) {

        Ingredient existing = getById(restaurantId, ingredientId);

        if (!existing.getName().equalsIgnoreCase(ingredient.getName())
                && ingredientRepository.existsByRestaurantIdAndNameIgnoreCase(restaurantId, ingredient.getName())) {

            throw new ResourceAlreadyExistsException(
                    Resource.INGREDIENT,
                    ingredient.getName()
            );
        }

        existing.setName(ingredient.getName());
        existing.setAllergens(ingredient.getAllergens());
        existing.setCurrentPriceByDefaultUnit(ingredient.getCurrentPriceByDefaultUnit());
    }

    @Transactional
    @Override
    public void archive(UUID restaurantId, UUID ingredientId) {

        Ingredient ingredient = getById(restaurantId, ingredientId);

        if (!ingredient.isArchived()) {
            ingredient.setArchived(true);
        }
    }

    @Transactional
    @Override
    public void restore(UUID restaurantId, UUID ingredientId) {

        Ingredient ingredient = getById(restaurantId, ingredientId);

        if (ingredient.isArchived()) {
            ingredient.setArchived(false);
        }
    }
}
