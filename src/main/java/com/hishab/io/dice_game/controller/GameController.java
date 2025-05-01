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
import java.util.Map;

/**
 * The type Game controller.
 * This controller handles all game-related API endpoints.
 */
@RestController
@RequestMapping("/api/v1/dice")
public class GameController {

    private final GameService gameService;

    /**
     * Instantiates a new Player controller.
     *
     * @param gameService the game service
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new player and adds them to the game.
     *
     * @param playerRequest the player request containing name and age
     * @return the response entity containing the created player's details
     */
    @Operation(summary = "Create a new player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created successfully",
                    content = @Content(schema = @Schema(implementation = PlayerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/create/player")
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest playerRequest) {
        Player player = gameService.createPlayer(playerRequest.name(), playerRequest.age());
        PlayerResponse playerResponse = new PlayerResponse(player.getName(), player.getScore());
        return new ResponseEntity<>(playerResponse, HttpStatus.CREATED);
    }

    /**
     * Starts the game if the conditions are met.
     *
     * @return the response entity containing the winner's name and current scores
     */
    @Operation(summary = "Start the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game started successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Not enough players",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startGame() {
        try {
            Map<String, Object> response = gameService.startGame();
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            throw new CustomException("GamePlayException",
                    "Failed to start game", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the current scores of all players.
     *
     * @return the response entity containing a list of player scores
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
     * Resets the game and clears all player states.
     *
     * @return the response entity confirming the game reset
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