package com.hishab.io.dice_game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * The type Player request.
 */
public record PlayerRequest(
        @NotBlank(message = "Name is mandatory")
        @Schema(description = "The name of the player", example = "Masud Bappy")
        String name,

        @Positive(message = "Age must be a positive number")
        @Schema(description = "The age of the player", example = "30")
        int age
) {}
