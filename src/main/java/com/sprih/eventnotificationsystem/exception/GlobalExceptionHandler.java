package com.sprih.eventnotificationsystem.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle @Valid field validation errors from DTOs
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return ResponseEntity.badRequest().body(errors);
	}

	// Handle unsupported event types
	@ExceptionHandler(UnsupportedEventTypeException.class)
	public ResponseEntity<Map<String, String>> handleUnsupportedEventType(UnsupportedEventTypeException ex) {
		return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
	}

	// Catch-all for any uncaught or runtime exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "Internal Server Error", "details", ex.getMessage()));
	}
}
