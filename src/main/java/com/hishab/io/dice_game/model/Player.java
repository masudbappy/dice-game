package com.hishab.io.dice_game.model;

import java.util.Objects;

public class Player {
    private String name;
    private int age;
    private int score;
    private boolean canPlay;
    private boolean firstSixRolled;

    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        this.score = 0;
        this.canPlay = false;
        this.firstSixRolled = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean isFirstSixRolled() {
        return firstSixRolled;
    }

    public void setFirstSixRolled(boolean firstSixRolled) {
        this.firstSixRolled = firstSixRolled;
    }

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
