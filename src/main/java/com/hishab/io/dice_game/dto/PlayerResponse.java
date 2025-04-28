package com.hishab.io.dice_game.dto;

public class PlayerResponse {
    private String name;
    private int score;

    public PlayerResponse() {
    }
    public PlayerResponse(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}