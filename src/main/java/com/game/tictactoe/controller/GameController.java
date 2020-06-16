package com.game.tictactoe.controller;

import com.game.tictactoe.model.Game;
import com.game.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;


    //TODO implement fuction to send active game to current user session.
    @MessageMapping("/player1Game")
    @SendToUser("/queue/activeGame")
    public Game findActiveGame(Message message){
        return gameService.findPlayer1Game(message);
    }

    @MessageMapping("/availableGames")
    @SendTo("/topic/availableGames")
    public List<Game> availableGames(Message message) {
        return gameService.findAvailableGames();
    }

    @MessageMapping("/createGame")
    @SendTo("/topic/createGame")
    public Game createGame(Message message) {
        return gameService.createGame(message);
    }


}
