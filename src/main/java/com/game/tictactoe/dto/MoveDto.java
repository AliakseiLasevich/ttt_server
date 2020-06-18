package com.game.tictactoe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveDto {
    private int gameId;
    private String userId;
    private String opponentId;
    private int move;
}
