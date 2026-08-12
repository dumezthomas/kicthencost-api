package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.entities.Restaurant;
import dev.dumezthomas.kitchencost.repositories.RestaurantRepository;
import dev.dumezthomas.kitchencost.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findAll() {

        return restaurantRepository.findAll();
    }
}
