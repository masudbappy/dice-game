package com.hishab.io.dice_game.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

/**
 * The type Custom exception.
 */
public class CustomException extends RuntimeException {
    @Schema(description = "The name of the exception", example = "ConstraintViolationException")
    private final String exceptionName;
    @Schema(description = "The message of the exception", example = "At least 2 players are required to start the game.")
    private final String message;
    @Schema(description = "The HTTP status code", example = "400")
    private final HttpStatus httpStatus;

    /**
     * Instantiates a new Custom exception.
     *
     * @param exceptionName the exception name
     * @param message       the message
     * @param httpStatus    the http status
     */
    public CustomException(String exceptionName, String message, HttpStatus httpStatus) {
        super(message);
        this.exceptionName = exceptionName;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Gets exception name.
     *
     * @return the exception name
     */
    public String getExceptionName() {
        return exceptionName;
    }


    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Gets http status.
     *
     * @return the http status
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}