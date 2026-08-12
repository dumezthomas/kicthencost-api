package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> findAll();
}
