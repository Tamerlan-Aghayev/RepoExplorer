package com.company.repoexplorer.exception;

import com.company.repoexplorer.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.exception(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ApiResponse<Object>> handleRateLimitException(RateLimitException ex) {
        ApiResponse<Object> response = ApiResponse.exception(HttpStatus.TOO_MANY_REQUESTS.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<Object> response = ApiResponse.exception(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
