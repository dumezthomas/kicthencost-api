package dev.dumezthomas.kitchencost.seeds;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final RestaurantSeeder restaurantSeeder;

    @Override
    public void run(String... args) {

        log.info("Seeding database...");

        restaurantSeeder.seed();

        log.info("Database seeded.");
    }
}
