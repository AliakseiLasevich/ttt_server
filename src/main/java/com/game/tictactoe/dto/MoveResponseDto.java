package com.game.tictactoe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MoveResponseDto {
    private String winnerId;
    private int opponentCellId;
    private String opponentId;

}
