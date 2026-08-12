package dev.dumezthomas.kitchencost.seeds;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.CuisineType;
import dev.dumezthomas.kitchencost.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestaurantSeeder {

    private final RestaurantRepository restaurantRepository;

    public void seed() {

        if (restaurantRepository.count() > 0) {
            return;
        }

        restaurantRepository.saveAll(List.of(

                createRestaurant(
                        "Le Petit Chef",
                        "Traditional French cuisine.",
                        CuisineType.FRENCH,
                        BigDecimal.valueOf(30),
                        BigDecimal.valueOf(35),
                        BigDecimal.valueOf(40)
                ),

                createRestaurant(
                        "Bella Napoli",
                        "Authentic Italian cuisine.",
                        CuisineType.ITALIAN,
                        BigDecimal.valueOf(28),
                        BigDecimal.valueOf(33),
                        BigDecimal.valueOf(38)
                ),

                createRestaurant(
                        "Sakura",
                        "Traditional Japanese cuisine.",
                        CuisineType.JAPANESE,
                        BigDecimal.valueOf(32),
                        BigDecimal.valueOf(37),
                        BigDecimal.valueOf(42)
                )
        ));

        log.info("Seeded {} restaurants.", restaurantRepository.count());
    }

    private Restaurant createRestaurant(
            String name,
            String description,
            CuisineType cuisineType,
            BigDecimal targetFoodCostPercentage,
            BigDecimal warningFoodCostPercentage,
            BigDecimal criticalFoodCostPercentage
    ) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(name);
        restaurant.setDescription(description);
        restaurant.setCuisineType(cuisineType);
        restaurant.setTargetFoodCostPercentage(targetFoodCostPercentage);
        restaurant.setWarningFoodCostPercentage(warningFoodCostPercentage);
        restaurant.setCriticalFoodCostPercentage(criticalFoodCostPercentage);

        return restaurant;
    }
}