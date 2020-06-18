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
        int[] cells = currentGame.getCells();
        cells[moveDto.getCellId()] = moveDto.getMoveEquivalent();
        String winner = findWinner(cells, moveDto.getUserId(), moveDto.getOpponentId());
        return new MoveResponseDto(winner, moveDto.getCellId(), moveDto.getUserId());
    }

    private Game findGameById(int gameId) {
        return gameRepository.findGameById(gameId);
    }


    private String findWinner(int[] cells, final String playerOne, final String playerTwo) {
        if (cells[0] + cells[1] + cells[2] == 30 ||
                cells[3] + cells[4] + cells[5] == 30 ||
                cells[6] + cells[7] + cells[8] == 30 ||
                cells[0] + cells[3] + cells[6] == 30 ||
                cells[1] + cells[4] + cells[7] == 30 ||
                cells[2] + cells[5] + cells[8] == 30 ||
                cells[0] + cells[4] + cells[8] == 30 ||
                cells[2] + cells[4] + cells[6] == 30) {
            return playerOne;
        }

        if (cells[0] + cells[1] + cells[2] == 3 ||
                cells[3] + cells[4] + cells[5] == 3 ||
                cells[6] + cells[7] + cells[8] == 3 ||
                cells[0] + cells[3] + cells[6] == 3 ||
                cells[1] + cells[4] + cells[7] == 3 ||
                cells[2] + cells[5] + cells[8] == 3 ||
                cells[0] + cells[4] + cells[8] == 3 ||
                cells[2] + cells[4] + cells[6] == 3) {
            return playerTwo;
        }

        return null;
    }
}
