module com.example.minesweeper2026 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.minesweeper2026 to javafx.fxml;
    exports com.example.minesweeper2026;
}