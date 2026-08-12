package dev.dumezthomas.kitchencost.models.error.responses;

import dev.dumezthomas.kitchencost.exceptions.KitchencostApiException;
import org.springframework.http.HttpStatus;

public record ErrorResponse(

        HttpStatus status,
        Object error
) {

    public static ErrorResponse fromException(KitchencostApiException e) {

        return new ErrorResponse(e.getStatus(), e.getError());
    }
}