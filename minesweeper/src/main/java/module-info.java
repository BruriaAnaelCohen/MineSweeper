module com.example.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.game.minesweeper to javafx.fxml;
    exports com.game.minesweeper;
}