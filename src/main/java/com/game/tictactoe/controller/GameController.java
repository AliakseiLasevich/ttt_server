package com.game.tictactoe.controller;

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

    @MessageMapping("/joinGame")
    @SendToUser("/queue/game")
    public void joinGame(Principal principal,
                         SimpMessageHeaderAccessor sha,
                         @Payload Message msg) {
        String playerTwo = principal.getName();
        Game game = gameService.joinGame(msg, playerTwo);
        String playerOne = game.getPlayerOne();

        simpMessagingTemplate.convertAndSendToUser(
                playerOne, "/user/queue/", "opponent id: "+playerTwo);
        simpMessagingTemplate.convertAndSendToUser(
                playerTwo, "/user/queue/", "opponent id: "+playerOne);

    }

//
//    @MessageMapping("/gameStart")
//    //    @SendTo("/topic/createGame")
//    public String gameStartNotifier() {
//        return "game starts";
//    }


}
