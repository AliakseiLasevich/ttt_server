package com.game.tictactoe.service;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> findAvailableGames() {
        return gameRepository.findAvailableGames();
    }

    @Override
    public Game createGame(Message message) {
        String player1 = (String) message.getHeaders().get("simpSessionId");
        String payload = new String((byte[]) message.getPayload());
        String tag = payload.substring(1, payload.length() - 1);
        return gameRepository.createGame(player1, tag);
    }


    @Override
    public Game findPlayer1Game(Message message) {
        String playerOne = (String) message.getHeaders().get("simpSessionId");
        return gameRepository.findPlayer1Game(playerOne);
    }
}
