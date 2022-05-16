# Websockets game server

The websocket server is reachable at the url : `https://api.{{ environemnt }}.prizy.io/websockets`

Note : `environemnt` is the environment name, e.g. `dev`, `stg`, and none for `prod`

### Game flow

#### Important note :

All the messages sent to the server have the following format :

  ```json
  {
  "type": "MESSAGE_TYPE"
  // ... fill with other fields
}
  ``` 

#### Important note 2 :

Client must connect to the websocket server with an `Authorization` header first

###  

* `Client` starts the game
  ```json
  {
    "type": "game_started",
    "contestId": "UUID",
    "diamonds": 1234 // bet value
  }
  ```

* `Server` sends board layout details (possible positions, obstacles ...)
  ```json
    {
      "type": "board_retrieved",
      "board": {
        "id": "UUID",
        "name": "Board name",
        "rowSize": 123,
        "cells": [1, 1, 0, 1, 0, 1] // matrix of 'rowSize' sized lines, where 1 means a cell is available for moves
                                    // and 0 means cell is not available for moves
      },
      "startPosition": 123, // index of start position
      "endPosition": 321, // index of end position
      "obstacles": [123, 321, ...] // list of cells marked as obstacles
    }
  ```

* `Client` sends move event on each move
  ```json
  {
    "type": "player_moved",
    "direction": "down" // possible values : up, down, left, right, up_right, up_left, down_right, down_left
  }
  ```

* `Server` sends new score if the last move was successful (no bomb found)
  ```json
  {
    "type": "score_updated",
    "score": 123
  }
  ```


* `Server` sends game won if the last move did get the player to the goal
  ```json
  {
    "type": "game_won",
    "score": 123,
    "obstacles": [1, 2, 3] // list of cells marked as obstacles
  }
  ```

* `Server` sends game over if the last move did get the player to step on a bomb
  ```json
  {
    "type": "game_lost",
    "obstacles": [1, 2, 3] // list of cells marked as obstacles
  }
  ```