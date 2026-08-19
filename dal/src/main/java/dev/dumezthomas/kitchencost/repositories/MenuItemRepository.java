package dev.dumezthomas.kitchencost.repositories;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    List<MenuItem> findAllByRestaurantIdAndArchivedFalse(UUID restaurantId);

    Optional<MenuItem> findByRestaurantIdAndId(UUID restaurantId, UUID menuItemId);

    boolean existsByRestaurantIdAndNameIgnoreCase(UUID restaurantId, String menuItemName);
}
