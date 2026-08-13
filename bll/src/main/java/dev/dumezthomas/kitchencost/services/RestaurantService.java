package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.Restaurant;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {

    List<Restaurant> getAll();

    Restaurant getById(UUID restaurantId);
}
