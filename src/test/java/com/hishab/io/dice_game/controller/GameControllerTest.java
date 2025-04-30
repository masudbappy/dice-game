package com.hishab.io.dice_game.controller;

import com.hishab.io.dice_game.dto.PlayerRequest;
import com.hishab.io.dice_game.dto.PlayerResponse;
import com.hishab.io.dice_game.exception.CustomException;
import com.hishab.io.dice_game.model.Player;
import com.hishab.io.dice_game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlayer_Success() {
        PlayerRequest request = new PlayerRequest("Masud", 25);
        PlayerResponse response = new PlayerResponse("Masud", 0);

        when(gameService.createPlayer(request.name(),
                request.age())).thenReturn(new Player(request.name(), request.age()));

        ResponseEntity<PlayerResponse> result = gameController.createPlayer(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response.name(), result.getBody().name());
        assertEquals(response.score(), result.getBody().score());
    }

    @Test
    void testCreatePlayer_Failure() {
        PlayerRequest request = new PlayerRequest("Masud", 25);

        when(gameService.createPlayer(request.name(), request.age()))
                .thenThrow(new CustomException("ConstraintViolationException", "Maximum 4 players are allowed.", HttpStatus.BAD_REQUEST));

        try {
            gameController.createPlayer(request);
        } catch (CustomException e) {
            assertEquals("Maximum 4 players are allowed.", e.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
        }
    }

    @Test
    void testStartGame_Success() {
        Map<String, Object> mockResponse = Map.of("winner", "Masud", "scores",
                List.of(new PlayerResponse("Masud", 25)));
        when(gameService.startGame()).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> result = gameController.startGame();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    void testResetGame_Success() {
        doNothing().when(gameService).resetGame();

        ResponseEntity<String> result = gameController.resetGame();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Game reset successfully!", result.getBody());
    }
}