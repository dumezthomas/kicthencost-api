package dev.dumezthomas.kitchencost.repositories;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
