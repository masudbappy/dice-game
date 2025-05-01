#   Dice Game Application

This is a simple board game application developed using Java 21 and Spring Boot 3.4.5

##   Requirements

* Java 21
* Gradle
* Docker

##   Features

* RESTful API for player management and game operations.
* Game logic implementing the specified rules.
* Exception handling.
* Logging of game events.
* API documentation using Swagger UI.
* Deodorization for easy deployment.

##   How to Build and Run

###   Prerequisites

* Java 21 JDK installed.
* Gradle installed.
* Docker installed.

###   Build

1.  Clone the repository.
2.  Navigate to the project directory.
3.  Run `./gradlew build` to build the application.
4.  The JAR file will be located in `build/libs`.

###   Run

####   Run with Gradle

1.  After building, run `./gradlew bootRun` to start the application.

####   Run with Docker

1.  **Pull Docker image**

    ```bash
    docker pull masudbappy/dice-game
    ```

2.  **Run the Docker container:**

    ```bash
    docker run -d -p 8081:8081 --name dice-game masudbappy/dice-game
    ```

The application will be accessible at `http://localhost:8081`.
##   API Documentation
API documentation is available at `http://localhost:8081/swagger-ui.html` after the application is running.

##   API Endpoints

* `POST /api/players`: Create a new player.
    * Request body:

        ```json
        {
          "name": "Player Name",
          "age": 25
        }
        ```

    * Response:

        ```json
        {
          "name": "Player Name",
          "score": 0
        }
        ```

* `POST /api/players/start`: Start the game.
* `GET /api/players/scores`: Retrieve current scores of all players.
    * Response:

        ```json
        [
          {
            "name": "Player 1",
            "score": 10
          },
          {
            "name": "Player 2",
            "score": 15
          }
        ]
        ```
##   Game Rules

* Maximum 4 players.
* Each player has a name and age.
* The first player to get a total sum of 25 (or more) is the winner. A player does not have to get 25 exactly (>= 25 is OK). The number 25 should be configurable.
* To get started, a player must roll a 6. Rolls of 1-5 result in waiting for their turn before having another go.
* When finally hitting the number 6, the player will have to throw again to determine the starting point. Getting a 6 on the first try will give you 0.
* Each time a player hits number 4, they will get -4 from the total score.
* If a player hits a 4 after hitting the first 6, they do not get a negative score but will have to roll another 6 before they start accumulating points.
* Each time a player hits the number 6, they will then get one extra throw.

##   Assumptions

* In-memory data storage is used for players and game state.
* The dice API (`http://developer-test.hishab.io/api/v1/roll-dice`) is assumed to be available and functioning as specified.
* Basic error handling and input validation are implemented.
* The game continues until the first player reaches the winning score; there is no handling of ties or multiple winners in the same turn.

##   Logging

The application logs game events to the console in the following format:
2025-05-01 19:20:28.889 [http-nio-8081-exec-2] ERROR c.h.i.dice_game.client.DiceApiClient - Inner API Exception :
I/O error on GET request for "http://developer-test.hishab.io/api/v1/roll-dice": developer-test.hishab.io