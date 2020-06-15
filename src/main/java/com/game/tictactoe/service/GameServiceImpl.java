package com.game.tictactoe.service;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.model.Player;
import com.game.tictactoe.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAllGames();
    }

    @Override
    public Game createGame(Player player) {
        return gameRepository.createGame(player);
    }

    @Override
    public void joinGame(Player player) {

    }
}
