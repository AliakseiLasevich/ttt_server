package com.game.tictactoe.controller;

import com.game.tictactoe.dto.*;
import com.game.tictactoe.model.Game;
import com.game.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/findGames")
    @SendTo("/topic/findGames")
    public List<Game> availableGames() {
        List<Game> l =  gameService.findAvailableGames();
        return l;
    }

    @MessageMapping("/createGame")
    @SendTo("/topic/createGame")
    public Game createGame(Principal principal,
                           @Payload String gameName) {
        return gameService.createGame(principal.getName(), gameName);
    }


    @MessageMapping("/getSessionId")
    @SendToUser("/queue/sessionId")
    public String getSessionId(Principal principal) {
        return principal.getName();
    }

    @MessageMapping("/joinGame")
    public void joinGame(Principal principal, @Payload int gameId) {
        String playerTwo = principal.getName();
        Game game = gameService.joinGame(gameId, playerTwo);
        String playerOne = game.getPlayerOne();
        simpMessagingTemplate.convertAndSendToUser(playerOne, "/queue/opponent", playerTwo);
        simpMessagingTemplate.convertAndSendToUser(playerTwo, "/queue/opponent", playerOne);
    }


    @MessageMapping("/move")
    public void move(MoveDto moveDto) {
        ResponseDto response = gameService.move(moveDto);
        simpMessagingTemplate.convertAndSendToUser(
                moveDto.getOpponentId(), "/queue/move", response);

        if (response instanceof WinnerDto) {
            simpMessagingTemplate.convertAndSendToUser(moveDto.getUserId(), "/queue/gameOver", response);
            simpMessagingTemplate.convertAndSendToUser(moveDto.getOpponentId(), "/queue/move", new MoveResponseDto(moveDto.getCellId(), moveDto.getOpponentId()));
            simpMessagingTemplate.convertAndSendToUser(moveDto.getOpponentId(), "/queue/gameOver", response);
        }

        if (response instanceof TieResponseDto) {
            simpMessagingTemplate.convertAndSendToUser(moveDto.getUserId(), "/queue/tie", response);
            simpMessagingTemplate.convertAndSendToUser(moveDto.getOpponentId(), "/queue/move", new MoveResponseDto(moveDto.getCellId(), moveDto.getOpponentId()));
            simpMessagingTemplate.convertAndSendToUser(moveDto.getOpponentId(), "/queue/tie", new TieResponseDto());
        }
    }
}
