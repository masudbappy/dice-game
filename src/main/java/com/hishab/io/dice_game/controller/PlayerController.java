package com.hishab.io.dice_game.controller;

import com.hishab.io.dice_game.dto.PlayerRequest;
import com.hishab.io.dice_game.dto.PlayerResponse;
import com.hishab.io.dice_game.model.Player;
import com.hishab.io.dice_game.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final GameService gameService;

    public PlayerController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest playerRequest) {
        Player player = gameService.createPlayer(playerRequest.getName(), playerRequest.getAge());
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setName(player.getName());
        playerResponse.setScore(player.getScore());
        return new ResponseEntity<>(playerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/start")
    public ResponseEntity<String> startGame() {
        try {
            gameService.startGame();
            return ResponseEntity.ok("Game started successfully!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scores")
    public ResponseEntity<List<PlayerResponse>> getCurrentScores() {
        return ResponseEntity.ok(gameService.getCurrentScores());
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetGame() {
        gameService.resetGame();
        return ResponseEntity.ok("Game reset successfully!");
    }
}