package com.game.tictactoe.service;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> findAvailableGames() {
        return gameRepository.findAvailableGames();
    }

    @Override
    public Game createGame(String playerOne, Message message) {
        log.info("new game. player one:" + playerOne);
        String payload = new String((byte[]) message.getPayload());
        String tag = payload.substring(1, payload.length() - 1);
        return gameRepository.createGame(playerOne, tag);
    }


    @Override
    public Game findPlayerOneGame(Message message) {
        String playerOne = (String) message.getHeaders().get("simpSessionId");
        return gameRepository.findPlayerOneGame(playerOne);
    }

    @Override
    public Game joinGame(Message msg, String playerTwo) {
        String payload = new String((byte[]) msg.getPayload());
        String playerOne = payload.substring(1, payload.length() - 1);
        Game game = gameRepository.findPlayerOneGame(playerOne);
        game.setPlayerTwo(playerTwo);
        game.setOpen(false);
        log.info("Game begins. Player one: " + playerOne + ", playerTwo: " + playerTwo);
        return game;
    }
}
