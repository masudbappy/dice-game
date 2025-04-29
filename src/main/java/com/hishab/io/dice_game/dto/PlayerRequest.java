package com.hishab.io.dice_game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * The type Player request.
 */
public class PlayerRequest {
    @NotBlank(message = "Name is mandatory")
    @Schema(description = "The name of the player", example = "Masud Bappy")
    private String name;

    @Positive(message = "Age must be a positive number")
    @Schema(description = "The age of the player", example = "30")
    private int age;

    /**
     * Instantiates a new Player request.
     */
    public PlayerRequest() {
    }

    /**
     * Instantiates a new Player request.
     *
     * @param name the name
     * @param age  the age
     */
    public PlayerRequest(String name, int age) {
        this.name = name;
        this.age = age;
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
}
