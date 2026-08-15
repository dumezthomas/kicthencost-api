package dev.dumezthomas.kitchencost.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidOperationException extends KitchencostApiException {

    public InvalidOperationException(String message) {

        super(
                HttpStatus.BAD_REQUEST,
                message
        );
    }
}
