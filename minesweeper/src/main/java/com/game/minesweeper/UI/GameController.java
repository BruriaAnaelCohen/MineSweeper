package com.game.minesweeper.UI;

import com.game.minesweeper.BLL.*;

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

    @FXML
    protected void initialize() {
        config = new GameBoardConfig();
        gameService = new GameService(config);

        buildBoardVisual(config);
    }

    private void buildBoardVisual(@NotNull GameBoardConfig config) {
        int board_size = config.getSize();
        boardButtons = new Button[board_size][board_size]; // Visual matrix

        for (int row = 0; row < board_size; ++row) {
            for (int col = 0; col < board_size; ++col) {

                Button btn = new Button();
                btn.setPrefSize(40, 40);
                //cell.setStyle("-fx-background-color: #5C5858; ");

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
        hasStarted = false;
        text_for_dev.setText("");
        clearGridView();
    }

    private void clearGridView() {
        int board_size = config.getSize();

        config.getMinesPosSet().forEach(pos -> {
            int row = pos / board_size;
            int col = pos % board_size;
            boardButtons[row][col].setText("");
        });
    }

    @FXML
    protected void handleClick(MouseEvent event, int i, int j){
        if (!hasStarted) {
            hasStarted = true;
            gameService.startGame(i, j);
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            System.out.println("Left-click action");
        } else if (event.getButton() == MouseButton.SECONDARY) {
            System.out.println("Right-click action");
        }

        text_for_dev.setText("Clicked cell: i=" + i + " j=" + j);
    }

}
