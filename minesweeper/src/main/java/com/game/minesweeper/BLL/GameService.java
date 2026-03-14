package com.game.minesweeper.BLL;
import com.game.minesweeper.DAL.*;

public class GameService {

    private Cube [][] cubesMat;
    private final GameBoardConfig config;

    private final int board_size;

    public GameService(GameBoardConfig config) {
        this.config = config;
        this.board_size = config.getSize();
        buildMatrix();
    }

    public void startGame(int i, int j) { // on user first click
        generateMinesPos(i, j);
        fillMatValues();
        printCubesBoard_FOR_DEV();
    }

    public void resetMatrix() {
        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {
                cubesMat[row][col].resetCube();
            }
        }
    }

    private void buildMatrix() {
        cubesMat = new Cube[board_size][board_size];

        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {
                cubesMat[row][col] = new Cube();
            }
        }
    }

    private void generateMinesPos(int i, int j) {
        config.clearMinesSet();

        int mine_cnt = config.getMinesCnt();

        int user_pos = i * board_size + j;

        while(config.getExistingMinesCnt() < mine_cnt) {
            int pos = (int) (Math.random() * (Math.pow(board_size, 2) - 1)); // 0 <= x <=80
            if (pos != user_pos){
                config.addMinePos(pos);
            }
        }
    }

    private void fillMatValues() {
        setMatrixMines();
        setCubeMatVals();
    }

    private void setMatrixMines() {
        config.getMinesPosSet().forEach(pos -> {
            int row = pos / board_size;
            int col = pos % board_size;

            cubesMat[row][col].setMine();
        });
    }

    private void setCubeMatVals() {
        for(int i = 0; i < board_size; ++i) {
            for(int j = 0; j < board_size; ++j) {

                if(cubesMat[i][j].getCubeType() != cubeType.MINE) {
                    int val = calcCubeValue(i, j);
                    cubesMat[i][j].setCubeNumber(val);
                }
            }
        }
    }

    private void printCubesBoard_FOR_DEV() {
        for(int i = 0; i < board_size; ++i) {
            for(int j = 0; j < board_size; ++j) {
                cubeType t = cubesMat[i][j].getCubeType();

                switch (t) {
                    case MINE:
                        System.out.print("* ");
                        break;
                    case BLANK:
                        System.out.print("_ ");
                        break;
                    case NUMBER:
                        System.out.print(cubesMat[i][j].getVal() + " ");
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private int calcCubeValue(int i, int j) { // check surrounding mines
        int counter = 0;
        cubeType t;

        if(i >= 1) { // empty row on top
            t = cubesMat[i - 1][j].getCubeType();
            if(t == cubeType.MINE) { // top
                ++counter;
            }

            if (j >= 1) {
                t = cubesMat[i - 1][j - 1].getCubeType();
                if(t == cubeType.MINE) { // top left
                    ++counter;
                }
            }

            if(j < board_size - 1 ) {
                t = cubesMat[i - 1][j + 1].getCubeType();
                if(t == cubeType.MINE) { // top right
                    ++counter;
                }

            }
        }

        if(j >= 1) {
            t = cubesMat[i][j - 1].getCubeType();
            if(t == cubeType.MINE) { // left
                ++counter;
            }
        }

        if(j < board_size - 1) {
            t = cubesMat[i][j + 1].getCubeType();
            if(t == cubeType.MINE) { // right
                ++counter;
            }
        }

        if(i < board_size - 1) { // empty row on bottom
            t = cubesMat[i + 1][j].getCubeType();
            if(t == cubeType.MINE) { // bottom
                ++counter;
            }

            if(j >= 1) {
                t = cubesMat[i + 1][j - 1].getCubeType();
                if(t == cubeType.MINE) { // bottom left
                    ++counter;
                }
            }

            if(j < board_size - 1) {
                t = cubesMat[i + 1][j + 1].getCubeType();
                if(t == cubeType.MINE) { // bottom right
                    ++counter;
                }
            }
        }

        return counter;
    }

    public void unhideCube(int i, int j) {
        cubesMat[i][j].unhide();
    }

    public boolean handleReveal(int i, int j) {
        Cube cube = cubesMat[i][j];
        cube.unhide();

        return cube.getCubeType() != cubeType.MINE;
    }

    public cubeType getCellCubeType(int i, int j) {
        return cubesMat[i][j].getCubeType();
    }

    public boolean isHidden(int i, int j) {
        return cubesMat[i][j].getIsHidden();
    }

    public String getCellVal(int i, int j) {
        Cube cube = cubesMat[i][j];
        cubeType type = cube.getCubeType();
        String str = "";

        switch(type) {
            case MINE: {
                str = "💣︎";
                break;
            }
            case NUMBER: {
                str = Integer.toString(cube.getVal());
                break;
            }
        }

        return str;
    }

    public boolean checkFlagIsMine(int i, int j) {
        return cubesMat[i][j].getCubeType() == cubeType.MINE;
    }

}
