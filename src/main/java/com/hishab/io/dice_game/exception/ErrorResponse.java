package com.hishab.io.dice_game.exception;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * The type Error response.
 */
public class ErrorResponse {
    @Schema(description = "The name of the exception", example = "ConstraintViolationException")
    private String exception;
    @Schema(description = "The message of the exception", example = "At least 2 players are required to start the game.")
    private String message;
    @Schema(description = "The HTTP status code", example = "400")
    private String status;
    @Schema(description = "The timestamp of the error", example = "2023-10-01T12:00:00Z")
    private Date timeStamp;
    @Schema(description = "The API path where the error occurred", example = "/api/v1/game/start")
    private String apiPath;

    /**
     * Instantiates a new Error response.
     */
    public ErrorResponse() {
    }

    /**
     * Instantiates a new Error response.
     *
     * @param exception the exception
     * @param message   the message
     * @param status    the status
     * @param timeStamp the time stamp
     * @param apiPath   the api path
     */
    public ErrorResponse(String exception, String message, String status, Date timeStamp, String apiPath) {
        this.exception = exception;
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
        this.apiPath = apiPath;
    }

    /**
     * Gets exception.
     *
     * @return the exception
     */
    public String getException() {
        return exception;
    }

    /**
     * Sets exception.
     *
     * @param exception the exception
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets time stamp.
     *
     * @param timeStamp the time stamp
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Gets api path.
     *
     * @return the api path
     */
    public String getApiPath() {
        return apiPath;
    }

    /**
     * Sets api path.
     *
     * @param apiPath the api path
     */
    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }
}