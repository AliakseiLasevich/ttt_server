package com.game.tictactoe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game {
    private String tag;
    private String playerOneSession;
    private String playerTwoSession;
    private boolean open;

    public Game(String playerOneSession, String tag) {
        this.playerOneSession = playerOneSession;
        this.tag = tag;

    }
}
