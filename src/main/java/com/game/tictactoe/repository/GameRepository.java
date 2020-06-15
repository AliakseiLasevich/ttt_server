package com.game.tictactoe.repository;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.model.Player;

import java.util.List;

public interface GameRepository {
    List<Game> findAllGames();

    Game createGame(Player player);
}
