package dev.dumezthomas.kitchencost.exceptions;

import dev.dumezthomas.kitchencost.enums.Resource;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends KitchencostApiException {

    public ResourceAlreadyExistsException(Resource resource, String name) {

        super(
                HttpStatus.CONFLICT,
                String.format("%s named '%s' already exists.", resource, name)
        );
    }
}
