package com.game.minesweeper.DAL;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GameBoardConfig {
    private int height = 9;
    private int width = 9;
    private int mines = 10;
}
