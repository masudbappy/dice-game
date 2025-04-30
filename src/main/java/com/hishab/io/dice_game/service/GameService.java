package com.hishab.io.dice_game.service;

import com.hishab.io.dice_game.dto.PlayerResponse;
import com.hishab.io.dice_game.model.Player;

import java.util.List;
import java.util.Map;

/**
 * The interface Game service.
 */
public interface GameService {
    /**
     * Create player player.
     *
     * @param name the name
     * @param age  the age
     * @return the player
     */
    Player createPlayer(String name, int age);

    /**
     * Start game.
     *
     * @return
     */
    Map<String, Object> startGame();

    /**
     * Gets current scores.
     *
     * @return the current scores
     */
    List<PlayerResponse> getCurrentScores();

    /**
     * Reset game.
     */
    void resetGame();
}
