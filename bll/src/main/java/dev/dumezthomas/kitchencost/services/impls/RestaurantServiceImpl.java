package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.enums.Resource;
import dev.dumezthomas.kitchencost.exceptions.ResourceNotFoundException;
import dev.dumezthomas.kitchencost.repositories.RestaurantRepository;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
