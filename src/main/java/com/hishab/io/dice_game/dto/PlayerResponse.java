package com.hishab.io.dice_game.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The type Player response.
 */
public record PlayerResponse(
        @Schema(description = "The name of the player", example = "Masud Bappy")
        String name,
        @Schema(description = "The current score of the player", example = "25")
        int score
) {}