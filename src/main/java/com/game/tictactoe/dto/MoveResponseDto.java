package com.game.tictactoe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MoveResponseDto implements ResponseDto {
    private int opponentCellId;
    private String opponentId;

}
