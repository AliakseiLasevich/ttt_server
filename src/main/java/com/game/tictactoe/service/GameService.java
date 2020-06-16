package com.game.tictactoe.service;

import com.game.tictactoe.model.Game;
import org.springframework.messaging.Message;

import java.util.List;

public interface GameService {
    List<Game> findAvailableGames();

    Game createGame(Message message);

    Game findPlayer1Game(Message message);
}
