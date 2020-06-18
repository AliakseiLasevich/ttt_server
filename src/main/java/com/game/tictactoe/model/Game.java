package com.game.tictactoe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game {
    private long id;
    private String name;
    private String tag;
    private String playerOne;
    private String playerTwo;
    private boolean open;
    private String winner;
    private int[] cells = new int[9];

    public Game(long id, String playerOne, String tag) {
        this.id = id;
        this.playerOne = playerOne;
        this.tag = tag;
        this.open = true;
    }
}
