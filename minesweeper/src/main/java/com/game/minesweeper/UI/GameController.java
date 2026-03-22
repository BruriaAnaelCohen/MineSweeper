package com.game.minesweeper.UI;

import com.game.minesweeper.BLL.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.List;

public class GameController {
    @FXML
    private Label text_for_dev;
    @FXML
    private Button[][] boardButtons;
    @FXML
    private GridPane boardGrid;

    private boolean hasStarted = false;
    private GameService gameService;

    private int board_height;
    private int board_width;

    private static final String FLAG = "⚑";
    private static final String BLANK = "";



    @FXML
    protected void initialize() {
        this.gameService = new GameService();
        this.board_height = gameService.getBoardHeight();
        this.board_width = gameService.getBoardWidth();

        buildBoardVisual();
    }

    private void buildBoardVisual() {
        boardButtons = new Button[board_height][board_width]; // Visual matrix

        for (int row = 0; row < board_height; ++row) {
            for (int col = 0; col < board_width; ++col) {
                Button btn = new Button();

                int i = row;
                int j = col;
                btn.setOnMouseClicked(event -> handleClick(event, i, j));
                boardButtons[row][col] = btn;

                boardGrid.add(btn, col, row);
            }
        }
    }

    @FXML
    protected void quit() {
        Platform.exit();
    }

    @FXML
    protected void resetGame() {
        this.hasStarted = false;
        this.text_for_dev.setText("");

        clearGridView();
        gameService.resetGame();

    }

    private void clearGridView() {
        for(int i = 0; i < board_height; ++i) {
            for(int j = 0; j < board_width; ++j) {
                boardButtons[i][j].setText("");
                boardButtons[i][j].setDisable(false);
                boardButtons[i][j].setStyle("-fx-border-color: black;");
            }
        }
    }

    private void unhideBlankNeighborsView(List<int[]> list) {
        for (int[] indexes : list) {
            int i = indexes[0];
            int j = indexes[1];

            boardButtons[i][j].setDisable(true);
            revealCellVal(i, j);
        }

    }

    @FXML
    protected void handleClick(MouseEvent event, int i, int j) {
        if (!hasStarted) {
            hasStarted = true;
            gameService.startGame(i, j);
        }

        if (event.getButton() == MouseButton.PRIMARY) { // left click
            handlePrimaryClick(i, j);
        } else if (event.getButton() == MouseButton.SECONDARY) { // right click
            handleSecondaryClick(i, j);
        }

        if(this.gameService.hasWon()) { //TODO
            disableBoard();
            text_for_dev.setText("YOU WON :)");
        }
    }

    private void handlePrimaryClick(int i, int j) {
        boolean isValidMove = gameService.handleReveal(i, j);

        if(!isValidMove) {
            boardButtons[i][j].setStyle("-fx-border-color: red;");
            gameOver();
        }

        if(gameService.isBlank(i, j)) {
            List<int[]> list = gameService.getBlankNeighborsList(i, j);
            unhideBlankNeighborsView(list);
        } else {
            boardButtons[i][j].setDisable(true);
            revealCellVal(i, j);
            this.gameService.incRevealedCards();
        }
    }

    private void handleSecondaryClick(int i, int j) {
        if(gameService.isFlagged(i, j)) { // user wants to cancel flag
            removeFlagView(i, j);
            this.gameService.removeFlag(i, j);
        } else {
            setFlagView(i, j); // placing flag
            this.gameService.setFlag(i, j);
        }
    }

    private void revealCellVal(int i, int j) {
        String val = gameService.getCellVal(i, j);
        boardButtons[i][j].setText(val);
    }

    private void setFlagView(int i, int j) {
        boardButtons[i][j].setText(FLAG);
    }

    private void removeFlagView(int i, int j) {
        boardButtons[i][j].setText(BLANK);
    }

    private void gameOver() {
        disableBoard();
        text_for_dev.setText("Game Over, What a LOOSER :(");
    }

    private void disableBoard(){
        for(int i = 0; i < board_height; ++i) {
            for(int j = 0; j < board_width; ++j) {
                boardButtons[i][j].setDisable(true);
            }
        }
    }
}
