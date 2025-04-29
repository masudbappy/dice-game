
package com.hishab.io.dice_game.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e, HttpServletRequest request) {
        logger.error("Exception occurred at URI: {} - Message: {}", request.getRequestURI(), e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "An error occurred while performing a database operation",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                new Date(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e, HttpServletRequest request) {
        logger.warn("IllegalArgumentException at URI: {} - Message: {}", request.getRequestURI(), e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "Invalid argument provided",
                HttpStatus.BAD_REQUEST.toString(),
                new Date(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception e, HttpServletRequest request) {
        Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
        String errorMessage = violations.stream()
                .map(violation -> String.format("%s", violation.getMessage()))
                .collect(Collectors.joining(", "));
        logger.info("Validation error at URI: {} - Violations: {}", request.getRequestURI(), errorMessage);
        ErrorResponse errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                errorMessage,
                HttpStatus.BAD_REQUEST.toString(),
                new Date(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        logger.error("CustomException occurred at URI: {} - Name: {} - Message: {}", request.getRequestURI(), e.getExceptionName(), e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                e.getExceptionName(),
                e.getMessage(),
                e.getHttpStatus().toString(),
                new Date(),
                request.getRequestURI()
        );
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }
}