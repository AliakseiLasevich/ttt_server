package com.game.tictactoe.service;

import com.game.tictactoe.dto.MoveDto;
import com.game.tictactoe.dto.ResponseDto;
import com.game.tictactoe.model.Game;

import java.util.List;

public interface GameService {
    List<Game> findAvailableGames();

    Game createGame(String playerOne, String gameName);

    Game joinGame(int msg, String playerTwo);

    ResponseDto move(MoveDto moveDto);


}
