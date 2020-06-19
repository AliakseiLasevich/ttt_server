package com.game.tictactoe.service;

import com.game.tictactoe.dto.*;
import com.game.tictactoe.model.Game;
import com.game.tictactoe.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
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
    public Game createGame(String playerOne, String gameName) {
        return gameRepository.createGame(playerOne, gameName);
    }

    @Override
    public Game joinGame(int gameId, String playerTwo) {
        Game game = gameRepository.findGameById(gameId);
        game.setPlayerTwo(playerTwo);
        game.setOpen(false);
        return game;
    }

    @Override
    public ResponseDto move(MoveDto moveDto) {
        Game currentGame = findGameById(moveDto.getGameId());
        int[] cells = currentGame.getCells();
        cells[moveDto.getCellId()] = moveDto.getMoveEquivalent();

        boolean isWinner = findWinner(cells);

        ResponseDto response = null;

        if (isWinner) {
            response = new WinnerDto(moveDto.getUserId());
        }
        if (!isWinner) {
            response = new MoveResponseDto(moveDto.getCellId(), moveDto.getUserId());
        }
        if (Arrays.stream(cells).allMatch(cell -> cell != 0)) {
            response = new TieResponseDto("Tie!");
        }

        return response;
    }

    private Game findGameById(int gameId) {
        return gameRepository.findGameById(gameId);
    }


    private boolean findWinner(int[] cells) {
        if (cells[0] == cells[1] && cells[1] == cells[2] && cells[1] != 0 ||
                cells[3] == cells[4] && cells[4] == cells[5] && cells[4] != 0 ||
                cells[6] == cells[7] && cells[7] == cells[8] && cells[7] != 0 ||
                cells[0] == cells[3] && cells[3] == cells[6] && cells[3] != 0 ||
                cells[1] == cells[4] && cells[4] == cells[7] && cells[4] != 0 ||
                cells[2] == cells[5] && cells[5] == cells[8] && cells[5] != 0 ||
                cells[0] == cells[4] && cells[4] == cells[8] && cells[4] != 0 ||
                cells[2] == cells[4] && cells[4] == cells[6] && cells[4] != 0) {
            return true;
        }
        return false;
    }
}
