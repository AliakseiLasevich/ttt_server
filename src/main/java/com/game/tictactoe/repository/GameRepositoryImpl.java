package com.game.tictactoe.repository;

import com.game.tictactoe.model.Game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class GameRepositoryImpl implements GameRepository {

    private long id = 0;
    private List<Game> games = new ArrayList<>();

    @Override
    public List<Game> findAvailableGames() {
        return games.stream()
                .filter(Game::isOpen)
                .collect(Collectors.toList());
    }

    @Override
    public Game createGame(String player1, String tag) {
        Game game = new Game(id, player1, tag);
        id++;
        games.add(game);
        return game;
    }

    @Override
    public Game findGameById(int gameId) {
        Game g= games.get(gameId);
        return games.get(gameId);
    }


}
