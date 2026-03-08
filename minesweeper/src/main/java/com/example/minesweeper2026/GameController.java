package com.example.minesweeper2026;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.HashSet;

public class GameController {
    @FXML
    private Label arrayText;
    @FXML
    private GridPane boardGrid;

    @FXML
    protected void onQuitButtonClick() {
        Platform.exit();
    }
    @FXML
    public void initialize() {
        int size = 9;
        char initialChar = 'A';

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Create a container for the character
                StackPane cell = new StackPane();
                cell.setPrefSize(40, 40); // Set cell dimensions

                // Add the character
                Label text = new Label(String.valueOf(initialChar));
                cell.getChildren().add(text);

                // Add to the grid: (child, columnIndex, rowIndex)
                boardGrid.add(cell, col, row);
            }
        }
    }
    @FXML
    protected void onPrintArrayClick() {
        int board_size = 9;
        int mine_cnt = 10;

        HashSet<Integer> mine_pos = new HashSet<Integer>();

        for(int i = 0; i < mine_cnt; ++i) { // 10 times or more
            int pos = (int) (Math.random() * (Math.pow(board_size, 2) - 1)); // 0 <= x <=80
            boolean has_inserted = mine_pos.add(pos);

            if (!has_inserted) {
                ++i;
            }
        }

        arrayText.setText(mine_pos.toString());
    }
}
