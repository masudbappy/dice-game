package com.hishab.io.dice_game.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The type Player response.
 */
public class PlayerResponse {
    @Schema(description = "The name of the player", example = "Masud Bappy")
    private String name;
    @Schema(description = "The current score of the player", example = "25")
    private int score;

    /**
     * Instantiates a new Player response.
     */
    public PlayerResponse() {
    }

    /**
     * Instantiates a new Player response.
     *
     * @param name  the name
     * @param score the score
     */
    public PlayerResponse(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }
}