package com.hishab.io.dice_game.service;

import com.hishab.io.dice_game.client.DiceApiClient;
import com.hishab.io.dice_game.dto.PlayerResponse;
import com.hishab.io.dice_game.exception.CustomException;
import com.hishab.io.dice_game.model.Player;
import com.hishab.io.dice_game.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private DiceApiClient diceApiClient;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        gameService = new GameServiceImpl(diceApiClient);
        var winningScoreField = GameServiceImpl.class.getDeclaredField("winningScore");
        winningScoreField.setAccessible(true);
        winningScoreField.set(gameService, 25);
    }

    @Test
    void testCreatePlayer_Success() {
        Player player = gameService.createPlayer("Masud", 25);
        assertEquals("Masud", player.getName());
        assertEquals(25, player.getAge());
    }

    @Test
    void testCreatePlayer_MaxPlayersExceeded() {
        for (int i = 1; i <= 4; i++) {
            gameService.createPlayer("Player" + i, 20 + i);
        }
        CustomException exception = assertThrows(CustomException.class, () -> gameService.createPlayer("Player5", 30));
        assertEquals("Maximum 4 players are allowed.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testStartGame_InsufficientPlayers() {
        gameService.createPlayer("Masud", 25);
        CustomException exception = assertThrows(CustomException.class, gameService::startGame);
        assertEquals("At least 2 players are required to start the game.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testStartGame_Success() {
        gameService.createPlayer("Masud", 25);
        gameService.createPlayer("Bappy", 30);
        when(diceApiClient.rollDice()).thenReturn(6, 4, 5); // Mock dice rolls
        gameService.startGame();
        List<PlayerResponse> scores = gameService.getCurrentScores();
        assertEquals(2, scores.size());
    }

    @Test
    void testResetGame() {
        gameService.createPlayer("Masud", 25);
        gameService.createPlayer("Bappy", 30);
        when(diceApiClient.rollDice()).thenReturn(6, 4, 5);
        gameService.startGame();
        gameService.resetGame();
        List<PlayerResponse> scores = gameService.getCurrentScores();
        scores.forEach(playerResponse -> {
            assertEquals(0, playerResponse.score());
        });
    }
}