package com.game.tictactoe.repository;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GameRepositoryImpl implements GameRepository {
    private List<Game> games = new ArrayList<>();

    @Override
    public List<Game> findAllGames() {
        return games;
    }

    @Override
    public Game createGame(Player player) {
        Game game = new Game();
        games.add(game);
        return game;
    }
}
