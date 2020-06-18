package com.game.tictactoe.repository;

import com.game.tictactoe.model.Game;

import java.util.List;

public interface GameRepository {
    List<Game> findAvailableGames();

    Game createGame(String player1, String tag);

    Game findGameById(int gameId);
}
