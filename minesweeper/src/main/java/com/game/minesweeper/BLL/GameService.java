package com.game.minesweeper.BLL;

import com.game.minesweeper.DAL.Cube;
import com.game.minesweeper.DAL.GameBoardConfig;
import com.game.minesweeper.DAL.cubeType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameService {

    private Cube[][] cubesMat;
    private final GameBoardConfig config;

    private final int board_height;
    private final int board_width;

    private int revealedCards = 0;
    private int minesDetected = 0;

    private static final String MINE = "⚙";

    public GameService() {
        this.config = new GameBoardConfig();
        this.board_height = config.getHeight();
        this.board_width = config.getWidth();
        buildMatrix();
    }

    public int getBoardHeight() {
        return this.board_height;
    }

    public int getBoardWidth() {
        return this.board_width;
    }

    public void startGame(int i, int j) { // on user first click
        generateMinesPos(i, j);
        setCubeMatVals();
        // printCubesBoard_FOR_DEV();
    }

    private void buildMatrix() {
        cubesMat = new Cube[board_height][board_width];

        for (int row = 0; row < board_height; ++row) {
            for (int col = 0; col < board_width; ++col) {
                cubesMat[row][col] = new Cube();
            }
        }
    }

    private void generateMinesPos(int i, int j) {
        HashSet<Integer> mines_pos = new HashSet<>();

        int mine_cnt = config.getMines();
        int user_pos = i * board_width + j;

        while(mines_pos.size() < mine_cnt) {
            int pos = (int) (Math.random() * (board_height * board_width));

            if (pos != user_pos){
                mines_pos.add(pos);
            }
        }

        setMatrixMines(mines_pos);
    }

    private void setMatrixMines(HashSet<Integer> mines_pos) {
        mines_pos.forEach(pos -> {
            int row = pos / board_width;
            int col = pos % board_width;

            cubesMat[row][col].setMine();
        });

        mines_pos.clear();
    }

    private void setCubeMatVals() {
        for(int i = 0; i < board_height; ++i) {
            for(int j = 0; j < board_width; ++j) {

                if(cubesMat[i][j].getType() != cubeType.MINE) {
                    int val = calcCubeValue(i, j);
                    cubesMat[i][j].setCubeVal(val);
                }
            }
        }
    }

    private void printCubesBoard_FOR_DEV() {
        for(int i = 0; i < board_height; ++i) {
            for(int j = 0; j < board_width; ++j) {
                cubeType t = cubesMat[i][j].getType();

                switch (t) {
                    case MINE:
                        System.out.print(MINE + " ");
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

        for(int row = i - 1; row <= i + 1; ++row) {
            if( row >= 0 && row < this.board_height) { // valid row index
                for (int col = j - 1; col <= j + 1; ++col) {
                    if (col >= 0 && col < this.board_width && !(row == i && col == j) // valid col index
                            && (cubesMat[row][col].getType() == cubeType.MINE)) { // if mine then count
                            ++counter;
                    }
                }
            }
        }

        return counter;
    }

    public boolean handleReveal(int i, int j) {
        Cube cube = cubesMat[i][j];
        cube.unhide();
        cube.setFlagged(false);

        return cube.getType() != cubeType.MINE;
    }

    public String getCellVal(int i, int j) {
        Cube cube = cubesMat[i][j];
        cubeType type = cube.getType();
        String str = "";

        switch(type) {
            case MINE: {
                str = MINE;
                break;
            }
            case NUMBER: {
                str = Integer.toString(cube.getVal());
                break;
            }
        }

        return str;
    }

    public void incRevealedCards() {
        ++this.revealedCards;
    }

    public boolean hasWon() {
        return this.minesDetected == config.getMines()
                && this.revealedCards == (this.board_width * this.board_height) - this.minesDetected;
    }

    public List<int[]> getBlankNeighborsList(int i, int j) {
        List<int[]> list = new ArrayList<>();
        unhideBlankNeighbor(i, j, list);

        return list;
    }

    private void unhideBlankNeighbor(int i, int j, List<int[]> list) {
        incRevealedCards();
        cubesMat[i][j].unhide();
        list.add(new int[]{i, j});

        if(isBlank(i, j)) { // recursively open neighbors
            for(int row = i - 1; row <= i + 1; ++row) {
                if( row >= 0 && row < this.board_height) { // valid row index
                    for (int col = j - 1; col <= j + 1; ++col) {
                        if (col >= 0 && col < this.board_width && !(row == i && col == j)) { // valid col index
                            if(isHidden(row, col)) {
                                unhideBlankNeighbor(row, col, list);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isBlank(int i, int j) {
        return cubesMat[i][j].getType() == cubeType.BLANK;
    }

    public boolean isHidden(int i, int j) {
        return cubesMat[i][j].isHidden();
    }

    public boolean isFlagged(int i, int j) {
        return cubesMat[i][j].isFlagged();
    }

    public void setFlag(int i, int j) {
        cubesMat[i][j].setFlagged(true);

        if(cubesMat[i][j].getType() == cubeType.MINE) {
            ++this.minesDetected;
        }
    }

    public void removeFlag(int i, int j) {
        cubesMat[i][j].setFlagged(false);
    }

    public void resetGame() {
        resetMeasures();
        resetMatrix();
    }

    private void resetMeasures() {
        this.revealedCards = 0;
        this.minesDetected = 0;
    }

    private void resetMatrix() {
        for (int row = 0; row < board_height; ++row) {
            for (int col = 0; col < board_width; ++col) {
                cubesMat[row][col].resetCube();
            }
        }
    }
}