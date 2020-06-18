package com.game.tictactoe.controller;

import com.game.tictactoe.dto.MoveDto;
import com.game.tictactoe.model.Game;
import com.game.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
    public List<Game> availableGames(@Payload Message msg,
                                     @Header("simpSessionId") String sessionId) {
        return gameService.findAvailableGames();
    }

    @MessageMapping("/createGame")
    @SendTo("/topic/createGame")
    public Game createGame(Principal principal,
                           @Payload Message msg) {
        return gameService.createGame(principal.getName(), msg);
    }


    @MessageMapping("/getSessionId")
    @SendToUser("/queue/sessionId")
    public String getSessionId(Principal principal) {
        return principal.getName();
    }

    @MessageMapping("/joinGame")
    public void joinGame(Principal principal,
                         SimpMessageHeaderAccessor sha,
                         @Payload Message msg) {
        String playerTwo = principal.getName();
        Game game = gameService.joinGame(msg, playerTwo);
        String playerOne = game.getPlayerOne();

        //need to use queue for messaging to specific user
        simpMessagingTemplate.convertAndSendToUser(
                playerOne, "/queue/opponent", playerTwo);

        simpMessagingTemplate.convertAndSendToUser(
                playerTwo, "/queue/opponent", playerOne);
    }


    @MessageMapping("/move")
    public  void move (MoveDto moveDto, Principal principal) {

        simpMessagingTemplate.convertAndSendToUser(
                moveDto.getUserId(), "/queue/move", "move user");
        simpMessagingTemplate.convertAndSendToUser(
                moveDto.getOpponentId(), "/queue/move", "move opponent");


    }
}
