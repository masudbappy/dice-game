package com.hishab.io.dice_game.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * The type Player.
 */
public class Player {
    @Schema(description = "The name of the player", example = "Masud Bappy")
    private String name;
    @Schema(description = "The age of the player", example = "30")
    private int age;
    @Schema(description = "The current score of the player", example = "25")
    private int score;
    @Schema(description = "Indicates if the player can play", example = "true")
    private boolean canPlay;
    @Schema(description = "Indicates if the player has rolled a six", example = "false")
    private boolean firstSixRolled;

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     * @param age  the age
     */
    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        this.score = 0;
        this.canPlay = false;
        this.firstSixRolled = false;
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
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
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
        score = Math.max(0, score); // Ensure score is not negative
    }

    /**
     * Is can play boolean.
     *
     * @return the boolean
     */
    public boolean isCanPlay() {
        return canPlay;
    }

    /**
     * Sets can play.
     *
     * @param canPlay the can play
     */
    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    /**
     * Is first six rolled boolean.
     *
     * @return the boolean
     */
    public boolean isFirstSixRolled() {
        return firstSixRolled;
    }

    /**
     * Sets first six rolled.
     *
     * @param firstSixRolled the first six rolled
     */
    public void setFirstSixRolled(boolean firstSixRolled) {
        this.firstSixRolled = firstSixRolled;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.score = 0;
        this.canPlay = false;
        this.firstSixRolled = false;
    }

    @Override
    public boolean equals(Object player) {
        if (this == player) return true;
        if (player == null || getClass() != player.getClass()) return false;
        return ((Player) player).getName().equals(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, score, canPlay, firstSixRolled);
    }
}
