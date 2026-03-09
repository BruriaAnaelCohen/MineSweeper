package com.example.minesweeper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashSet;

public class GameController {
    @FXML
    private Label arrayText;
    @FXML
    private Button[][] cells;
    @FXML
    private GridPane boardGrid;

    private Cube [][] cubesBoard;

    private HashSet<Integer> mine_pos = new HashSet<Integer>(); // set of mines positions

    private final int board_size = 9;
    private final int mine_cnt = 10;

    private boolean hasStarted = false;

    @FXML
    protected void onQuitButtonClick() {
        Platform.exit();
    }

    @FXML
    protected void initialize() {
        buildGrid();
    }

    @FXML
    protected void resetGame() {
        hasStarted = false;
        arrayText.setText("");
        clearGridView();
    }

    private void startGame() { // on user first click
        generateMinesPos();
        setGridMines();
        setGridMines_actual();
        setCubesBoardVals();
        printCubesBoard();
    }

    private void buildGrid() {

        cubesBoard = new Cube[board_size][board_size]; // Java Class matrix
        cells = new Button[board_size][board_size]; // Visual matrix

        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {
                cubesBoard[row][col] = new Cube(); // Java Class Obj

                Button cell = new Button();
                cell.setPrefSize(45, 45);
                //cell.setStyle("-fx-background-color: #5C5858; ");
                cells[row][col] = cell;

                int i = row;
                int j = col;
                cells[row][col].setOnMouseClicked(event -> handleCellClick(event, i, j));

                boardGrid.add(cell, col, row);
            }
        }
    }

    private void handleCellClick(MouseEvent event, int i, int j){
        if (!hasStarted) {
           hasStarted = true;
            startGame();
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            System.out.println("Left-click action");
        } else if (event.getButton() == MouseButton.SECONDARY) {
            System.out.println("Right-click action");
        }

        arrayText.setText("Clicked cell: i=" + i + " j=" + j);
    }


    private void generateMinesPos() {
        mine_pos.clear();

//        while(mine_pos.size() < mine_cnt) {
//            int pos = (int) (Math.random() * (Math.pow(board_size, 2) - 1)); // 0 <= x <=80
//            mine_pos.add(pos);
//        }

        mine_pos.add(2);
        mine_pos.add(8);
        mine_pos.add(9);
        mine_pos.add(15);
        mine_pos.add(20);
        mine_pos.add(25);
        mine_pos.add(31);
        mine_pos.add(35);
        mine_pos.add(42);
        mine_pos.add(53);

        arrayText.setText(mine_pos.toString());
    }

    private void clearGridView() {
        mine_pos.forEach(pos -> {
            int row = pos / board_size;
            int col = pos % board_size;
            cells[row][col].setText("");
        });
    }

    private void setGridMines_actual() {
        mine_pos.forEach(pos -> {
            int row = pos / board_size;
            int col = pos % board_size;
            cubesBoard[row][col].setMine();
        });
    }

    private void setGridMines() {
        mine_pos.forEach(pos -> {
            int row = pos / board_size;
            int col = pos % board_size;
            cells[row][col].setText("*");
        });
    }

    private void setCubesBoardVals() {
        for(int i = 0; i < board_size; ++i) {
            for(int j = 0; j < board_size; ++j) {
                int val = setCubeValue(i, j);
                cubesBoard[i][j].setCubeNumber(val);
            }
        }
    }

    private void printCubesBoard() {
        for(int i = 0; i < board_size; ++i) {
            for(int j = 0; j < board_size; ++j) {
                cubeType t = cubesBoard[i][j].getCubeType();

                switch (t) {
                    case MINE:
                        System.out.print("* ");
                        break;
                    case BLANK:
                        System.out.print("_ ");
                        break;
                    case NUMBER:
                        System.out.print(cubesBoard[i][j].getVal() + " ");
                        break;
                }
            }

            System.out.println();
        }
    }

    private int setCubeValue(int i, int j) { // check surounding mines
        int counter = 0;

        if(i >= 1) {
            if (j >= 1) {
                if(cubesBoard[i - 1][j - 1].getCubeType() == cubeType.MINE) { // top left
                    ++counter;
                }
                if(cubesBoard[i - 1][j].getCubeType() == cubeType.MINE) { // top
                    ++counter;
                }
                if(cubesBoard[i][j - 1].getCubeType() == cubeType.MINE) { // left
                    ++counter;
                }
            }

            if(j < board_size - 1 ) {
                if(cubesBoard[i - 1][j + 1].getCubeType() == cubeType.MINE) { // top right
                    ++counter;
                }
                if(cubesBoard[i][j + 1].getCubeType() == cubeType.MINE) { // right
                    ++counter;
                }
            }
        }

        if(i < board_size - 1) {
            if(j >= 1) {
                if(cubesBoard[i + 1][j - 1].getCubeType() == cubeType.MINE) { // bottom left
                    ++counter;
                }
            }

            if(cubesBoard[i + 1][j].getCubeType() == cubeType.MINE) { // bottom
                ++counter;
            }

            if(j < board_size - 1) {
                if(cubesBoard[i + 1][j + 1].getCubeType() == cubeType.MINE) { // bottom right
                    ++counter;
                }
            }
        }

        return counter;
    }
}
