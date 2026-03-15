package com.game.minesweeper.UI;

import com.game.minesweeper.BLL.*;

import com.game.minesweeper.DAL.cubeType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public class GameController {
    @FXML
    private Label text_for_dev;
    @FXML
    private Button[][] boardButtons;
    @FXML
    private GridPane boardGrid;

    private boolean hasStarted = false;
    private GameService gameService;
    private GameBoardConfig config;

    private int board_size;

    private static final String FLAG = "⚑";
    private static final String BLANK = "";

    private int wrongMoves = 0;
    private int revealedCards = 0;
    private int minesDetected = 0;

    @FXML
    protected void initialize() {
        config = new GameBoardConfig();
        gameService = new GameService(config);
        board_size = config.getSize();

        buildBoardVisual(config);
    }

    private void buildBoardVisual(GameBoardConfig config) {
        boardButtons = new Button[board_size][board_size]; // Visual matrix

        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {
                Button btn = new Button();

                int i = row;
                int j = col;
                btn.setOnMouseClicked(event -> handleClick(event, i, j, " "));
                boardButtons[row][col] = btn;

                boardGrid.add(btn, col, row);
            }
        }
    }

    private void updateEventParam() {
        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {
                int i = row;
                int j = col;
                boardButtons[row][col].setOnMouseClicked(
                        event -> handleClick(event, i, j, boardButtons[i][j].getText()));
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
        this.wrongMoves = 0;
        this.revealedCards = 0;
        this.minesDetected = 0;
        this.text_for_dev.setText("");

        clearGridView();
        gameService.resetMatrix();

    }

    private void clearGridView() {
        for(int i = 0; i < board_size; ++i) {
            for(int j = 0; j < board_size; ++j) {
                boardButtons[i][j].setText("");
                boardButtons[i][j].setDisable(false);
                boardButtons[i][j].setStyle("-fx-border-color: black;");
            }
        }
    }

    private void unhideBlankNeighbor(int i, int j, String cube_val) {
        ++revealedCards;

        boardButtons[i][j].setDisable(true);
        revealCellVal(i, j);
        gameService.unhideCube(i, j);

        if(cube_val.equals(FLAG)) {
            --wrongMoves; // not a mine for sure
        }

        if(gameService.getCellCubeType(i, j) == cubeType.BLANK) {

            if(i >= 1) {
                if(gameService.isHidden(i - 1, j)) {
                    unhideBlankNeighbor(i - 1, j, boardButtons[i - 1][j].getText()); // up
                }

                if (j >= 1 && gameService.isHidden(i - 1, j - 1)) {
                    unhideBlankNeighbor(i - 1, j - 1, boardButtons[i - 1][j - 1].getText()); // top left
                }

                if(j < board_size - 1 && gameService.isHidden(i - 1, j + 1)) {
                    unhideBlankNeighbor(i - 1, j + 1, boardButtons[i - 1][j + 1].getText()); // top right
                }
            }

            if(j >= 1 && gameService.isHidden(i, j - 1)) {
                unhideBlankNeighbor(i, j - 1, boardButtons[i][j - 1].getText()); // left
            }

            if(j < board_size - 1 && gameService.isHidden(i, j + 1)) {
                unhideBlankNeighbor(i, j + 1, boardButtons[i][j + 1].getText()); // right
            }

            if(i < board_size - 1) {
                if(gameService.isHidden(i + 1, j)) {
                    unhideBlankNeighbor(i + 1, j, boardButtons[i + 1][j].getText()); //bottom
                }

                if (j >= 1 && gameService.isHidden(i + 1, j - 1)) {
                    unhideBlankNeighbor(i + 1, j - 1, boardButtons[i + 1][j - 1].getText()); // bottom left
                }

                if(j < board_size - 1 && gameService.isHidden(i + 1, j + 1)) {
                    unhideBlankNeighbor(i + 1, j + 1, boardButtons[i + 1][j + 1].getText()); // bottom right
                }
            }
        }

    }

    @FXML
    protected void handleClick(MouseEvent event, int i, int j, String cube_val) {
        if (!hasStarted) {
            hasStarted = true;
            gameService.startGame(i, j);
            updateEventParam();
        }

        if (event.getButton() == MouseButton.PRIMARY) { // left click
            handlePrimaryClick(i, j, cube_val);
        } else if (event.getButton() == MouseButton.SECONDARY) { // right click
            handleSecondaryClick(i, j, cube_val);
        }

        if(hasWon()) {
            disableBoard();
            // TO - DO
            text_for_dev.setText("YOU WON :)");
        }
    }

    private void handlePrimaryClick(int i, int j, String cube_val) {
        boolean isValidMove = gameService.handleReveal(i, j);

        if(!isValidMove) {
            boardButtons[i][j].setStyle("-fx-border-color: red;");
            gameOver();
        }

        if(gameService.getCellCubeType(i, j) == cubeType.BLANK) {
            unhideBlankNeighbor(i, j, cube_val);
        } else {
            boardButtons[i][j].setDisable(true);
            revealCellVal(i, j);
            ++revealedCards;
            if(cube_val.equals(FLAG)) {
                --wrongMoves; // not a mine for sure
            }
        }
    }

    private void handleSecondaryClick(int i, int j, String cube_val) {
        if(cube_val.equals(FLAG)) { // user wants to cancel flag
            removeFlag(i, j);
        } else {
            setFlag(i, j); // placing flag
        }
    }

    private void revealCellVal(int i, int j) {
        String val = gameService.getCellVal(i, j);
        boardButtons[i][j].setText(val);
    }

    private void setFlag(int i, int j) {
        boardButtons[i][j].setText(FLAG);
        boolean isMine = gameService.checkFlagIsMine(i, j);

        if(isMine) {
            ++minesDetected;
        } else {
            ++wrongMoves; // placing flag for no mine
        }
    }

    private void removeFlag(int i, int j) {
        boardButtons[i][j].setText(BLANK);
        boolean isMine = gameService.checkFlagIsMine(i, j);

        if(!isMine) {
            --wrongMoves;
        }

    }

    private boolean hasWon() {
        return wrongMoves == 0
                && minesDetected == config.getMinesCnt()
                && revealedCards == (Math.pow(config.getSize(), 2)) - minesDetected;
    }

    private void gameOver() {
        disableBoard();
        text_for_dev.setText("Game Over, What a LOOSER :(");
    }

    private void disableBoard(){
        for(int i = 0; i < board_size; ++i) {
            for(int j = 0; j < board_size; ++j) {
                boardButtons[i][j].setDisable(true);
            }
        }
    }
}
