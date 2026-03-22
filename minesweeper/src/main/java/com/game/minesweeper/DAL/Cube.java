package com.game.minesweeper.DAL;

import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;

 @Getter
 @NoArgsConstructor

public class Cube {
    private boolean isHidden = true;
    private cubeType type = cubeType.BLANK;
    private int val = -1;
    @Setter  private boolean isFlagged = false;

    public void setMine() {
         this.type = cubeType.MINE;
    }

    public void setCubeVal(int val) {
        if(val != 0) {
            this.type = cubeType.NUMBER;
            this.val = val;
        }
    }

    public void unhide() {
        this.isHidden = false;
    }

    public void resetCube() {
         this.isHidden = true;
         this.type = cubeType.BLANK;
         this.val = -1;
     }

 }
