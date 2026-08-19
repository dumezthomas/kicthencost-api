package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.Resource;
import dev.dumezthomas.kitchencost.exceptions.InvalidOperationException;
import dev.dumezthomas.kitchencost.exceptions.ResourceAlreadyExistsException;
import dev.dumezthomas.kitchencost.exceptions.ResourceNotFoundException;
import dev.dumezthomas.kitchencost.repositories.RestaurantRepository;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getAll() {

        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getById(UUID restaurantId) {

        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Resource.RESTAURANT,
                        restaurantId
                ));
    }

    @Transactional
    @Override
    public void update(UUID restaurantId, Restaurant restaurant) {

        Restaurant existing = getById(restaurantId);

        if (!existing.getName().equalsIgnoreCase(restaurant.getName())
                && restaurantRepository.existsByNameIgnoreCase(restaurant.getName())) {

            throw new ResourceAlreadyExistsException(
                    Resource.RESTAURANT,
                    restaurant.getName()
            );
        }

        validateFoodCostThresholds(
                restaurant.getTargetFoodCostPercentage(),
                restaurant.getWarningFoodCostPercentage(),
                restaurant.getCriticalFoodCostPercentage()
        );

        existing.setName(restaurant.getName());
        existing.setDescription(restaurant.getDescription());
        existing.setCuisineType(restaurant.getCuisineType());
        existing.setTargetFoodCostPercentage(restaurant.getTargetFoodCostPercentage());
        existing.setWarningFoodCostPercentage(restaurant.getWarningFoodCostPercentage());
        existing.setCriticalFoodCostPercentage(restaurant.getCriticalFoodCostPercentage());
    }

    private void validateFoodCostThresholds(BigDecimal target, BigDecimal warning, BigDecimal critical) {

        if (target.compareTo(warning) >= 0) {
            throw new InvalidOperationException(
                    "Target food cost percentage must be lower than warning food cost percentage."
            );
        }

        if (warning.compareTo(critical) >= 0) {
            throw new InvalidOperationException(
                    "Warning food cost percentage must be lower than critical food cost percentage."
            );
        }
    }
}
