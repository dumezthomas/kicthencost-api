package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.Resource;
import dev.dumezthomas.kitchencost.exceptions.ResourceAlreadyExistsException;
import dev.dumezthomas.kitchencost.exceptions.ResourceNotFoundException;
import dev.dumezthomas.kitchencost.repositories.MenuItemRepository;
import dev.dumezthomas.kitchencost.services.MenuItemService;
import dev.dumezthomas.kitchencost.services.RecipeService;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    private final RecipeService recipeService;
    private final RestaurantService restaurantService;

    @Override
    public List<MenuItem> getAll(UUID restaurantId) {

        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public List<MenuItem> getAllActive(UUID restaurantId) {

        return menuItemRepository.findAllByRestaurantIdAndArchivedFalse(restaurantId);
    }

    @Override
    public MenuItem getById(UUID restaurantId, UUID menuItemId) {

        return menuItemRepository.findByRestaurantIdAndId(restaurantId, menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Resource.MENU_ITEM,
                        menuItemId
                ));
    }

    @Transactional
    @Override
    public UUID create(UUID restaurantId, MenuItem menuItem) {

        if (menuItemRepository.existsByRestaurantIdAndNameIgnoreCase(restaurantId, menuItem.getName())) {

            throw new ResourceAlreadyExistsException(
                    Resource.MENU_ITEM,
                    menuItem.getName()
            );
        }

        Restaurant restaurant = restaurantService.getById(restaurantId);
        menuItem.setRestaurant(restaurant);

        Recipe recipe = recipeService.getById(restaurantId, menuItem.getRecipe().getId());
        menuItem.setRecipe(recipe);

        return menuItemRepository.save(menuItem).getId();
    }

    @Transactional
    @Override
    public void update(UUID restaurantId, UUID menuItemId, MenuItem menuItem) {

        MenuItem existing = getById(restaurantId, menuItemId);

        if (!existing.getName().equalsIgnoreCase(menuItem.getName())
                && menuItemRepository.existsByRestaurantIdAndNameIgnoreCase(restaurantId, menuItem.getName())) {

            throw new ResourceAlreadyExistsException(
                    Resource.MENU_ITEM,
                    menuItem.getName()
            );
        }

        Recipe recipe = recipeService.getById(restaurantId, menuItem.getRecipe().getId());
        existing.setRecipe(recipe);

        existing.setName(menuItem.getName());
        existing.setDescription(menuItem.getDescription());
        existing.setType(menuItem.getType());
        existing.setPrice(menuItem.getPrice());
    }

    @Transactional
    @Override
    public void archive(UUID restaurantId, UUID menuItemId) {

        MenuItem menuItem = getById(restaurantId, menuItemId);

        if (!menuItem.isArchived()) {
            menuItem.setArchived(true);
        }
    }

    @Transactional
    @Override
    public void restore(UUID restaurantId, UUID menuItemId) {

        MenuItem menuItem = getById(restaurantId, menuItemId);

        if (menuItem.isArchived()) {
            menuItem.setArchived(false);
        }
    }
}