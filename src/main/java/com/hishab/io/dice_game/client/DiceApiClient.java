package com.hishab.io.dice_game.client;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Random;

/**
 * The type Dice api client.
 */
@Component
public class DiceApiClient {

    /**
     * The Log.
     */
    Logger log = org.slf4j.LoggerFactory.getLogger(DiceApiClient.class);
    @Value("${dice.upstream.api.url}")
    private String upstreamUrl;

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    /**
     * Instantiates a new Dice api client.
     *
     * @param restTemplate the rest template
     */
    public DiceApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Roll dice int.
     *
     * @return the int
     */
    public int rollDice() {
        try {
            return Optional.ofNullable(restTemplate.getForEntity(upstreamUrl, Integer.class).getBody())
                    .orElse(getRandomDiceValue());
        } catch (Exception e) {
            log.error("Inner API Exception : {}", e.getMessage());
            return getRandomDiceValue();
        }
    }

    private int getRandomDiceValue() {
        return random.nextInt(6) + 1;
    }
}
