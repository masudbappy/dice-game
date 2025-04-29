package com.hishab.io.dice_game.controller;

import com.hishab.io.dice_game.dto.PlayerRequest;
import com.hishab.io.dice_game.dto.PlayerResponse;
import com.hishab.io.dice_game.exception.CustomException;
import com.hishab.io.dice_game.exception.ErrorResponse;
import com.hishab.io.dice_game.model.Player;
import com.hishab.io.dice_game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Player controller.
 */
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final GameService gameService;

    /**
     * Instantiates a new Player controller.
     *
     * @param gameService the game service
     */
    public PlayerController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Create player response entity.
     *
     * @param playerRequest the player request
     * @return the response entity
     */
    @Operation(summary = "Create a new player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created successfully",
                    content = @Content(schema = @Schema(implementation = PlayerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest playerRequest) {
        Player player = gameService.createPlayer(playerRequest.getName(), playerRequest.getAge());
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setName(player.getName());
        playerResponse.setScore(player.getScore());
        return new ResponseEntity<>(playerResponse, HttpStatus.CREATED);
    }

    /**
     * Start game response entity.
     *
     * @return the response entity
     */
    @Operation(summary = "Start the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game started successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - Not enough players",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/start")
    public ResponseEntity<String> startGame() {
        try {
            gameService.startGame();
            return ResponseEntity.ok("Game started successfully!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Gets current scores.
     *
     * @return the current scores
     */
    @Operation(summary = "Get the current scores of all players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved player scores",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlayerResponse.class))))
    })
    @GetMapping("/scores")
    public ResponseEntity<List<PlayerResponse>> getCurrentScores() {
        return ResponseEntity.ok(gameService.getCurrentScores());
    }

    /**
     * Reset game response entity.
     *
     * @return the response entity
     */
    @Operation(summary = "This will reset the game and all players' scores to 0. You can start a new game after this.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game reset successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - Game not started",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/reset")
    public ResponseEntity<String> resetGame() {
        gameService.resetGame();
        return ResponseEntity.ok("Game reset successfully!");
    }
}