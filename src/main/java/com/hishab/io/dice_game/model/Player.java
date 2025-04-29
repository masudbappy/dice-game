package com.hishab.io.dice_game.model;

import java.util.Objects;

/**
 * The type Player.
 */
public class Player {
    private String name;
    private int age;
    private int score;
    private boolean canPlay;
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
