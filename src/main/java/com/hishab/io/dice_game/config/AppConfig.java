package com.hishab.io.dice_game.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * The type App config.
 */
@Configuration
public class AppConfig {

    @Value("${dice.upstream.connection-time-out}")
    private int connectionTimeout;

    @Value("${dice.upstream.read-time-out}")
    private int readTimeout;


    /**
     * Rest template rest template.
     *
     * @param builder the builder
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .requestFactory(this::simpleClientHttpRequestFactory)
                .build();
    }
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    private ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectionTimeout)); // Connection timeout
        factory.setReadTimeout(Duration.ofMillis(readTimeout)); // Read timeout
        return factory;
    }
}
