package com.hishab.io.dice_game.service;

import com.hishab.io.dice_game.dto.PlayerResponse;
import com.hishab.io.dice_game.model.Player;

import java.util.List;

public interface GameService {
    Player createPlayer(String name, int age);

    void startGame();

    List<PlayerResponse> getCurrentScores();

    void resetGame();
}
