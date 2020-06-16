package com.game.tictactoe.repository;

import com.game.tictactoe.model.Game;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GameRepositoryImpl implements GameRepository {
    private List<Game> games = new ArrayList<>();

    @Override
    public List<Game> findAvailableGames() {
//        for (int i = 0; i < 5; i++) {
//            Game game = new Game();
//            game.setTag("game #" + i);
//            games.add(game);
//        }

        return games.stream()
                .filter(game -> game.getPlayerTwoSession() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Game createGame(String player1, String tag) {
        Game game = new Game(player1, tag);
        games.add(game);
        return game;
    }

    @Override
    public Game findPlayer1Game(String playerOneSession) {
         return  games.stream()
                 .filter(game -> game.getPlayerOneSession().equals(playerOneSession))
                 .reduce((u, v) -> { throw new IllegalStateException("More than one ID found");})
                 .get();
    }
}
