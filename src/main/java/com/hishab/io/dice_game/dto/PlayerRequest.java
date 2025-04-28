package com.hishab.io.dice_game.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class PlayerRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Positive(message = "Age must be a positive number")
    private int age;

    public PlayerRequest() {
    }

    public PlayerRequest(String name, int age) {
        this.name = name;
        this.age = age;
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
}
