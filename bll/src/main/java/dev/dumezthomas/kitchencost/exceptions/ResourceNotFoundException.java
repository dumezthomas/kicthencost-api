package dev.dumezthomas.kitchencost.exceptions;

import dev.dumezthomas.kitchencost.enums.Resource;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ResourceNotFoundException extends KitchencostApiException {

    public ResourceNotFoundException(Resource resource, UUID id) {

        super(
                HttpStatus.NOT_FOUND,
                String.format("%s with id '%s' was not found.", resource, id)
        );
    }
}
