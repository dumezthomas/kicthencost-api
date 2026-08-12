package dev.dumezthomas.kitchencost.controllers.advisors;

import dev.dumezthomas.kitchencost.exceptions.KitchencostApiException;
import dev.dumezthomas.kitchencost.models.error.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({KitchencostApiException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(KitchencostApiException e) {

        ErrorResponse errorResponse = ErrorResponse.fromException(e);

        return ResponseEntity
                .status(errorResponse.status())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException e
    ) {

        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Objects.requireNonNullElse(
                                error.getDefaultMessage(),
                                "Validation failed."
                        ),
                        (m1, _) -> m1
                ));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errors
        );

        return ResponseEntity
                .status(errorResponse.status())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException() {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An error occurred."
        );

        return ResponseEntity
                .status(errorResponse.status())
                .body(errorResponse);
    }
}