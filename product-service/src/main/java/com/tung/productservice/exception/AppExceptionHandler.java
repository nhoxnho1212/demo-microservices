package com.tung.productservice.exception;

import com.tung.productservice.payload.response.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = { DatabaseException.class })
    public ResponseEntity<Object> handleDatabaseException(DatabaseException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                Boolean.FALSE,
                exception.getErrorCode().name(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), exception.getErrorCode().getStatusCode());
    }

    @ExceptionHandler(value = { SearchException.class })
    public ResponseEntity<Object> handleSearchException(SearchException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                Boolean.FALSE,
                exception.getErrorCode().name(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), exception.getErrorCode().getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorDetails errorDetails = new ErrorDetails(
                Boolean.FALSE,
                ServiceError.FILTER_INVALID_FIELD_ERROR.name(),
                errors
        );
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), ServiceError.FILTER_INVALID_FIELD_ERROR.getStatusCode());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                Boolean.FALSE,
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
