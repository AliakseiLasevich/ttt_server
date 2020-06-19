package com.game.tictactoe.controller;

import com.game.tictactoe.dto.*;
import com.game.tictactoe.model.Game;
import com.game.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return gameService.findAvailableGames();
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
        notifyOpponent(playerOne, playerTwo);
    }

    private void notifyOpponent(String playerOne, String playerTwo){
        simpMessagingTemplate.convertAndSendToUser(playerOne, "/queue/opponent", playerTwo);
        simpMessagingTemplate.convertAndSendToUser(playerTwo, "/queue/opponent", playerOne);
    }


    @MessageMapping("/move")
    public void move(MoveDto moveDto) {
        ResponseDto response = gameService.move(moveDto);
        simpMessagingTemplate.convertAndSendToUser(
                moveDto.getOpponentId(), "/queue/move", response);
        pickMoveResponse(response, moveDto);
    }

    private void pickMoveResponse(ResponseDto response, MoveDto moveDto) {
        if (response instanceof WinnerDto) {
            passGameOverMessage(moveDto.getUserId(), (WinnerDto) response);
            passToOpponentMove(moveDto);
            passGameOverMessage(moveDto.getOpponentId(), (WinnerDto) response);
        }
        if (response instanceof TieResponseDto) {
            simpMessagingTemplate.convertAndSendToUser(moveDto.getUserId(), "/queue/tie", response);
            passToOpponentMove(moveDto);
            simpMessagingTemplate.convertAndSendToUser(moveDto.getOpponentId(), "/queue/tie", new TieResponseDto());
        }
    }

    private void passToOpponentMove(MoveDto moveDto) {
        simpMessagingTemplate.convertAndSendToUser(moveDto.getOpponentId(), "/queue/move", new MoveResponseDto(moveDto.getCellId(), moveDto.getOpponentId()));
    }

    private void passGameOverMessage(String userId, WinnerDto winnerDto) {
        simpMessagingTemplate.convertAndSendToUser(userId, "/queue/gameOver", winnerDto);
    }


}
