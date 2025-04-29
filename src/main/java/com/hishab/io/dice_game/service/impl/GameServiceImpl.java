package com.hishab.io.dice_game.service.impl;

import com.hishab.io.dice_game.client.DiceApiClient;
import com.hishab.io.dice_game.dto.PlayerResponse;
import com.hishab.io.dice_game.exception.CustomException;
import com.hishab.io.dice_game.model.Player;
import com.hishab.io.dice_game.service.GameService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Game service.
 */
@Service
public class GameServiceImpl implements GameService {

    @Value("${dice.game.winning-score}")
    private int winningScore;
    private final List<Player> players = new ArrayList<>();
    private final DiceApiClient diceApiClient;
    private final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private boolean gameStarted = false;

    /**
     * Instantiates a new Game service.
     *
     * @param diceApiClient the dice api client
     */
    public GameServiceImpl(DiceApiClient diceApiClient) {
        this.diceApiClient = diceApiClient;
    }

    @Override
    public Player createPlayer(String name, int age) {
        if (players.size() >= 4) {
            throw new CustomException("ConstraintViolationException",
                    "Maximum 4 players are allowed.", HttpStatus.BAD_REQUEST);
        }
        Player player = new Player(name, age);
        if (players.contains(player)) {
            throw new CustomException("IllegalStateException",
                    "Player with this name already exists.", HttpStatus.BAD_REQUEST);
        }
        players.add(player);
        logger.info("Player created: {}", player.getName());
        return player;
    }

    @Override
    public void startGame() {
        if (players.size() < 2) {
            throw new CustomException("ConstraintViolationException",
                    "At least 2 players are required to start the game.", HttpStatus.BAD_REQUEST);
        }
        if (!players.stream().noneMatch(this::hasWon)) {
            throw new CustomException("IllegalStateException",
                    "Game is already finished. Please start a new game.", HttpStatus.BAD_REQUEST);
        }
        gameStarted = true;
        playGame();
    }

    private void playGame() {
        int currentPlayerIndex = 0;
        while (players.stream().noneMatch(this::hasWon)) {
            Player currentPlayer = players.get(currentPlayerIndex);
            playTurn(currentPlayer);
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    private void playTurn(Player player) {
        int diceValue = diceApiClient.rollDice();
        logger.info("Player name: {}, Total Score: {}, Current Value of Dice: {}", player.getName(), player.getScore(), diceValue);

        if (!player.isCanPlay()) {
            if (diceValue == 6) {
                player.setCanPlay(true);
                logger.info("Congratulations! Player {} rolled a 6 and can now play.", player.getName());
                int initialMove = diceApiClient.rollDice();
                logger.info("Player name: {}, Total Score: {}, Current Value of Dice: {}", player.getName(), player.getScore(), initialMove);
                if (initialMove != 6) {
                    player.setScore(initialMove);
                    player.setFirstSixRolled(true);
                }
            }
        } else {
            if (player.isFirstSixRolled() && diceValue == 4) {
                player.setScore(player.getScore() - 4);
            } else if (diceValue == 6) {
                player.setScore(player.getScore() + diceValue);
                playTurn(player); // Extra turn
            } else {
                player.setScore(player.getScore() + diceValue);
            }
            if (diceValue == 6) {
                player.setFirstSixRolled(true);
            }
        }
    }

    @Override
    public List<PlayerResponse> getCurrentScores() {
        return players.stream().map(this::convertToResponse).toList();
    }

    @Override
    public void resetGame() {
        players.forEach(Player::reset);
        gameStarted = false;
    }

    private PlayerResponse convertToResponse(Player player) {
        PlayerResponse response = new PlayerResponse();
        response.setName(player.getName());
        response.setScore(player.getScore());
        return response;
    }

    private boolean hasWon(Player player) {
        return player.getScore() >= winningScore;
    }
}