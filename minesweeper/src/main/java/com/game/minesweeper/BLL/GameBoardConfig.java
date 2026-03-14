package com.game.minesweeper.BLL;

import java.util.HashSet;

public class GameBoardConfig {
    private int size = 9;
    private int mines = 10;
    private HashSet<Integer> mines_pos = new HashSet<>();

    public GameBoardConfig(int size, int mines) {
        this.size = size;
        this.mines = mines;
    }

    public GameBoardConfig() { }

    public int getSize() {
        return this.size;
    }

    public int getMinesCnt() {
        return this.mines;
    }

    public int getExistingMinesCnt() {
        return this.mines_pos.size();
    }

    public HashSet<Integer> getMinesPosSet() {
        return this.mines_pos;
    }

    public void addMinePos(int pos) {

        this.mines_pos.add(pos);
    }

    public void clearMinesSet() {
        this.mines_pos.clear();
    }
}
