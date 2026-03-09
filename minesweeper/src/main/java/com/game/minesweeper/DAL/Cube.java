package com.game.minesweeper.DAL;

public class Cube {
    private boolean isHidden = true;
    private cubeType type;
    private int val = -1;

    public Cube() { }

    public void setCubeNumber(int val) {
        if(val == 0) {
            this.type = cubeType.BLANK;
        } else {
            this.type = cubeType.NUMBER;
            this.val = val;
        }
    }

    public void setMine() {
        this.type = cubeType.MINE;
    }

    public int getVal() {
        return this.val;
    }

    public boolean getIsHidden() {
        return this.isHidden;
    }

    public cubeType getCubeType(){
        return this.type;
    }
}
