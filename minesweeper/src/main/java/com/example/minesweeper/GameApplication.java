package com.example.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("GameBoard.fxml"));

        Scene scene = //new Scene(grid); //
            new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("MineSweeper");
        stage.setScene(scene);
        stage.show();
    }
}
