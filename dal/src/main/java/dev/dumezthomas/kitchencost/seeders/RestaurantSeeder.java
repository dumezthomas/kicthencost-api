package dev.dumezthomas.kitchencost.seeders;

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

        List<Restaurant> restaurants = List.of(

                new Restaurant(
                        "Le Petit Chef",
                        "Traditional French cuisine.",
                        CuisineType.FRENCH,
                        BigDecimal.valueOf(30),
                        BigDecimal.valueOf(35),
                        BigDecimal.valueOf(40)
                ),

                new Restaurant(
                        "Bella Napoli",
                        "Authentic Italian cuisine.",
                        CuisineType.ITALIAN,
                        BigDecimal.valueOf(28),
                        BigDecimal.valueOf(33),
                        BigDecimal.valueOf(38)
                ),

                new Restaurant(
                        "Sakura",
                        "Traditional Japanese cuisine.",
                        CuisineType.JAPANESE,
                        BigDecimal.valueOf(32),
                        BigDecimal.valueOf(37),
                        BigDecimal.valueOf(42)
                )
        );

        restaurantRepository.saveAll(restaurants);

        log.info("Seeded {} restaurants.", restaurants.size());
    }
}