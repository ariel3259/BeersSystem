package com.ariel.BeersApi.Controllers;

import com.ariel.BeersApi.Exceptions.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((x) -> {
            String fieldName = ((FieldError)x).getField();
            String message = x.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<Map<String, String>> handleHttpException(HttpException httpException) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Message", httpException.getMessage());
        return ResponseEntity.status(httpException.getStatusCode()).body(errors);
    }
}
