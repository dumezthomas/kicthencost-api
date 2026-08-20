package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.MenuItem;

import java.util.List;
import java.util.UUID;

public interface MenuItemService {

    List<MenuItem> getAll(UUID restaurantId);

    List<MenuItem> getAllActive(UUID restaurantId);

    MenuItem getById(UUID restaurantId, UUID menuItemId);

    UUID create(UUID restaurantId, MenuItem menuItem);

    void update(UUID restaurantId, UUID menuItemId, MenuItem menuItem);

    void archive(UUID restaurantId, UUID menuItemId);

    void restore(UUID restaurantId, UUID menuItemId);
}
