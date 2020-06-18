package com.game.tictactoe.service;

import com.game.tictactoe.dto.MoveDto;
import com.game.tictactoe.dto.MoveResponseDto;
import com.game.tictactoe.model.Game;
import com.game.tictactoe.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Game createGame(String playerOne, String tag) {
        log.info("new game. player one:" + playerOne);
        return gameRepository.createGame(playerOne, tag);
    }


    @Override
    public Game joinGame(int gameId, String playerTwo) {
        Game game = gameRepository.findGameById(gameId);
        game.setPlayerTwo(playerTwo);
        game.setOpen(false);
        log.info("Game begins. Player one: " + game.getPlayerOne() + ", playerTwo: " + game.getPlayerTwo());
        return game;
    }

    @Override
    public MoveResponseDto move(MoveDto moveDto) {
        Game currentGame = findGameById(moveDto.getGameId());

        return null;
    }

    private Game findGameById(int gameId) {
        return gameRepository.findGameById(gameId);
    }

}
