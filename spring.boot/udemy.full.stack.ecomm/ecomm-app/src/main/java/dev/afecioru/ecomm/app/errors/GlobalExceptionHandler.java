package dev.afecioru.ecomm.app.errors;

import dev.afecioru.ecomm.core.Error.InvalidOperationError;
import dev.afecioru.ecomm.core.Error.NotFoundError;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        val response = e.getBindingResult().getFieldErrors().stream()
            .map(error -> Map.entry(error.getField(), Optional.ofNullable(error.getDefaultMessage()).orElse("is invalid")))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (existing, replacement) -> existing + " AND " + replacement
            ));

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundError.class)
    public ResponseEntity<String> handleNotFound(NotFoundError e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidOperationError.class)
    public ResponseEntity<String> handleInvalidOperation(InvalidOperationError e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
