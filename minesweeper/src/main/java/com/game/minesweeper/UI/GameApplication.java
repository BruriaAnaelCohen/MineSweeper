package com.game.minesweeper.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Objects.requireNonNull(
                        GameApplication.class.getResource("/com/game/minesweeper/GameBoard.fxml")
                )
        );

        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("MineSweeper");
        stage.setScene(scene);
        stage.show();
    }
}
