package dev.dumezthomas.kitchencost.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public abstract class KitchencostApiException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    @Getter
    private final Object error;
}
