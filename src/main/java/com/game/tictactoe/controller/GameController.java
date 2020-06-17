package com.game.tictactoe.controller;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

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
    public Game createGame(@Payload Message msg,
                           @Header("simpSessionId") String sessionId) {
        return gameService.createGame(msg);
    }

    @MessageMapping("/joinGame")
//    @SendTo("/topic/findGames")
    public void joinGame(@Payload Message msg,
                         @Header("simpSessionId") String playerTwo) {
        gameService.joinGame(msg, playerTwo);


    }

//
//    @MessageMapping("/gameStart")
//    //    @SendTo("/topic/createGame")
//    public String gameStartNotifier() {
//        return "game starts";
//    }


}
