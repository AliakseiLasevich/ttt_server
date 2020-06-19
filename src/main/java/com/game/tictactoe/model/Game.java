package com.game.tictactoe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Game {
    private long id;
    private String name;
    private List<String> tags;
    private String playerOne;
    private String playerTwo;
    private boolean open;
    private String winner;
    private int[] cells = new int[9];

    public Game(long id, String playerOne, String name) {
        this.id = id;
        this.playerOne = playerOne;
        this.name = name;
        this.open = true;
        tags = new ArrayList<>();
    }
}
