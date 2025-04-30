package com.hishab.io.dice_game.exception;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * The type Error response.
 */
public record ErrorResponse(
        @Schema(description = "The name of the exception", example = "ConstraintViolationException")
        String exception,
        @Schema(description = "The message of the exception", example = "At least 2 players are required to start the game.")
        String message,
        @Schema(description = "The HTTP status code", example = "400")
        String status,
        @Schema(description = "The timestamp of the error", example = "2023-10-01T12:00:00Z")
        Date timeStamp,
        @Schema(description = "The API path where the error occurred", example = "/api/v1/game/start")
        String apiPath
) {}