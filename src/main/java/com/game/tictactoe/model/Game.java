package com.game.tictactoe.model;

import lombok.Data;

@Data
public class Game {
    private String name;
    private String tag;
    private Player player1;
    private Player player2;
    private boolean open;
}
