package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.results.RestaurantAnalysis;

import java.util.UUID;

public interface RestaurantAnalysisService {

    RestaurantAnalysis analyze(UUID restaurantId);
}
