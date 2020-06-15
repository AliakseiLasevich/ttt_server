package com.game.tictactoe.service;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.model.Player;

import java.util.List;

public interface GameService {
    List<Game> findAllGames();

    Game createGame(Player player);

    void joinGame(Player player);
}
