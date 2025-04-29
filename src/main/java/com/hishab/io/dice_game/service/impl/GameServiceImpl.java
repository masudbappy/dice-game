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
        logger.info("Player {} has joined the game.", player.getName());
        return player;
    }

    @Override
    public void startGame() {
        if (players.size() < 2) {
            throw new CustomException("ConstraintViolationException",
                    "At least 2 players are required to start the game.", HttpStatus.BAD_REQUEST);
        }
        if (gameStarted) {
            throw new CustomException("IllegalStateException",
                    "Game is already in progress.", HttpStatus.BAD_REQUEST);
        }
        if (!players.stream().noneMatch(this::hasWon)) {
            throw new CustomException("IllegalStateException",
                    "The previous game has already finished. Please reset the game to start a new one.", HttpStatus.BAD_REQUEST);
        }
        gameStarted = true;
        logger.info("The game has started with {} players.", players.size());
        playGame();
        logger.info("The game has ended.");
    }

    private void playGame() {
        int currentPlayerIndex = 0;
        while (players.stream().noneMatch(this::hasWon)) {
            Player currentPlayer = players.get(currentPlayerIndex);
            playTurn(currentPlayer);
            if (hasWon(currentPlayer)) {
                logger.info("Player {} has won with a score of {}!", currentPlayer.getName(), currentPlayer.getScore());
                break;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    private void playTurn(Player player) {
        int diceValue = diceApiClient.rollDice();
        logger.info("Player {} rolled a {}", player.getName(), diceValue);

        if (!player.isCanPlay()) {
            if (diceValue == 6) {
                player.setCanPlay(true);
                logger.info("Player {} rolled a 6 and can now start playing.", player.getName());
                int initialMove = diceApiClient.rollDice();
                logger.info("Player {}'s starting roll is {}", player.getName(), initialMove);
                if (initialMove != 6) {
                    player.setScore(initialMove);
                    player.setFirstSixRolled(true);
                    logger.info("Player {}'s score is now {}", player.getName(), player.getScore());
                } else {
                    logger.info("Player {} rolled a 6 on the starting roll, score remains 0.", player.getName());
                }
            } else {
                logger.info("Player {} rolled a {} and needs a 6 to start.", player.getName(), diceValue);
            }
        } else {
            if (player.isFirstSixRolled() && diceValue == 4) {
                player.setScore(player.getScore() - 4);
                logger.info("Player {} rolled a 4, score reduced to {}", player.getName(), player.getScore());
            } else if (diceValue == 6) {
                player.setScore(player.getScore() + diceValue);
                logger.info("Player {} rolled a 6, score increased to {} and gets an extra turn.", player.getName(), player.getScore());
                if (hasWon(player)) {
                    return;
                }
                playTurn(player); // Extra turn
            } else {
                player.setScore(player.getScore() + diceValue);
                logger.info("Player {} rolled a {}, score increased to {}", player.getName(), diceValue, player.getScore());
            }
        }
        if (diceValue == 6 && !player.isFirstSixRolled() && player.isCanPlay()) {
            player.setFirstSixRolled(true);
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
        logger.info("The game has been reset.");
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