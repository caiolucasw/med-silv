package com.medsilveira.api.infra;

import java.util.List;

import org.springframework.http.HttpMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandleApiExceptions {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity handleException404(Exception e) {
    // Implement exception handling logic here
    // For example, log the exception and return a custom error response
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<DataErrorValidation>> handleException400(MethodArgumentNotValidException e) {

    List<DataErrorValidation> errors = e.getFieldErrors().stream()
        .map(DataErrorValidation::new)
        .toList();

    return ResponseEntity.badRequest().body(errors);
  }

  private record DataErrorValidation(String field, String message) {
    public DataErrorValidation(FieldError fieldError) {
      this(fieldError.getField(), fieldError.getDefaultMessage());
    }
  }
}
